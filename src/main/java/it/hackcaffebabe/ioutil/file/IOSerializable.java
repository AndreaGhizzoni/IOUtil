package it.hackcaffebabe.ioutil.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * This class facilitates the Input/Output for serialization operations.<br>
 * For example use this code to store an Integer object on the
 * path "sys/foo/int/int.ser":<br>
 * <pre>{@code
 * Integer i = new Integer( 42 );
 * IOSerializable.save( i, new File( "sys/foo/int/int.ser" ) );
 * }</pre>
 * And finally use this to retrieve information from serialized
 * object ( for example the previous Integer object ):
 * <pre>{@code
 * Integer serializedObject = IOSerializable.load( Integer.class, new File( "sys/foo/int/int.ser" ) );
 * }</pre>
 *
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public final class IOSerializable
{
	private IOSerializable(){}

	/**
	 * This method serialize an object on a {@link File} given.<br>
	 * All file specification are set by {@link File}
     * argument ( name, extension, path... ).<br>The file could not be exists,
     * it will be created when the object will be written on it, but the path
     * MUST be exists. Otherwise will be throw a {@link FileNotFoundException}.
	 * 
	 * @param serializableFile {@link Object} the object to serialize on file.
	 * @param onFile {@link File} the file to store on disk the serialized object.
	 * @throws IllegalArgumentException if <code>serializedObject</code> is null
     * or <code>onFile</code> is null or a directory.
	 * @throws IOException if there are issues to store the file or the path
     * of file is not valid. In this case will be throw a {@link FileNotFoundException}.
	 */
	public synchronized static void save(Serializable serializableFile, File onFile) throws IllegalArgumentException,
			IOException{
		if(serializableFile == null)
			throw new IllegalArgumentException( "Object given can not be null!" );

		if(onFile == null || onFile.isDirectory())
			throw new IllegalArgumentException( "File name can not be null or a directory!" );

		ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream( onFile ) );
		out.writeObject( serializableFile );
        out.flush();
		out.close();
	}

	/**
	 * This method load an serialized object from file.<br>
	 * The file MUST be exists, otherwise will be throw
     * a {@link FileNotFoundException}.
	 * @param classToCast {@link Class} the class to cast the read object.
	 * @param fromFile {@link File} the file to read the serialized object.
	 * @return object of class T, instance of class given by argument
     * <code>classToCast</code>.
	 * @throws ClassNotFoundException if class to cast not implements
     * {@link Serializable}.
	 * @throws IllegalArgumentException if argument are null or empty.
	 * @throws IOException if method can not read file.
	 */
	public synchronized static <T> T load(Class<T> classToCast, File fromFile) throws IllegalArgumentException,
			IOException, ClassNotFoundException{
		if(classToCast == null)
			throw new IllegalArgumentException( "Class to cast can not be null!" );

		if(fromFile == null || fromFile.isDirectory())
			throw new IllegalArgumentException( "File name can not be null or void!" );

		ObjectInputStream in = new ObjectInputStream( new FileInputStream( fromFile ) );
		T toReturn = classToCast.cast( in.readObject() );
		in.close();
		return toReturn;
	}
}
