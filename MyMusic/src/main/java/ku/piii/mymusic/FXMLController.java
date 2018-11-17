 package ku.piii.mymusic;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.URL;
import java.util.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ku.piii.marshalling.JacksonJSONMarshallingSupport;
import ku.piii.marshalling.MarshallingSupport;
import ku.piii.model.MusicMedia;
import ku.piii.model.MusicMediaCollection;
import org.codehaus.plexus.util.FileUtils;



@SuppressWarnings("restriction")
public class FXMLController implements Initializable {
    @FXML private TextField searchBar;
    @FXML private AnchorPane anchorPane;
    @FXML private HBox searchResults;
    @FXML private Button btnFavourite;
    @FXML private Label pleaseSelect;
    @FXML private Button btnDeleteDuplicates;
    @FXML private Button btnPlay;
    private final Instance instance = new Instance();
    private final  Stage ownerWindow = instance.returnStageObject();
    private final MarshallingSupport marshallingSupport = new JacksonJSONMarshallingSupport(new ObjectMapper());
    private final  DirectoryChooser directoryChooser = instance.returnDirectoryChooserObject();
    private final TableModel table = new TableModel(new String [] {"Title","Year","Genre"},new String [] {"title","year","genre"});
    private MusicMediaCollection collection;
    private final  ObservableList<MusicMedia> music = FXCollections.observableArrayList(
        );
    private final Music musicInstance = new Music();
    
    @FXML
    @SuppressWarnings("empty-statement")
    public void viewAll(ActionEvent event) throws IOException {
        //File selectedDir = directoryChooser.showDialog(ownerWindow);
        //MusicService musicService= instance.returnMusicServiceObject();
        this.defaultSettings();
        btnFavourite.setVisible(true);
        collection = instance.returnMusicServiceObject().createMusicMediaCollection(new File("src/main/resources/AllMusic").toPath());
        for(MusicMedia mm : collection.getMusic())
        {
            music.add(mm);
        }
       this.addTableToVBox(table,searchResults,ownerWindow,music);
       playSong();
    }
    @FXML
    @SuppressWarnings("empty-statement")
    public void upload(ActionEvent event) throws IOException {
        this.defaultSettings();
        collection = instance.returnMusicServiceObject().createMusicMediaCollection(directoryChooser.showDialog(ownerWindow).toPath());
        
        for(MusicMedia mm : collection.getMusic())
        {
            music.add(mm);
            FileUtils.copyFileToDirectory(mm.getPath(), "src/main/resources/AllMusic/");
        }
        this.addTableToVBox(table,searchResults,ownerWindow,music);
    }
    @FXML
    @SuppressWarnings("empty-statement")
    public void addToFavourite(ActionEvent event) throws IOException {
       MusicMedia musicMedia = (MusicMedia) table.getSelectionModel().getSelectedItem();
       if(musicMedia != null)
       {
           musicInstance.addToTextFile(musicMedia,new File("src/main/resources/Favourites.txt"),true);
           pleaseSelect.setText(musicMedia.getTitle() + " has been added");
       }
       else
       {
           pleaseSelect.setText("Please Select an Item");
       }
    }
    @FXML
    @SuppressWarnings("empty-statement")
    public void viewFavourites(ActionEvent event) throws IOException {
        this.defaultSettings();
        btnDeleteDuplicates.setVisible(true);
        this.addTableToVBox(table,searchResults,ownerWindow,musicInstance.selectAllInTextFile(new File("src/main/resources/Favourites.txt")));   
    }
    @FXML
    @SuppressWarnings("empty-statement")
    public void deleteDuplicates(ActionEvent event) throws IOException {
        this.defaultSettings();
        btnDeleteDuplicates.setVisible(true);
        ArrayList<String> arrayOfFavourites = musicInstance.selectAllInTextFileAsStringArray(new File("src/main/resources/Favourites.txt"));
        musicInstance.addArrayToTextFile(musicInstance.deleteDuplicates(arrayOfFavourites), new File("src/main/resources/Favourites.txt"));
        this.addTableToVBox(table,searchResults,ownerWindow,musicInstance.selectAllInTextFile(new File("src/main/resources/Favourites.txt")));   
    }
    public void playSong()
    {
        if(table.getSelectionModel().getSelectedItem() != null)
        {
            MusicMedia musicMedia = (MusicMedia) table.getSelectionModel().getSelectedItem();
            Media hit = new Media(new File(musicMedia.getPath()).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void addTableToVBox(TableModel tm, HBox hbox,Stage stage, ObservableList<MusicMedia> ob)
    {
            tm.setItems(ob);
            hbox.getChildren().add(tm);
            Scene scene= new Scene(hbox);
            
            stage.setScene(scene);
            stage.show();
    }
    public void defaultSettings() throws FileNotFoundException
    {
        music.clear();
        pleaseSelect.setText("");
        btnFavourite.setVisible(false);
        btnDeleteDuplicates.setVisible(false);
        searchResults.getChildren().clear();
        musicInstance.selectAllInTextFile(new File("src/main/resources/Favourites.txt")).clear();
        table.getItems().clear();
    }
    
}