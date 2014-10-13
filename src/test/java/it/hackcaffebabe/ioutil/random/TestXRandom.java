package it.hackcaffebabe.ioutil.random;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class of {@link XRandom}
 */
public class TestXRandom {
    @Test
    public void testRandomStringWithLengthIllegal(){
        boolean t = false;
        try {
            XRandom.getRandomString(0, Mode.STRING_CAMEL_CASE);
        }catch(IllegalArgumentException e){
            t = true;
        }
        Assert.assertTrue("Can not get random string with length = 0.", t);
    }

    @Test
    public void testRandomStringWithModeNull(){
        boolean t = false;
        try {
            XRandom.getRandomString(1, null);
        }catch(IllegalArgumentException e){
            t = true;
        }
        Assert.assertTrue("Can not get random string with mode == null.", t);
    }

    @Test
    public void testRandomStringWithModeIllegal(){
        String t = XRandom.getRandomString(1, Mode.DATE_SQL_DATABASE);
        Assert.assertTrue("Can not get random string with illegal mode.", t==null);
    }

    @Test
    public void testRandomIntNumberWithMinIllegal(){
        boolean t = false;
        try {
            XRandom.getRandomIntNumber(-1, 1);
        }catch(IllegalArgumentException e){
            t = true;
        }
        Assert.assertTrue("Can not get random number with min range illegal.", t);
    }

    @Test
    public void testRandomIntNumberWithMaxIllegal(){
        boolean t = false;
        try {
            XRandom.getRandomIntNumber(1, -1);
        }catch(IllegalArgumentException e){
            t = true;
        }
        Assert.assertTrue("Can not get random number with max range illegal.", t);
    }

    @Test
    public void testRandomIntNumberWithMinMaxSwap(){
        boolean t = false;
        try {
            XRandom.getRandomIntNumber(1,0);
        }catch(IllegalArgumentException e){
            t = true;
        }
        Assert.assertTrue("Can not get random number with max < min.", t);
    }

    @Test
    public void testRandomIntNumberWithOutOfTheRange(){
        boolean t = false;
        try {
            XRandom.getRandomIntNumber(10);
        }catch(IllegalArgumentException e){
            t = true;
        }
        Assert.assertTrue("Can not get random number with length out of the range 0-1.", t);
    }

    @Test
    public void testRandomDateWithIllegalMode(){
        String t = XRandom.getRandomDate(Mode.STRING_CAMEL_CASE);
        Assert.assertTrue("Can not get random date from illegal mode.", t == null);
    }

    @Test
    public void testRandomDateWithNullMode(){
        boolean t = false;
        try {
            XRandom.getRandomDate(null);
        }catch(IllegalArgumentException e){
            t = true;
        }
        Assert.assertTrue("Can not get random date from null mode.", t);
    }
}
