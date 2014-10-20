package it.hackcaffebabe.ioutil.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * This class provide the common method to create a Zip file.<br>
 * This class can archive ONLY file! For example:
 * <pre>{@code
 * File dir = new File("/path/to/dirs");
 * Zipper z = new Zipper("archive.zip");
 * z.addFile(dir);
 * z.forceZip();
 * }</pre>
 * In this case all the files from /path/to/dirs this folder are archived. No
 * folder structure are stored in the archive. Another example:
 * <pre>{@code
 * Zipper zip = new Zipper( new File("~/path/to/zip/arch.zip") );
 * for( File f : myListOfFiles )
 *    zip.addFile(f)
 * zip.softZip();
 * }</pre>
 * See doc of previous method to decide each one to use.
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public final class Zipper
{
	private File dst;
    private ArrayList<File> lst = new ArrayList<File>();

    /**
	 * Instance a Zipper object with the full path of the zip file.
	 * @param zip {@link File} must be a file .zip, otherwise will be
     *                        thrown an {@link IllegalArgumentException}.
	 * @throws IllegalArgumentException if arguments are null.
	 * @throws IOException if parent folder of zip can not be written.
	 */
    public Zipper(File zip) throws IllegalArgumentException, IOException{
        this.setZip(zip);
    }

//==============================================================================
// METHOD
//==============================================================================
	/**
	 * This method doesn't create the zip file if already exists into specified
     * directory, otherwise will be create.
	 * @throws IOException if already exists into specified directory or it
     * incurred a problem while opening the zip file.
	 */
	public void softZip() throws IOException{
		if(dst.exists())
			throw new IOException(String.format("%s already exists in %s", dst.getName(), dst.getParent()));
		zip();
	}

	/**
	 * This method create the zip file in every case. If zip already exists
     * it will deleted and a new one will be create.
	 * @throws IOException if it incurred a problem while creating the zip file. 
	 */
	public void forceZip() throws IOException{
		if(dst.exists() && !dst.delete())
            throw new IOException("Error while deleting existing zip file.");
		zip();
	}

	/* perform the zip creation */
	private synchronized void zip() throws IOException{
		ZipOutputStream zos = new ZipOutputStream( new FileOutputStream( this.dst ) );
		for(File f: this.lst) {
            zos.putNextEntry( new ZipEntry( shrinkPath(f) ));
            FileInputStream in = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            in.close();
            zos.closeEntry();
		}
		zos.close();
	}

    /* cuts the path of file given */
    private String shrinkPath(File f){
        String fullPath = f.getAbsolutePath();
        String parentPath = f.getParentFile().getAbsolutePath();
        int fpLength = fullPath.length();
        int ppLength = parentPath.length();
        return fullPath.substring(ppLength+1,fpLength);
    }

//==============================================================================
// SETTER
//==============================================================================
    /**
     * This method change the zip path.
     * @param f {@link File} the path of the zip archive.
     * @throws IllegalArgumentException if file given is null.
     */
	public void setZip(File f) throws IllegalArgumentException{
		if(f == null)
			throw new IllegalArgumentException("Zip file can not be null.");
        f.getParentFile().mkdirs();
		this.dst = f;
	}

    /**
     * This method add a list of files to the archive.
     * @param lst {@link java.util.List} of File to add.
     * @throws IllegalArgumentException if argument given is null or empty list.
     * @throws IOException if there is a file in the list that doesn't exists.
     */
	public void addAll(List<File> lst) throws IllegalArgumentException, IOException{
		if(lst == null)
			throw new IllegalArgumentException("List of file to zip can not be null.");
        if(lst.isEmpty())
            throw new IllegalArgumentException("List of file to zip can not be empty.");
        for( File f  : lst )
            add(f);
    }

    /**
     * This method add a single file or directory to the archive.
     * @param f {@link File} the file to add.
     * @throws IllegalArgumentException if file is null.
     * @throws IOException if file doesn't exists.
     */
    public void add(File f) throws IllegalArgumentException, IOException{
        if(f == null)
            throw new IllegalArgumentException("File to zip can not be null.");
        if(!f.exists())
            throw new IOException(String.format("File %s added does not exists.", f.getName()));
        if(f.isDirectory()){
            for(File file : f.listFiles() )
                add(file);
        }else{
            this.lst.add(f);
        }
    }

//==============================================================================
// GETTER
//==============================================================================
	/** @return {@link File} the zip path. */
	public File getPath(){
		return this.dst;
	}

	/** @return {@link ArrayList} of {@link File}. */
	public ArrayList<File> getFiles(){
		return this.lst;
	}
}
