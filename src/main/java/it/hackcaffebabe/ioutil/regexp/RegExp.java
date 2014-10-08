package it.hackcaffebabe.ioutil.regexp;

/**
 * Simple class that provide the most common regular expression validation.<br>
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class RegExp
{
	private static final String CONTAINS_NUMBERS = ".*\\d.*";
	private static final String CONTAINS_LETTERS = "^[a-zA-Z]+$";
	private static final String CONTAINS_SPECIAL_CHARACTERS = ".*[\\s?]+.*";
	private static final String IS_VALID_EMAIL = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String IS_VALID_DATE = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
	private static final String IS_VALID_HOURS = "^(([0-1]?[0-9])|([2][0-4])):([0-5]?[0-9])(:([0-5]?[0-9]))?$";
	private static final String IS_VALID_PHONE = "\\+\\d{2}\\-\\d{10}";

	/**
	 * Checks string with a Regular Expression given.
	 */
	private static boolean check(String stringToCheck, String regexp) throws IllegalArgumentException{
		if(stringToCheck == null || stringToCheck.isEmpty())
			throw new IllegalArgumentException( "String to check can not be null or empty." );

		return stringToCheck.matches( regexp );
	}

	/**
	 * Static method to check if a string contains some digits characters.
	 * @param stringToCheck {@link String} string to check with regular expression.
	 * @return {@link Boolean} true if string contains numbers, otherwise false.
	 * @throws IllegalArgumentException if argument given is null or empty.
	 */
	public static boolean containsNumbers(String stringToCheck) throws IllegalArgumentException{
		return check( stringToCheck, CONTAINS_NUMBERS );
	}

	/**
	 * Static method to check if a string contains ONLY letters characters.
	 * @param stringToCheck {@link String} string to check with regular expression.
	 * @return {@link Boolean} true if string contains only letters, otherwise false.
	 * @throws IllegalArgumentException if argument is null or empty.
	 */
	public static boolean containsLetters(String stringToCheck) throws IllegalArgumentException{
		return check( stringToCheck, CONTAINS_LETTERS );
	}

	/**
	 * This method check if into the string given are present some of characters given whitespace character, short for [\t\n\x0b\r\f].
	 * @param stringToCheck {@link String} the string to check if some characters are present.
	 * @param specialCharacters {@link Character} the characters to check.
	 * @return {@link Boolean} true if some characters are present into the string, otherwise false.
	 * @throws IllegalArgumentException if argument given are null or empty characters.
	 */
	public static boolean containsSpecialCharacters(String stringToCheck, char... specialCharacters)
			throws IllegalArgumentException{
		if(specialCharacters.length == 0)
			throw new IllegalArgumentException( "You need to specify some special characters to check." );

		StringBuilder builder = new StringBuilder();
		for(char c: specialCharacters)
			if(c == '[' || c == ']' || c == '|')
				builder.append( "\\\\" + c );
			else builder.append( c );

		return check( stringToCheck, CONTAINS_SPECIAL_CHARACTERS.replace( "?", builder.toString() ) );
	}

	/**
	 * Check if a String is a Phone number in format "+prefix-number" ( without quotes ).
	 * @param phoneNumebr {@link String} the complete phone number in format "+prefix-number" ( without quotes ).
	 * @return {@link Boolean} true if the phone number is in format "+prefix-number" ( without quotes ).
	 * @throws IllegalArgumentException if lengths given are less or equal of zero or phone number is null or empty.
	 */
	public static boolean isValidPhoneNumber(String phoneNumebr) throws IllegalArgumentException{
		return check( phoneNumebr, IS_VALID_PHONE );
	}

	/**
	 * Check if the String given is a valid email.
	 * @param email {@link String} string to check with regular expression.
	 * @return {@link Boolean} true if the string given is a valid email, otherwise false.
	 * @throws IllegalArgumentException if argument given is null or empty.
	 */
	public static boolean isValidEmail(String email) throws IllegalArgumentException{
		return check( email, IS_VALID_EMAIL );
	}

	/**
	 * Check if a date is in format dd/mm/yyyy.
	 * @param date {@link String} string to check with regular expression.
	 * @return {@link Boolean} true if date is in format dd/mm/yyyy and his fields are correct, otherwise false.
	 * @throws IllegalArgumentException if argument are null or empty.
	 */
	public static boolean isValidDate(String date) throws IllegalArgumentException{
		return check( date, IS_VALID_DATE );
	}

	/**
	 * Check if a date is in format dd/mm/yyyy.
	 * @param date {@link String} string to check with regular expression.
	 * @param separator {@link Character} that represent the separator of data fields. It can not be a digits.
	 * @return {@link Boolean} true if date is in format dd/mm/yyyy and his fields are correct, otherwise false.
	 * @throws IllegalArgumentException if argument are null or empty.
	 */
	public static boolean isValidDate(String date, char separator) throws IllegalArgumentException{
		if(RegExp.containsNumbers( Character.toString( separator ) ))
			throw new IllegalArgumentException( "Separator can not be a number." );

		if("()[]{}".contains( String.valueOf( separator ) ))
			throw new IllegalArgumentException( "Separator can not be one of those parenthesis." );

		return check( date, "(0?[1-9]|[12][0-9]|3[01])" + separator + "(0?[1-9]|1[012])" + separator
				+ "((19|20)\\d\\d)" );
	}

	/**
	 * Check if the hours is in format hh:mm:ss.
	 * @param hours {@link String} string to check with regular expression.
	 * @return {@link Boolean} true if hours is in format hh:mm:ss and his fields are correct, otherwise false.
	 * @throws IllegalArgumentException if argument are null or empty.
	 */
	public static boolean isValidHours(String hours) throws IllegalArgumentException{
		return check( hours, IS_VALID_HOURS );
	}
}
