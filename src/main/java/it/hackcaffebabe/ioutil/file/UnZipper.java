package it.hackcaffebabe.ioutil.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * This class provide the common method to unzip a .zip file. How to use:
 * <pre>{@code
 * File src = new File("~/path/to/zip/arch.zip");
 * File dst = new File("~/");
 * UnZipper unzip = new UnZipper( src, dst );
 * System.out.println( unzip.listZipContent() ); //(To ONLY view the zip content)
 * unzip.unZip(); //(To unzip all the content)
 * }</pre>
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public final class UnZipper
{
	private File src;
	private File dst;
    private ArrayList<File> content = new ArrayList<File>();

	/**
	 * Instance the UnZipper object to unzip all the content into destination
     * folder given.
	 * @param zip {@link File} the zip source file.
	 * @param dstFolder {@link File} the destination folder to unzip
     *                              all the content.
	 * @throws IllegalArgumentException if argument given are null.
	 * @throws IOException if into destination folder given can be written the
     * zip content.
	 */
	public UnZipper(File zip, File dstFolder) throws IllegalArgumentException, IOException{
		this.setZipSource( zip );
		this.setDestinationFolder( dstFolder );
	}

//==============================================================================
// METHOD
//==============================================================================
	/**
	 * This method list all the zip content.
	 * @return {@link ArrayList} of zip content as {@link File}
	 * @throws IOException if it incurred a problem while opening the zip file. 
	 */
	public synchronized ArrayList<File> listZipContent() throws IOException{
        this.content.clear();
		ZipInputStream zis = new ZipInputStream( new FileInputStream( this.src ) );
		ZipEntry ze = zis.getNextEntry();
		while( ze != null ){
			this.content.add( new File(ze.getName() ) );
			ze = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
        return this.content;
	}

	/**
	 * This method unzip all the zip content into the destination folder given.
	 * Pass true as argument if you want to skip the file if it's already exists
     * into destination folder,
	 * otherwise it will be rewritten.
	 * @param skipIfExists {@link Boolean} true to skip the file if already
     *                                    exists, otherwise false.
	 * @return {@link List} of File that are unzipped into destination folder.
	 * @throws IOException if it incurred a problem while extracts files. 
	 */
	public synchronized List<File> unZipAll(boolean skipIfExists) throws IOException{
		List<File> lstFileLoaded = new ArrayList<File>();
		ZipInputStream zis = new ZipInputStream( new FileInputStream( this.src ) );
		ZipEntry ze;
        File tmp;
		while( (ze = zis.getNextEntry()) != null ) {
            tmp = unzipFile(zis, ze, skipIfExists);
            if(tmp != null)
                lstFileLoaded.add( tmp );
        }
		zis.closeEntry();
		zis.close();
		return lstFileLoaded;
	}

	/**
	 * This method extract from zip all the file that are into the list of
     * {@link String} given. The string inside the list must be exactly the same
     * as the name of file into the zip, otherwise this method will skip it.
	 * @param lstFilesToUnzip {@link List} of String to match with the name of
     *                                    file inside the zip. if null this
     *                                    method returns null.
	 * @return {@link List} of file extracted in this way.
	 * @throws IOException if it incurred a problem while extract files.
	 */
	public synchronized List<File> unZipSelective(List<String> lstFilesToUnzip) throws IOException{
		if(lstFilesToUnzip == null)
			return null;

		List<File> filesLoaded = new ArrayList<File>();
		ZipInputStream zis = new ZipInputStream( new FileInputStream( this.src ) );
		ZipEntry ze;
        File newFile;
		while( ( ze = zis.getNextEntry() )!= null ) {
			if(lstFilesToUnzip.contains( ze.getName() )) {
                newFile = unzipFile(zis,ze, false);
                if(newFile != null);
                    filesLoaded.add( newFile );
			}
		}
		zis.closeEntry();
		zis.close();
		return filesLoaded;
	}

    /* this method unzip a single file */
    private synchronized File unzipFile(ZipInputStream zis, ZipEntry ze, boolean skipIfExists) throws IOException{
        File tmp = new File( String.format("%s%s%s", this.dst,PathUtil.FILE_SEPARATOR,ze.getName()) );
        if(tmp.exists() && skipIfExists )//if file already exists, skip it
            return null;

        if(ze.isDirectory()){
            tmp.mkdirs();
            return null;
        }else{
            byte[] buffer = new byte[1024];
            FileOutputStream fos = new FileOutputStream( tmp );
            int len;
            while( (len = zis.read( buffer )) > 0 ) {
                fos.write( buffer, 0, len );
            }
            fos.close();
        }
        return tmp;
    }

//==============================================================================
// SETTER
//==============================================================================
	/* set the zip source file. */
	private void setZipSource(File zip) throws IllegalArgumentException, IOException{
		if(zip == null)
			throw new IllegalArgumentException( "Source Zip file can not to be null." );
		if(!zip.getName().toLowerCase().endsWith( ".zip" ))
			throw new IllegalArgumentException( "Source must be a zip file." );

		this.src = zip;
	}

	/* set the destination folder to unzip the source file. */
	private void setDestinationFolder(File folder) throws IllegalArgumentException, IOException{
		if(folder == null)
			throw new IllegalArgumentException( "Destination folder can not to be null." );
		if(!folder.isDirectory())
			throw new IllegalArgumentException( "Destination must be a folder." );
		if(!folder.canWrite())
			throw new IOException( "Files can not be unzipped in: " + folder.toString() );

		folder.mkdirs();
		this.dst = folder;
	}

//==============================================================================
// GETTER
//==============================================================================
	/** @return {@link File} the source zip file. */
	public File getSourceZipFile(){
		return this.src;
	}

	/** @return {@link File} the destination folder. */
	public File getDestinationFolder(){
		return this.dst;
	}

    /** @return {@link ArrayList} of {@link File} that are the zip content. */
    public ArrayList<File> getContent(){
        if(this.content == null) {
            try {
                return listZipContent();
            } catch (IOException e) {
                return null;
            }
        }else{
            return this.content;
        }
    }
}
