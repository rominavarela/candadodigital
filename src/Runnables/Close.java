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
		try{
			
			File  ConfigFile= new File(".config");
			ArrayList<String> content= new ArrayList<String>();
			
			content= IO.read(Commons.Enums.GENERIC_KEY, ConfigFile);
			
			if(!content.isEmpty() && content.get(0).contentEquals(Commons.Enums.OPENED))
			{
				String key= content.get(1);
				
				ArrayList <File> files = Commons.IO.directoryContent ( "Locker");
				lockerManager.hideFiles(key, files);
				
				content.remove(0);
				content.add(0, Commons.Enums.CLOSED);
				IO.write(key, ConfigFile, content);
			}
			
		}catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
