/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii.mymusic;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ku.piii.marshalling.JacksonJSONMarshallingSupport;
import ku.piii.mp3.MP3PathToMusicMapperImpl;
import ku.piii.music.MusicRepositoryImpl;
import ku.piii.music.MusicService;
import ku.piii.music.MusicServiceImpl;
import ku.piii.nio.file.TextFileStoreImpl;

/**
 *
 * @author Sofya
 */
public class Instance {
      
    public MusicService returnMusicServiceObject()
    {
        MusicService musicService = new MusicServiceImpl(
                        new MusicRepositoryImpl(
                                new JacksonJSONMarshallingSupport(new ObjectMapper()),
                                new TextFileStoreImpl()),
                        new MP3PathToMusicMapperImpl()
                );;
        return musicService;
    }
    public DirectoryChooser returnDirectoryChooserObject()
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        return directoryChooser;
    }
    public Stage returnStageObject()
    {
        Stage stage = new Stage();
        return stage;
    }
}
