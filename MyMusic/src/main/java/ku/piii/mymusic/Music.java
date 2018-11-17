/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii.mymusic;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;
import javafx.collections.*;
import ku.piii.marshalling.JacksonJSONMarshallingSupport;
import ku.piii.marshalling.MarshallingSupport;
import ku.piii.model.MusicMedia;

/**
 *
 * @author Sofya
 */
public class Music{    
    private final MarshallingSupport marshallingSupport = new JacksonJSONMarshallingSupport(new ObjectMapper());
    private FileWriter fw;
    private Scanner scanner ;
    private final  ObservableList<MusicMedia> music = FXCollections.observableArrayList(
        );
    
    public Music() {
    }
    public void addToTextFile(MusicMedia musicMedia,File file,boolean dontOverwrite) throws IOException
    {
        fw = new FileWriter(file,dontOverwrite);
        fw.append(marshallingSupport.marshal(musicMedia) + "\n");
        fw.close();
    }
    public ObservableList<MusicMedia> selectAllInTextFile(File file) throws FileNotFoundException
    {
            scanner = new Scanner(file);
            while(scanner.hasNextLine())
            {
                String [] line = scanner.nextLine().split("\n");
                MusicMedia mm = marshallingSupport.unmarshal(line[0], MusicMedia.class);
                music.add(mm);
            }
            scanner.close();
       return music;
    }
    public ArrayList<String> selectAllInTextFileAsStringArray(File file) throws FileNotFoundException
    {
            ArrayList<String> stringArray = new ArrayList();
            scanner = new Scanner(file);
            while(scanner.hasNextLine())
            {
                String [] line = scanner.nextLine().split("\n");
                stringArray.add(line[0]);
            }
            scanner.close();
       return stringArray;
    }
    public ArrayList<String> deleteDuplicates(ArrayList<String> myArray)
    {
        ArrayList<String> listWithoutDuplicates = new ArrayList();
            for(int i=0; i < myArray.size(); i++)
            {
                if(!listWithoutDuplicates.contains(myArray.get(i)))
                {
                    listWithoutDuplicates.add(myArray.get(i));
                }
            }
        return listWithoutDuplicates;
    }
    public void addArrayToTextFile(ArrayList<String> myArray, File file) throws IOException
    {
        boolean trueFalse = false;
        for(int i=0; i < myArray.size(); i++)
        {
            if(i > 0)
            {
                trueFalse = true;
            }
            this.addToTextFile(marshallingSupport.unmarshal(myArray.get(i), MusicMedia.class), file, trueFalse);
        }
    }
}
