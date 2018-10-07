/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbs;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author W8
 */
public class Main extends Application {
    
    private Stage window;
    private DbController controller;
    private Views views;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        controller = new DbController();
        window = stage;
        window.setTitle("Library");
        window.setOnCloseRequest(e -> closeProgram());
        
        views = new Views(window, controller);
                
        window.setScene(views.getLoginScene());
        window.show();
    }    
    
    private void closeProgram() {
        controller.exit();
    }
}
