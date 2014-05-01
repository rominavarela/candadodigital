package Runnables;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Commons.IO;
import Locker.lockerManager;

public class Close {
	public static void main(String args[])
	{
		new Close();
	}
	
	public Close()
	{
		String msg="Action could not be completed.";
		
		try{
			
			File  ConfigFile= IO.getFile(".lockerconfig");
			ArrayList<String> content= new ArrayList<String>();
			
			content= IO.read(Commons.Enums.GENERIC_KEY, ConfigFile);
			
			if(!content.isEmpty() && content.get(0).contentEquals(Commons.Enums.OPENED))
			{
				String key= content.get(1);
				
				File f= new File(ConfigFile.getParent()+"/Locker");
				
				ArrayList <File> files = Commons.IO.directoryContent (f);
				lockerManager.hideFiles(key, files);
				
				content.remove(0);
				content.add(0, Commons.Enums.CLOSED);
				IO.write(key, ConfigFile, content);
				msg="Action Completed";
			}
			
		}catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		JOptionPane.showMessageDialog(null, msg);
	}
}
