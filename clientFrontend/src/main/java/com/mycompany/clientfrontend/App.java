package com.mycompany.clientfrontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * JavaFX App
 */
public class App extends Application {
    
    public static BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("testing");
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    public static String getMessageList() {
        try {
            String messageList = queue.take();
            return messageList;
        }
        catch (InterruptedException e) {
            System.out.println("Error receiving msgLst");
        }
        return ("Error receiving msgLst");
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {

        Client client = new Client(queue);
        //BackendClient client = new BackendClient();
        launch();
    }

}