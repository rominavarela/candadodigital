package Commons;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JFileChooser;
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
			
		}catch (Exception ex){System.out.println("ERROR\n"+ex.getMessage());}
		
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
	public static File getFile(String name)
	{
		File f=new File(name);
		
		if(f.exists())
			return f;
		else
		{
			File ROOT	= new File(IO.class.getResource("/").getFile());
			f=new File(ROOT.getAbsolutePath().replace("%20", " ")+"/"+name);

			return f;
		}
	}
	
	public static ArrayList<File> directoryContent ( File where )
	{
		ArrayList<File> ans = new ArrayList<File>();
		
		if ( where.exists() && where.isDirectory() )
		{
			File[] fs = where.listFiles();
			
			for ( int i=0 ; i < fs.length ; i++ )
				ans.add(fs[i]);
		}
		
		return ans;
	}
	
	public static void writeRunnable( File where )
	{
		BufferedWriter writer= null;
		
		if ( where.exists() )		
		try{
			
			if(where.isDirectory())
				where=new File(where.getPath()+"/autorun.inf");
			
			writer= new BufferedWriter(new FileWriter(where));
			
			writer.write( "open=\"Open.jar\"" );
			
		}
		catch (Exception ex){}
		
		try{
			writer.close();
		}
		catch (Exception ex){}
		
	}
	
	public static void CopyFile(File fileA, File fileB) throws Exception	{
		FileInputStream		fin=  null;
		FileOutputStream	fout= null;
		byte[] 				data= null;
		
	  //convert file into array of bytes
		data = new byte[(int) fileA.length()];
		fin = new FileInputStream(fileA);
	    fin.read(data);
	    fin.close();
	    
	  //convert array of bytes into file
	    fout = new FileOutputStream(fileB); 
	    fout.write(data);
	    fout.close();
		
    }
	
	public static String chooseDirection()
	{
		JFileChooser chooser = new JFileChooser(); 
	    chooser.setDialogTitle("Pick your flash direction.");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
	    
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
	    	return chooser.getSelectedFile().getAbsolutePath().toString();
	    
		return null;
	}
	
}
