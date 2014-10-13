package it.hackcaffebabe.ioutil.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * This class provide the common method to create a Zip file.<br>
 * How to use:<br>
 * <pre>{@code
 * Zipper zip = new Zipper( new File("~/path/to/zip/arch.zip"), new File[]{new File("a.dat"), new File("b.json")} );
 * zip.softZip();
 * (or)
 * zip.forceZip()
 * }</pre>
 * See doc of previous method to decide each one to use.
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public final class Zipper
{
	private File dst;
	private File[] lst;

	/**
	 * Instance a Zipper object with folder destination and an array of file to put into it.
	 * @param zipFile {@link File} must be a file .zip, otherwise will be thrown an {@link IllegalArgumentException}.
	 * @param fileList array of Files to put into the zip.
	 * @throws IllegalArgumentException if arguments are null.
	 * @throws IOException if parent folder of zipFile can not be written.
	 */
	public Zipper(File zipFile, File[] fileList) throws IllegalArgumentException, IOException{
		this.setZipFile( zipFile );
		this.setFileList( fileList );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * This method dosn't create the zip file if already exists into specified directory, otherwise will be create.
	 * @throws IOException if already exists into specified directory or it incurred a problem while opening the zip file. 
	 */
	public void softZip() throws IOException{
		if(dst.exists())
			throw new IOException( String.format( "%s already exists in %s", dst.getName(), dst.getParent() ) );
		zip();
	}

	/**
	 * This method create the zip file in every case. If zip already exists it will deleted and a new one will be create.
	 * @throws IOException if it incurred a problem while creating the zip file. 
	 */
	public void forceZip() throws IOException{
		if(dst.exists())
			dst.delete();
		zip();
	}

	/* perform the zip creation */
	private synchronized void zip() throws IOException{
		ZipOutputStream zos = new ZipOutputStream( new FileOutputStream( this.dst ) );
		for(File f: this.lst) {
			if(f != null) {
				zos.putNextEntry( new ZipEntry( f.getName() ) );
				FileInputStream in = new FileInputStream( f );
				byte[] buffer = new byte[1024];
				int len;
				while( (len = in.read( buffer )) > 0 ) {
					zos.write( buffer, 0, len );
				}
				in.close();
				zos.closeEntry();
			}
		}
		zos.close();
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/* set the zip destination path */
	private void setZipFile(File f) throws IllegalArgumentException, IOException{
		if(f == null)
			throw new IllegalArgumentException( "Zip file can not be null." );
		if(!f.getParentFile().canWrite())
			throw new IOException( "Zip file can not be written on: " + f.getParentFile().toString() );
		if(f.isDirectory())
			throw new IllegalArgumentException( "Zip path can not be a directory." );
		f.mkdirs();
		this.dst = f;
	}

	/* set the zip content */
	private void setFileList(File[] lst) throws IllegalArgumentException{
		if(lst == null)
			throw new IllegalArgumentException( "List of file to zip can not be null." );
		this.lst = lst;
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/** @return {@link File} the zip path. */
	public File getDestinationFolder(){
		return this.dst;
	}

	/** @return Array of {@link File} zip content. */
	public File[] getFiles(){
		return this.lst;
	}
}
