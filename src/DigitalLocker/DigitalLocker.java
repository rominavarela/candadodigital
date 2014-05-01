package DigitalLocker;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Commons.IO;

import javafx.application.*;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class DigitalLocker {
	
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
	
	public void device_ok()
	{
		String msg="";
		try{
				File f= new File(device_direction.getText());
				
				String password = device_password1.getText();
				if(password.length()==0)
				{
					msg="\n- Empty field at: key";
					device_password1.selectAll();
				}
				else if (password.length()!=4)
				{
					msg="\n- Key must have exactly 4 characters";
					device_password1.selectAll();
				}
				else if (!device_password2.getText().contentEquals(password))
				{
					msg="\n- Keys mismatch";
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
	
	private static void initUSB( File root , String key )
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
		 * 4 Open/Close.jar
		 */
		File cp=null;
		
		try{
			cp=IO.getFile("Open.jar");
			IO.CopyFile(cp, new File(root.getPath()+"/Close.jar"));
			
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "ERROR"+cp.getAbsolutePath()+"\n\n"+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			try{
				
				cp=IO.getFile("Close.jar");
				IO.CopyFile(cp, new File(root.getPath()+"/Close.jar"));
				
			}catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, "ERROR"+cp.getAbsolutePath()+"\n\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	///////////////////////////////////////////////////////////
	// SETTINGS TAB
	/*
	 * Objects
	 */
	
	@FXML private TextField 	settings_direction;
	@FXML private PasswordField settings_key1;
	@FXML private PasswordField settings_key2;
	@FXML private PasswordField settings_key3;
	
	/*
	* Actions
	*/
	public void settings_chooser()
	{
		String path= IO.chooseDirection();
		
	    if (path!=null)
	    	settings_direction.setText(path);
	    
		return;
	}
	
	public void settings_ok()
	{
		String msg="";
		
		try{
			/*
			 * checks input
			 */
				String password = settings_key2.getText();
				if(password.length()==0)
				{
					msg="\n- Empty field at: new key";
					settings_key2.selectAll();
				}
				else if (password.length()!=4)
				{
					msg="\n- Key must have exactly 4 characters";
					settings_key2.selectAll();
				}
				else if (!settings_key3.getText().contentEquals(password))
				{
					msg="\n- Keys mismatch";
					settings_key3.selectAll();
				}
				
			/*
			 * checks device direction
			 */
				File root= new File(settings_direction.getText());
				
				if ( ! root.exists() )
				{
					msg="\n- Not found direction."+msg;
					settings_direction.selectAll();
				}
				else
				{
				/*
				 * checks actual password
				 */
					File  ConfigFile= new File(root+"/.lockerconfig");
					ArrayList<String> content = IO.read(Commons.Enums.GENERIC_KEY, ConfigFile);
					String actual= content.get(1);
					
					if(settings_key1.getText().contentEquals(actual))
					{
					/*
					 * success
					 */
						content.remove(1);
						content.add(password);
						IO.write(Commons.Enums.GENERIC_KEY, ConfigFile, content);
					}
					else
					{
						msg= "\n- Actual key is wrong"+msg;
						settings_key1.selectAll();
					}
				}
			
		}catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "ERROR"+msg+"\n\nStack Trace:\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		/*
		 * final message
		 */
		if( !msg.isEmpty() )
		{
			JOptionPane.showMessageDialog(null,
					"ERROR"+msg, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Successful Operation");
			Platform.exit();
		}
		
	}
	
	public void cancel()
	{
		Platform.exit();
	}
	
	
}