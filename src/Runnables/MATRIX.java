package Runnables;

import java.io.File;

import javax.swing.JOptionPane;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MATRIX extends Application{

	public static void main(String args[])
	{
		try{
		launch (args);
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
	
	@SuppressWarnings("deprecation")
	public void start(Stage stage) throws Exception {
		File f= new File("view.fxml");
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(f.toURL());
		    Parent root = (Parent) fxmlLoader.load();
			Scene scene= new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());}
	}
	
	///////////////////////////////////////////////////////////
	// MASTER TAB
	/*
	 * Objects
	 */
	@FXML private PasswordField master_field1;
	@FXML private PasswordField master_field2;
	@FXML private PasswordField master_field3;
	
	/*
	 * Actions
	 */
	public void master_cancel(ActionEvent e)
	{
		master_field1.setText("");
		master_field2.setText("");
		master_field3.setText("");
	}
	
	public void master_ok(ActionEvent e)
	{
		System.out.println("you clicked to ok");
	}
	
	
	///////////////////////////////////////////////////////////
	// DEVICE TAB
	/*
	* Objects
	*/
	@FXML private TextField 	device_direction;
	@FXML private PasswordField device_password1;
	@FXML private PasswordField device_password2;
	
	/*
	* Actions
	*/
	public void device_chooser(ActionEvent e)
	{
		System.out.println("you clicked to chooser");
	}
	
	public void device_cancel(ActionEvent e)
	{
		device_direction.setText("");
		device_password1.setText("");
		device_password2.setText("");
	}
	
	public void device_ok(ActionEvent e)
	{
		System.out.println("you clicked to ok");
	}
	

}