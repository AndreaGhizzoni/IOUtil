package it.hackcaffebabe.ioutil.regexp;

import static org.junit.Assert.*;
import static it.hackcaffebabe.ioutil.regexp.RegExp.*;
import org.junit.Test;

/**
 * Test class of {@link RegExp}
 */
public class TestRegExp {
    @Test
    public void testAll(){
        String numbers = "1231jff83r8gjagsf838";
        String lettersOne = "123asdasd";
        String lettersTwo = "asdasd123";
        String lettersThree = "123asdasd123";
        String lettersFour = "123as123dasd123";
        String special = "\nasd1243";
        String phone = "+12-1234567890";
        String email = "myfuckingemail.email@fuck.you";
        String dateSlash = "18/04/1993";
        String dateSep = "18%04%1993";
        String hours = "14:04:54";

        assertTrue("Can check if string contains numbers.", containsNumbers(numbers));
        assertTrue("Can check if string contains letters at the end.", containsLetters(lettersOne));
        assertTrue("Can check if string contains letters at the start.", containsLetters(lettersTwo));
        assertTrue("Can check if string contains letters at the start and end.", containsLetters(lettersThree));
        assertTrue("Can check if string contains letters at the start and end and middle.", containsLetters(lettersFour));
        assertTrue("Can check if string contains special characters.", containsSpecialCharacters(special, '\n'));
        assertTrue("Can check if string is a valid phone number.", isValidPhoneNumber(phone));
        assertTrue("Can check if string is a valid email address.", isValidEmail(email));
        assertTrue("Can check if string is a valid date.", isValidDate(dateSlash));
        assertTrue("Can check if string is a valid date whit different separator.", isValidDate(dateSep, '%'));
        assertTrue("Can check if string is a valid hours.", isValidHours(hours));
    }

    @Test
    public void testContainsNumbersWithNullString(){
        boolean t = false;
        try{
            containsNumbers(null);
        }catch (IllegalArgumentException e){
           t = true;
        }
        assertTrue("Can not check if contains numbers of null string.", t);
    }

    @Test
    public void testContainsNumbersWithEmptyString(){
        boolean t = false;
        try{
            containsNumbers("");
        }catch (IllegalArgumentException e){
           t = true;
        }
        assertTrue("Can not check if contains numbers of empty string.", t);
    }

    @Test
    public void testContainsLettersWithNullString(){
        boolean t = false;
        try{
            containsLetters(null);
        }catch (IllegalArgumentException e){
           t = true;
        }
        assertTrue("Can not check if contains letters of null string.", t);
    }

    @Test
    public void testContainsLettersWithEmptyString(){
        boolean t = false;
        try{
            containsLetters("");
        }catch (IllegalArgumentException e){
           t = true;
        }
        assertTrue("Can not check if contains letters of empty string.", t);
    }

    @Test
    public void testContainsSpecialCharactersWithNullString(){
        boolean t = false;
        try{
            containsSpecialCharacters(null, 'c');
        }catch (IllegalArgumentException e){
           t = true;
        }
        assertTrue("Can not check if contains special characters of null string.", t);
    }

    @Test
    public void testContainsSpecialCharactersWithEmptyString(){
        boolean t = false;
        try{
            containsSpecialCharacters("", 'c');
        }catch (IllegalArgumentException e){
           t = true;
        }
        assertTrue("Can not check if contains special characters of empty string.", t);
    }

    @Test
    public void testValidPhoneNumberWithNullString(){
        boolean t = false;
        try{
            isValidPhoneNumber(null);
        }catch (IllegalArgumentException e){
           t = true;
        }
        assertTrue("Can not check if is valid phone number of null string.", t);
    }

    @Test
    public void testValidPhoneNumberWithEmptyString(){
        boolean t = false;
        try{
            isValidPhoneNumber("");
        }catch (IllegalArgumentException e){
           t = true;
        }
        assertTrue("Can not check if is valid phone number of empty string.", t);
    }

    @Test
    public void testValidEmailWithNullString(){
        boolean t = false;
        try{
            isValidEmail(null);
        }catch (IllegalArgumentException e){
           t = true;
        }
        assertTrue("Can not check if is valid email of null string.", t);
    }

    @Test
    public void testValidEmailWithEmptyString(){
        boolean t = false;
        try{
            isValidEmail("");
        }catch (IllegalArgumentException e){
           t = true;
        }
        assertTrue("Can not check if is valid email of empty string.", t);
    }

    @Test
    public void testValidDateWithNullString(){
        boolean t = false;
        try{
            isValidDate(null);
        }catch (IllegalArgumentException e){
           t = true;
        }
        assertTrue("Can not check if is valid date of null string.", t);
    }

    @Test
    public void testValidDateWithEmptyString(){
        boolean t = false;
        try{
            isValidDate("");
        }catch (IllegalArgumentException e){
           t = true;
        }
        assertTrue("Can not check if is valid date of empty string.", t);
    }


}
