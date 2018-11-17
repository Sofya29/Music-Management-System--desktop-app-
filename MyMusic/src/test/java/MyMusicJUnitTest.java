/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ku.piii.marshalling.JacksonJSONMarshallingSupport;
import ku.piii.marshalling.MarshallingSupport;
import ku.piii.model.MusicMedia;
import ku.piii.mymusic.Music;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sofya
 */
public class MyMusicJUnitTest {
     private final Music musicInstance = new Music();
     
    @Test
    public void testAddToTextFile() throws IOException
    {
        MusicMedia musicMedia = new MusicMedia();
        musicInstance.addToTextFile(musicMedia, new File("src/main/resources/Test.txt"),false);
        int noOfItems = musicInstance.selectAllInTextFile(new File("src/main/resources/Test.txt")).size();
        assertEquals(noOfItems,1);
    }
    @Test
    public void testSelectAllITextFile() throws FileNotFoundException
    {
       int noOfItemsInTest2 = musicInstance.selectAllInTextFile(new File("src/main/resources/Test2.txt")).size();
       assertEquals(noOfItemsInTest2,7);
    }
    @Test
    public void testSelectAllInTextFileAsStringArray() throws FileNotFoundException
    {
       int noOfItemsInTest2 = musicInstance.selectAllInTextFileAsStringArray(new File("src/main/resources/Test2.txt")).size();
       assertEquals(noOfItemsInTest2,7);
    }
    @Test
    public void testDeleteDuplicates()
    {
        ArrayList<String> stringArray = new ArrayList();
        stringArray.add("test1");
        stringArray.add("test1");
        stringArray.add("test2");
        stringArray.add("test2");
        stringArray.add("test1");
        stringArray.add("test3");
        
        assertEquals( musicInstance.deleteDuplicates(stringArray).size(), 3);
    }
    @Test
    public void testaddArrayToTextFile() throws IOException
    {
        ArrayList<String> stringArray = new ArrayList();
        MusicMedia mm = new MusicMedia();
        MarshallingSupport marshallingSupport = new JacksonJSONMarshallingSupport(new ObjectMapper());
        stringArray.add(marshallingSupport.marshal(mm) + "\n");
        stringArray.add(marshallingSupport.marshal(mm) + "\n");
        stringArray.add(marshallingSupport.marshal(mm) + "\n");
        
        musicInstance.addArrayToTextFile(stringArray, new File("src/main/resources/Test.txt"));
        
        assertEquals(musicInstance.selectAllInTextFile(new File("src/main/resources/Test.txt")).size(), 3);
    }
}
