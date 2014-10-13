package it.hackcaffebabe.ioutil.file;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Test class of {@link IOSerializable}
 */
public class TestIOSerializable {

    @Test
    public void testSaveAndLoad(){
        File f = new File("/tmp/test.ser");
        Integer i = 42;
        try {
            IOSerializable.save(i, f);
            i = null;
            i = IOSerializable.load(Integer.class, f);
        }catch(Exception e){
            Assert.fail();
        }
        Assert.assertTrue("Can save and load correctly an object.", i.equals(42));
    }

    @Test
    public void testSaveWithPathNull(){
        File f = null;
        Integer obj = 42;
        boolean t = false;
        try{
            IOSerializable.save(obj, f);
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not serialize with null path.", t);
    }

    @Test
    public void testSaveWithPathInvalid(){
        File f = new File("/tmp");
        Integer obj = 42;
        boolean t = false;
        try{
            IOSerializable.save(obj, f);
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not serialize with invalid path.", t);
    }

    @Test
    public void testSaveWithNullObject(){
        File f = new File("/tmp/ser.test");
        Integer obj = null;
        boolean t = false;
        try{
            IOSerializable.save(obj, f);
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not serialize with null Object.", t);
    }

    @Test
    public void testLoadIfNotExistingFile(){
        File f = new File("/tmp/ImNotExist.test");
        boolean t = false;
        try{
            IOSerializable.load(Integer.class, f);
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not load Object from not existing file.", t);
    }

    @Test
    public void testLoadFromClassToCastNull(){
        File f = new File("/tmp/ser.test");
        Integer i = 42;
        boolean t = false;
        try{
            IOSerializable.save(i,f);
            IOSerializable.load(null, f);
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not load Object from null class to cast.", t);
    }

    @Test
    public void testLoadFromDifferentClass(){
        File f = new File("/tmp/ser.test");
        Integer i = 42;
        boolean t = false;
        try{
            IOSerializable.save(i,f);
            IOSerializable.load(String.class, f);
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not load Object from String class to Integer.", t);
    }

    @Test
    public void testLoadFromNullPath(){
        File f = new File("/tmp/ser.test");
        Integer i = 42;
        boolean t = false;
        try{
            IOSerializable.save(i,f);
            IOSerializable.load(Integer.class, null);
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not load Object from null path.", t);
    }

    @Test
    public void testLoadFromInvalidPath(){
        File f = new File("/tmp/ser.test");
        Integer i = 42;
        boolean t = false;
        try{
            IOSerializable.save(i,f);
            IOSerializable.load(Integer.class, new File("/tmp"));
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not load Object from invalid path.", t);
    }

}
