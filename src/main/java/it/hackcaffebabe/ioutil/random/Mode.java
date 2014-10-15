package it.hackcaffebabe.ioutil.random;

/**
 * Enumeration that describe some type of random object gets from {@link XRandom}.
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public enum Mode
{
	/** Represents the date in format yyyy-mm-dd */
	DATE_SQL_DATABASE,

	/** Represents the date in format dd/mm/yyyy */
	DATE_IT_FORMAT,

	/** Represents the date in format mm/dd/yyyy */
	DATE_US_FORMAT,

	/** THE STRING UPPER CASE */
	STRING_UPPER_CASE,

	/** The String with First Character Upper */
	STRING_FIRST_UPPER,

	/** the string with lower case */
	STRING_LOWER_CASE,

	/** ThE StRiNg UPPeR anD LoWer CASe*/
	STRING_UPPER_LOWER_CASE,

	/** TheStringCamelCase*/
	STRING_CAMEL_CASE
}
