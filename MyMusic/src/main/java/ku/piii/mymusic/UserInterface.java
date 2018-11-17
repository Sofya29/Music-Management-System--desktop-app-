/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii.mymusic;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Sofya
 */
public class UserInterface {
    Scene scene = null;
    Stage stage = new Stage();
    VBox vbox = new VBox();
    
    public void setScene(String resource, String stylesheet, String title) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource(resource));
        scene = new Scene(root);
        scene.getStylesheets().add(stylesheet);
        stage.setTitle(title);
        stage.setResizable(false);
        this.addToVBox(root,scene);
    }
    public void addToVBox(Parent root, Scene scene)
    {
        vbox.getChildren().add(root);
        scene= new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }
}
