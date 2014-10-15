package it.hackcaffebabe.ioutil.file;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Test class of {@link Zipper}
 */
public class TestZipper {
    @Test
    public void testZipperFirst(){
        try{
            String zipPath = "/tmp/testFirst.zip";
            File zip = new File(zipPath);
            if(zip.exists())
                zip.delete();

            Zipper z = new Zipper(zip);
            File tmp;
            for(int i = 0; i<5; i++) {
                tmp = new File(String.format("/tmp/fileFirst%s.txt", i) );
                tmp.createNewFile();
                z.addFile(tmp);
            }
            z.softZip();
        }catch (IllegalArgumentException ei){
            Assert.fail(ei.getMessage());
        }catch (IOException eio){
            Assert.fail(eio.getMessage());
        }
    }

    @Test
    public void testZipperSecond(){
        try{
            String zipPath = "/tmp/testFirst.zip";
            File zip = new File(zipPath);
            Zipper z = new Zipper(zip);
            ArrayList<File> lst = new ArrayList<File>();
            File tmp;
            for(int i = 0; i<5; i++) {
                tmp = new File(String.format("/tmp/fileSecond%s.txt", i) );
                tmp.createNewFile();
                lst.add(tmp);
            }
            z.addFiles(lst);
            zip.createNewFile();
            z.forceZip();
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testZipperContent(){
        try{
            String zipPath = "/tmp/testFirst.zip";
            File zip = new File(zipPath);
            Zipper z = new Zipper(zip);
            ArrayList<File> lst = new ArrayList<File>();
            File tmp;
            for(int i = 0; i<5; i++) {
                tmp = new File(String.format("/tmp/fileSecond%s.txt", i) );
                tmp.createNewFile();
                lst.add(tmp);
            }
            z.addFiles(lst);

            Assert.assertTrue("Can add a list of Files.", lst.equals(z.getFiles()));
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testZipperDestinationFolder(){
        try{
            Zipper z = new Zipper(new File( "/tmp/testFirst.zip"));
            File nevv = new File("/tmp/new/test.zip");
            z.setZip(nevv);

            Assert.assertTrue("Can change the zip path.", nevv.equals(z.getPath()));
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testZipWithNullDestination(){
        boolean t = false;
        try {
            new Zipper(null);
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not instance a Zipper with null destination.", t);
    }

    @Test
    public void testZipWithNullFile(){
        boolean t = false;
        try {
            Zipper z = new Zipper(new File("~/test.zip"));
            z.addFile(null);
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not zip a null file.", t);
    }

    @Test
    public void testZipWithInvalidFile(){
        boolean t = false;
        try {
            Zipper z = new Zipper(new File("~/test.zip"));
            z.addFile(new File("/this/file/does/not/exists.txt"));
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not zip a file if not exists.", t);
    }

    @Test
    public void testZipWithNullFiles(){
        boolean t = false;
        try {
            Zipper z = new Zipper(new File("~/test.zip"));
            z.addFiles( null );
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not zip a null list of files.", t);
    }

    @Test
    public void testZipWithEmptyListFiles(){
        boolean t = false;
        try {
            Zipper z = new Zipper(new File("~/test.zip"));
            z.addFiles( new ArrayList<File>());
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not zip a empty list of files.", t);
    }
}
