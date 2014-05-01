package Runnables;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
		try{

			File  ConfigFile= IO.getFile(".config");
			ArrayList<String> content= new ArrayList<String>();
			
			while(true)
			{
				String key=JOptionPane.showInputDialog(null, "Introduce Key");
				
				if(key==null || key.length()==0)
					break;
				
				content= IO.read(key, ConfigFile);
				
				if(!content.isEmpty() && content.get(1).contentEquals(key))
				{
					if(content.get(0).contentEquals(Enums.CLOSED))
					{
						ArrayList <File> files = Commons.IO.directoryContent ("Locker");
						lockerManager.showFiles(key, files);
						
						content.remove(0);
						content.add(0, Commons.Enums.OPENED);
						IO.write(Commons.Enums.GENERIC_KEY, ConfigFile, content);
					}
					break;
				}
			}
			
		}catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
