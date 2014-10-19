package it.hackcaffebabe.ioutil;

import it.hackcaffebabe.ioutil.file.UnZipper;
import it.hackcaffebabe.ioutil.file.Zipper;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Test class of {@link UnZipper}
 */
public class TestUnZipper {
    @Test
    public void testUnZipper(){
        try{
            File f1 = new File("/tmp/testing/TESTone.txt");
            f1.createNewFile();
            File f2 = new File("/tmp/testing/TESTtwo.txt");
            f2.createNewFile();
            File f3 = new File("/tmp/testing/TESTthree.txt");
            f3.createNewFile();
            File dir = new File("/tmp/testing/TEST");
            dir.mkdir();
            File fileDir = new File("/tmp/testing/TEST/TESTfour.txt");
            fileDir.createNewFile();
            Zipper zipper = new Zipper(new File("/tmp/testing/testing.zip"));
            zipper.addFile(f1);
            zipper.addFile(f2);
            zipper.addFile(f3);
            zipper.addFile(dir);
            zipper.forceZip();

            //File zip = new File("/tmp/testing/testing.zip");
            //File dst = new File("/tmp/testing/");
            //UnZipper z = new UnZipper(zip, dst);

            //z.unZipAll(false);
        }catch (IllegalArgumentException ex){
            Assert.fail("Illegal Arg "+ex.getMessage());
        }catch (IOException eio){
            Assert.fail("IOEX "+eio.getMessage());
        }
    }

}
