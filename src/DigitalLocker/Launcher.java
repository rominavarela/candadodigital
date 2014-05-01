package DigitalLocker;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

import Commons.IO;

public class Launcher extends Application{
	
	public static void main(String args[])
	{		
		launch ();
	}
	
	@SuppressWarnings("deprecation")
	public void start(Stage stage) throws Exception {
		
		File f= IO.getFile("view.fxml");
				
		/*
		 * loads view
		 */
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(f.toURL());
		    Parent root = (Parent) fxmlLoader.load();
			Scene scene= new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "ERROR @start\n"+ex.getMessage());}
	}

}
