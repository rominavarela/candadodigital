package Runnables;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import Commons.Enums;
import Commons.IO;
import Locker.lockerManager;

public class Open {
	
	public static void main(String args[])
	{
		new Open();
	}
	
	public Open()
	{
		String msg="Action could not be completed.";
		
		try{

			File  ConfigFile= IO.getFile(".lockerconfig");
			ArrayList<String> content= new ArrayList<String>();
			
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
					
					msg="Action Completed";
					break;
				}
			}
			
		}catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		JOptionPane.showMessageDialog(null, msg);
	}
}
