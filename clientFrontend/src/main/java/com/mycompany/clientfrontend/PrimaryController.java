package com.mycompany.clientfrontend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.scene.control.ListView;


public class PrimaryController {

    @FXML
    private ListView<String> ListView;
    
    

    
    @FXML
    private void switchToSecondary() throws IOException {
        String messageList = App.getMessageList();
        
        String[] parts = messageList.split(",\\s*");   // splits on comma + optional space

        ArrayList<String> list = new ArrayList<>(Arrays.asList(parts));

        ObservableList<String> items = FXCollections.observableArrayList(list);
        ListView.setItems(items);
        
    }
}
