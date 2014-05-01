package OpenClose;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import Commons.Enums;
import Commons.IO;
import Locker.lockerManager;

public class OpenClose {
	
	public static void main(String args[])
	{
		String msg="Action could not be completed.";
		
		try{
			
			File  ConfigFile= IO.getFile(".lockerconfig");
			ArrayList<String> content= new ArrayList<String>();
			
			content= IO.read(Commons.Enums.GENERIC_KEY, ConfigFile);
			
			if(!content.isEmpty() && content.get(0).contentEquals(Commons.Enums.OPENED))
				msg=Close(ConfigFile, content);
			else
				msg=Open(ConfigFile, content);
			
			
		}catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		JOptionPane.showMessageDialog(null, msg);
	}
	
	///////////////////////////////////////////////////////////////////////
	//CLOSE
	public static String Close( File ConfigFile, ArrayList<String> content)
	{
		String key= content.get(1);
		
		File f= new File(ConfigFile.getParent()+"/Locker");
		
		ArrayList <File> files = Commons.IO.directoryContent (f);
		lockerManager.hideFiles(key, files);
		
		content.remove(0);
		content.add(0, Commons.Enums.CLOSED);
		IO.write(key, ConfigFile, content);
		
		/*
		 * file rename
		 */
		try{
			File fileA= IO.getFile("Close.jar");
			File fileB= IO.getFile("Open.jar");
			fileA.renameTo(fileB);
		}catch(Exception ex){}
		
		return "Action Completed";
	}
	
	///////////////////////////////////////////////////////////////////////
	//OPEN
	public static String Open( File ConfigFile, ArrayList<String> content)
	{
		while(true)
		{
			JPasswordField pf = new JPasswordField();
			JOptionPane.showConfirmDialog(null, pf, "Introduce Key", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			
			String key = new String(pf.getPassword());
			
			if(key==null || key.length()==0)
				break;
			
			content= IO.read(key, ConfigFile);
			
			if(!content.isEmpty() && content.get(1).contentEquals(key))
			{
				if(content.get(0).contentEquals(Enums.CLOSED))
				{
					ArrayList <File> files = Commons.IO.directoryContent ( IO.getFile("Locker"));
					lockerManager.showFiles(key, files);
					
					content.remove(0);
					content.add(0, Commons.Enums.OPENED);
					IO.write(Commons.Enums.GENERIC_KEY, ConfigFile, content);
				}
				
				/*
				 * file rename
				 */
				try{
					File fileA= IO.getFile("Open.jar");
					File fileB= IO.getFile("Close.jar");
					fileA.renameTo(fileB);
				}catch(Exception ex){}
				
				return "Action Completed";
			}
		}
		
		return "Action could not be completed.";
	}
}
