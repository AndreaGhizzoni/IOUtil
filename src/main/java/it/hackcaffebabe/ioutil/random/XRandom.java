package it.hackcaffebabe.ioutil.random;

import java.util.Random;
import nl.flotsam.xeger.Xeger;


/**
 * Simple library that provide some methods to generate random data for testing purpose.<br>
 * This is made possible by Xeger class http://code.google.com/p/xeger/
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public final class XRandom
{
	private XRandom(){}

	/**
	 * This method generate a Random String with length and mode given.
	 * @param length {@link Integer} of the String
	 * @param mode {@link Mode} of String generation.<br>
	 * 		  String with all UPPER case, lower case, First Char Upper, BoTh Of ThEm or CamelCase.
	 * @return {@link String} generated random String.
	 * @throws IllegalArgumentException if argument given are null or length less or equal to 0
	 */
	public static String getRandomString(int length, Mode mode) throws IllegalArgumentException{
		if(length <= 0)
			throw new IllegalArgumentException( "Length of String given can not be null or <= 0." );
		if(mode == null)
			throw new IllegalArgumentException( "RegExp mode can not be null. Use RegExpMode enum." );

		switch( mode ) {
			case STRING_UPPER_CASE:
				return generate( String.format( "[A-Z]{%d}", length ) );
			case STRING_LOWER_CASE:
				return generate( String.format( "[a-z]{%d}", length ) );
			case STRING_UPPER_LOWER_CASE:
				return generate( String.format( "[a-zA-Z]{%d}", length ) );
			case STRING_FIRST_UPPER:
				return generate( String.format( "[A-Z][a-z]{%d}", (length - 1) ) );
			case STRING_CAMEL_CASE:
				return generate( String.format( "[A-Z][a-z]{%d}[A-Z][a-z]{%d}", ((length / 2) - 1),
						(length % 2 == 0 ? (length / 2) - 1 : (length / 2)) ) );
			default:
				return null;
		}
	}

	/**
	 * This method returns a random Integer number in range 1-999999999.
	 * @return {@link Integer} random Integer number.
	 */
	public static Integer getRandomIntNumber(){
		return Integer.parseInt( generate( "[0-9]{1,9}" ) );
	}

	/**
	 * This method returns a random number between minimum and maximum number ( included ). 
	 * @param min {@link Integer} the minimum number of range.
	 * @param max {@link Integer} the maximum number of range.
	 * @return {@link Integer} random number between minimum and maximum number.
	 * @throws IllegalArgumentException if minimum or maximum are less of zero, or minimum is greater than maximum.
	 */
	public static Integer getRandomIntNumber(int min, int max) throws IllegalArgumentException{
		if(min < 0)
			throw new IllegalArgumentException( "Minimum number can not be less of zero." );
		if(max <= 0)
			throw new IllegalArgumentException( "Maximum number can not be less of zero." );
		if(min >= max)
			throw new IllegalArgumentException( "Minimum can not be bigger than Maximum number." );

		return(min + new Random().nextInt( max - min ));
	}

	/**
	 * This method returns a random Integer number in range 1 to maxLength.
	 * Use this method if you want a range less of 1-9.
	 * @param maxLength {@link Integer} of decimal number length.
	 * @return {@link Integer} random Decimal number with length between 1 to maxLength.
	 * @throws IllegalArgumentException if argument are null or out of range 1 to 9.
	 */
	public static Integer getRandomIntNumber(int maxLength) throws IllegalArgumentException{
		if(maxLength < 1 || maxLength > 9)
			throw new IllegalArgumentException( "Max length given can not be null or out in range 1-9" );

		return Integer.parseInt( generate( String.format( "[0-9]{1,%d}", maxLength ) ) );
	}

	/**
	 * This method returns a random Hexadecimal number.
	 * @return {@link Integer} random Hexadecimal number.
	 */
	public static String getRandomHexNumber(){
		return Integer.toHexString( getRandomIntNumber() );
	}

	/**
	 * This method returns a random Binary number.
	 * @return {@link Integer} random Binary number.
	 */
	public static String getRandomBinNumber(){
		return Integer.toBinaryString( getRandomIntNumber() );
	}

	/**
	 * This method generates a random date in mode given.
	 * @param dateMode {@link Mode} the mode to generate the date.
	 * @return {@link String} representing the random date.
	 */
	public static String getRandomDate(Mode dateMode){
        if(dateMode == null)
            throw new IllegalArgumentException("Date mode can not be null.");

		String days = "(0?[1-9]|[12][0-9]|3[01])";
		String months = "(0?[1-9]|1[012])";
		String years = "(([1-2][1-9][1-9][1-9]))";
		String pattern = "%s/%s/%s";
		switch( dateMode ) {
			case DATE_IT_FORMAT:
				return generate( String.format( pattern, days, months, years ) );
			case DATE_SQL_DATABASE:
				return generate( String.format( pattern, years, months, days ) );
			case DATE_US_FORMAT:
				return generate( String.format( pattern, months, days, years ) );
			default:
				return null;
		}
	}

	/**
	 * This method return a random email in format uuuu.uuuu@dddd.ddd or uuuu.uuuu@dddd.dd
	 * @return {@link String} return a random email in format uuuu.uuuu@dddd.ddd or uuuu.uuuu@dddd.dd
	 */
	public static String getRandomEmail(){
		return generate( "[a-z]{4}[.][a-z]{4}[@][a-z]{4}[.]([a-z]{3}|[a-z]{2})" );
	}

	/**
	 * This method returns a random hours in format hh:mm:ss
	 * @return return a random hours in format hh:mm:ss
	 */
	public static String getRandomHours(){
		return generate( "(([0-1][0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))" );
	}

	/**
	 * This method returns a custom IPv4 as a String.
	 * @return {@link String} returns a custom IPv4 as a String.
	 */
	public static String getRandomIPv4(){
		return generate( "([.][0-2][0-5]{2}){4}" ).replaceAll( "[.][0]+", "." ).substring( 1 );
	}

	/**
	 * This is the core method. It's generates specific string from Regular Expression given.
	 * 
	 * ************************************************************************************
	 * ********************************* IMPORTANT ****************************************
	 * ************************************************************************************
	 * ==                                                                                ==
	 * == This method may be cause of StackOverflow with Regular Expression too complex. ==
	 * ==                                                                                ==
	 * ************************************************************************************
	 * ********************************* IMPORTANT ****************************************
	 * ************************************************************************************
	 * 
	 */
	private static String generate(String regex){
		return new Xeger( regex ).generate();
	}
}
