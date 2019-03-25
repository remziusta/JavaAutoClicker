package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	
	
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			Parent root= FXMLLoader.load(getClass().getResource("sample.fxml"));
			Scene scene = new Scene(root,350,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.getIcons().add(new Image("click.png"));
			primaryStage.setScene(scene);
			primaryStage.setTitle("AutoClick");
			primaryStage.setResizable(false);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
		
	}
	
	
	
	
	
	
	
}
