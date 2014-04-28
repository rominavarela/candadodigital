package Locker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class lockerManager {
	
	public static void main(String args[])
	{
		ArrayList <File> files = Commons.IO.directoryContent ( "/home/romina/PDF/Locker" );
		hideFiles("lo que sea", files);
	}
	
	public static void hideFiles ( String key , ArrayList <File> files )
	{
		for( File file : files )
			if(file.isFile())
				hideFile(key,file);
	}
	
	static void hideFile ( String key , File file )
	{
		if(!file.exists())
			return;
		
		FileInputStream		fin=  null;
		FileOutputStream	fout= null;
		byte[] 				data= null;
		
		try
		{
		  //convert file into array of bytes
			data = new byte[(int) file.length()];
			fin = new FileInputStream(file);
		    fin.read(data);
		    fin.close();
		    
		  //alterates bytes
		    for( int i=0; i<data.length; i++)
			{
				data[i]=(byte) (Byte.MAX_VALUE-data[i]);
			}
		 
		  //convert array of bytes into file
		    fout = new FileOutputStream(file); 
		    fout.write(data);
		    fout.close();
		}
		catch (Exception ex){System.out.println(ex.getLocalizedMessage());}
		
	}
	
	public static void showFiles ( String key , ArrayList<File> files )
	{
		for( File file : files )
			showFile(key,file);
	}
	
	static void showFile ( String key , File file )
	{
		hideFile ( key , file );
	}

}
