package Commons;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class IO {
	
	public static ArrayList<String> read( String key , File where )
	{
		ArrayList<String> ans = new ArrayList<String>();
		BufferedReader reader= null;
		
		if ( where.exists() )		
		try{
			
			reader= new BufferedReader(new FileReader(where));
			
			textEncryptor encryptor = new textEncryptor();
			encryptor.setKey(key);
			
			String s;
			while(true)
			{
				s=reader.readLine();
				if ( s== null || s.isEmpty() )
					break;
				ans.add(encryptor.open(s));
			}
			
		}catch (Exception ex){}
		
		try{
			reader.close();
		}
		catch (Exception ex){}
		
		return ans;
	}
	
	public static void write( String key , File where , ArrayList<String> what )
	{
		BufferedWriter writer= null;
		
		if ( where.exists() )		
		try{
			
			writer= new BufferedWriter(new FileWriter(where));
			
			textEncryptor encryptor = new textEncryptor();
			encryptor.setKey(key);
			
			for ( String line : what )
				writer.write( encryptor.close(line) + "\n" );
			
		}
		catch (Exception ex){}
		
		try{
			writer.close();
		}
		catch (Exception ex){}
		
	}
	
	public static ArrayList<File> directoryContent ( String where )
	{
		ArrayList<File> ans = new ArrayList<File>();
		
		File directory= new File ( where );
		if ( directory.exists() && directory.isDirectory() )
		{
			File[] fs = directory.listFiles();
			
			for ( int i=0 ; i < fs.length ; i++ )
				ans.add(fs[i]);
		}
		
		return ans;
	}
	
}
