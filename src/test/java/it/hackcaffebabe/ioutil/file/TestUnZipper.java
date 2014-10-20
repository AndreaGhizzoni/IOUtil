package it.hackcaffebabe.ioutil.file;

import it.hackcaffebabe.ioutil.file.UnZipper;
import it.hackcaffebabe.ioutil.file.Zipper;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Test class of {@link UnZipper}
 */
public class TestUnZipper {
    @Test
    public void testUnZipper(){
        try{
            ArrayList<File> files = new ArrayList<File>();
            for(int i=1; i<5; i++) {
                File f = new File("/tmp/testing/TEST" + i + ".txt");
                f.createNewFile();
                files.add(f);
            }
            new File("/tmp/testing/TEST/SUBFOLDER/SUB").mkdirs();
            new File("/tmp/testing/TEST/TESTdir.txt").createNewFile();
            new File("/tmp/testing/TEST/SUBFOLDER/SUB/Test.txt").createNewFile();

            Zipper zipper = new Zipper(new File("/tmp/testing/testing.zip"));
            zipper.addAll(files);
            zipper.add(new File("/tmp/testing/TEST/"));
            zipper.forceZip();

            File zip = new File("/tmp/testing/testing.zip");
            File dst = new File("/tmp/testing/extracting");
            dst.mkdirs();
            UnZipper z = new UnZipper(zip, dst);
            z.unZipAll(false);
        }catch (IllegalArgumentException ex){
            Assert.fail("Illegal Arg "+ex.getMessage());
        }catch (IOException eio){
            Assert.fail("IOEX "+eio.getMessage());
        }
    }
}
