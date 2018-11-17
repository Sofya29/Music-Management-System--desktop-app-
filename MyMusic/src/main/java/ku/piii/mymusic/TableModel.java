/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii.mymusic;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import ku.piii.model.MusicMedia;

/**
 *
 * @author Sofya
 */
public class TableModel extends TableView {
    private String [] titles;
    private String [] attributes;
    
    public TableModel(String [] titles, String [] attributes) {
        this.titles = titles;
        this.attributes = attributes;
        
        for(int i = 0; i<titles.length; i++)
        {
            TableColumn tblCol = new TableColumn(titles[i]);
            tblCol.setMinWidth(200);
            tblCol.setCellValueFactory(new PropertyValueFactory<MusicMedia, String>(attributes[i]));
            tblCol.setCellFactory(TextFieldTableCell.forTableColumn());
            this.getColumns().add(tblCol);
        }
    } 

    public String [] getTitles() {
        return titles;
    }

    public void setTitles(String [] titles) {
        this.titles = titles;
    }

    public String [] getAttributes() {
        return attributes;
    }

    public void setAttributes(String [] attributes) {
        this.attributes = attributes;
    }
}
