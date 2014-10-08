package it.hackcaffebabe.ioutil.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Utility class that provide System constants and file system features.<br> 
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public final class PathUtil
{
	private PathUtil(){}

	/** File separator of OS*/
	public static final String FILE_SEPARATOR = System.getProperty( "file.separator" );
	/** Current Java Version*/
	public static final String JAVA_VERSION = System.getProperty( "java.version" );
	/** The OS Architecture*/
	public static final String OS_ARCHITECTURE = System.getProperty( "os.arch" );
	/** The OS Version*/
	public static final String OS_VERSION = System.getProperty( "os.version" );
	/** The OS Name*/
	public static final String OS_NAME = System.getProperty( "os.name" );
	/** The default path separator of OS*/
	public static final String PATH_SEPARATOR = System.getProperty( "path.separator" );
	/** The user home directory*/
	public static final String USER_HOME = System.getProperty( "user.home" );
	/** The User name*/
	public static final String USER_NAME = System.getProperty( "user.name" );
	/** The current Directory path of application*/
	public static final String CURRENT_DIRECTORY = System.getProperty( "user.dir" );

	/**
	 * This method copy recursively all content of source folder to destination folder.
	 * @param source {@link File} source folder to copy.
	 * @param destination {@link File} destination folder where source will be copied.
	 * @throws IOException if there are file system issues.
	 * @throws IllegalArgumentException if argument given are null, the same folder or destination folder is a file.
	 */
	public static synchronized void copyDirectory(File source, File destination) throws IOException,
			IllegalArgumentException{
		if(source == null || !source.exists())
			throw new IllegalArgumentException( "Source file given can not be null or must be exists." );

		if(source.equals( destination ))
			throw new IllegalArgumentException( "Source and Destination can not be the same file." );

		if(source.isDirectory() && destination.isFile())
			throw new IllegalArgumentException( "If Source is a Directory, destination file must be a Directory." );

		if(source.isDirectory()) {
			if(!destination.exists())
				destination.mkdir();

			String[] children = source.list();
			for(int i = 0; i < children.length; i++)
				copyDirectory( new File( source, children[i] ), new File( destination, children[i] ) );
		} else {
			InputStream in = new FileInputStream( source );
			OutputStream out = new FileOutputStream( destination );

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while( (len = in.read( buf )) > 0 )
				out.write( buf, 0, len );

			in.close();
			out.close();
		}
	}

	/**
	 * This method reads a file content line by line.
	 * @param path {@link File} the file to read content.
	 * @return {@link String} of his content.
	 * @throws IOException if read fails
	 * @throws IllegalArgumentException if argument given is null, not exists or is a directory.
	 */
	public static synchronized String readContent(File path) throws IOException, IllegalArgumentException{
		if(path == null)
			throw new IllegalArgumentException( "File to read can not be null." );
		if(!path.exists())
			throw new IllegalArgumentException( "File not exists." );
		if(path.isDirectory())
			throw new IllegalArgumentException( "File to read can not be a directory." );

		BufferedReader buf = new BufferedReader( new FileReader( path ) );
		StringBuffer sb = new StringBuffer();
		String line = buf.readLine();
		while( line != null ) {
			sb.append( String.format( "%s\n", line ) );
			line = buf.readLine();
		}
		buf.close();
		return sb.toString();
	}

	/**
	 * This method force to read all the content of file given (if exists and is not a directory) without throws no IOException.
	 * If throws, it returns the content read until that point. 
	 * @param path {@link File} the file to read content.
	 * @return {@link String} of his content.
	 * @throws IllegalArgumentException if argument given is null.
	 */
	public static synchronized String forceReadContent(File path) throws IllegalArgumentException{
		if(path == null)
			throw new IllegalArgumentException( "File to read can not be null." );
		if(!path.exists())
			throw new IllegalArgumentException( "File not exists." );
		if(path.isDirectory())
			throw new IllegalArgumentException( "File to read can not be a directory." );

		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader buf = new BufferedReader( new FileReader( path ) );
			String line = buf.readLine();
			while( line != null ) {
				sb.append( String.format( "%s\n", line ) );
				line = buf.readLine();
			}
			buf.close();
			return sb.toString();
		} catch(IOException e) {
			return sb.toString();
		}
	}
}