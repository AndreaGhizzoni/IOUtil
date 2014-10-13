package it.hackcaffebabe.ioutil.normalizer;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test class of {@link StringNormalizer}
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestStringNormalizer
{
	@Test
	public void testRemovingAccentedCharacters(){
		String t = StringNormalizer.removeAccentCharacters( "testàèéùìò" );
		String exp = "testaeeuio";
        Assert.assertEquals("Can remove accent characters.",exp, t);
		Assert.assertEquals( exp, t );
	}

	@Test
	public void testRemovingFileExtension(){
		String t = StringNormalizer.removeExtension( "test.ext" );
		String exp = "test";
		Assert.assertEquals("Can remove file extension.", exp, t );
	}

	@Test
	public void testRemovingPathCharacters(){
		String t = StringNormalizer.removePathCharacters( "\\/\t|-\n--asd" );
		String exp = "asd";
		Assert.assertEquals( "Can remove path characters.", exp, t );
	}

	@Test
	public void testRemovingAccentedCharactersFromEmptyString(){
		String t = StringNormalizer.removeAccentCharacters( "" );
		Assert.assertTrue( "Can not remove characters from empty string.",t.isEmpty() );
	}

	@Test
	public void testRemovingAccentedCharactersFromNullString(){
		String t = StringNormalizer.removeAccentCharacters( null );
		Assert.assertTrue( "Can not remove characters from null string.",t == null );
	}

	@Test
	public void testRemovingFileExtensionFromEmptyString(){
		String t = StringNormalizer.removeExtension( "" );
		Assert.assertTrue( "Can not remove file extension from empty string.",t.isEmpty() );
	}

	@Test
	public void testRemovingFileExtensionFromNullString(){
		String t = StringNormalizer.removeExtension( null );
		Assert.assertTrue( "Can not remove file extension from null string.",t == null );
	}

	@Test
	public void testRemovingPathCharactersFromEmptyString(){
		String t = StringNormalizer.removePathCharacters( "" );
		Assert.assertTrue( "Can not remove path characters from empty string.", t.isEmpty() );
	}

	@Test
	public void testRemovingPathCharactersFromNullString(){
		String t = StringNormalizer.removePathCharacters( null );
		Assert.assertTrue( "Can not remove path characters from empty string.", t == null );
	}
}
