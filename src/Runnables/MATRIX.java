package Runnables;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Commons.IO;

import javafx.application.*;
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
			JOptionPane.showMessageDialog(null, "ERROR @main\n"+ex.getMessage());
		}
	}
	
	@SuppressWarnings("deprecation")
	public void start(Stage stage) throws Exception {
		
		File f= IO.getFile("view.fxml");
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(f.toURL());
		    Parent root = (Parent) fxmlLoader.load();
			Scene scene= new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "ERROR @start\n"+ex.getMessage());}
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
	public void master_cancel()
	{
		master_field1.setText("");
		master_field2.setText("");
		master_field3.setText("");
		
		Platform.exit();
	}
	
	public void master_ok()
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
	public void device_chooser()
	{
		String path= IO.chooseDirection();
		
	    if (path!=null)
	    	device_direction.setText(path);
	    
		return;
	}
	
	public void device_cancel()
	{
		Platform.exit();
	}
	
	public void device_ok()
	{
		
		String msg="";
		try{
				File f= new File(device_direction.getText());
				
				String password = device_password1.getText();
				if(password.length()==0)
				{
					msg="\n- Empty field at: password";
					device_password1.selectAll();
				}
				else if (password.length()!=4)
				{
					msg="\n- Password must have exactly 4 characters";
					device_password1.selectAll();
				}
				else if (!device_password2.getText().contentEquals(password))
				{
					msg="\n- Passwords mismatch";
					device_password2.selectAll();
				}
				

				if ( ! f.exists() )
				{
					msg="\n- Not found direction."+msg;
					device_direction.selectAll();
				}
				
				if( !msg.isEmpty() )
				{
					JOptionPane.showMessageDialog(null,
							"ERROR"+msg, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					initUSB(f, password);
					JOptionPane.showMessageDialog(null, "Action completed");
					Platform.exit();
				}
			
		}catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "ERROR"+msg+"\n\nStack Trace:\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public static void initUSB( File root , String key )
	{		
		/*
		 * 1 Locker Folder
		 */
		File LockerFolder = new File(root.getAbsolutePath() +"/Locker");
		LockerFolder.mkdirs();
		/*
		 * 2 Config.txt
		 */
		try{
			File  ConfigFile= new File(root+"/.lockerconfig");
			ConfigFile.createNewFile();
	
			ArrayList<String> content= new ArrayList<String>();
			content.add(Commons.Enums.OPENED);
			content.add(key);
			
			IO.write( Commons.Enums.GENERIC_KEY , ConfigFile , content);
			
			//stores key in device log
			content= IO.read(Commons.Enums.GENERIC_KEY, new File(".devices"));
			content.add(key);
			IO.write(Commons.Enums.GENERIC_KEY, new File("/.lockerdevices"), content);
		}catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		/*
		 * 3 Runnable
		 */
		IO.writeRunnable(root);
		
		/*
		 * 4 Open.jar
		 */
		IO.CopyFile(IO.getFile("Open.jar"), new File(root.getPath()+"/Open.jar"));
		
		/*
		 * 5 Close.jar
		 */
		IO.CopyFile(IO.getFile("Close.jar"), new File(root.getPath()+"/Close.jar"));
	}
	
	
}