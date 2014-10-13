package it.hackcaffebabe.ioutil.file;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Test class of {@link PathUtil}
 */
public class TestPathUtil {
    @Test
    public void testCopyDirectory(){
        try {
            File src = new File("/tmp/src");
            src.mkdir();
            new File("/tmp/src/dir1").mkdir();
            new File("/tmp/src/dir2").mkdir();

            File dst = new File("/tmp/dst");
            dst.mkdir();

            PathUtil.copyDirectory(src,dst);

            List<String> lst = Arrays.asList(dst.list());
            boolean t = dst.list().length != 0 && lst.contains("dir1") && lst.contains("dir2");
            Assert.assertTrue("Can copy src content in to dst.", t);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testCopyDirectoryWithNullSource(){
        boolean t = false;
        try{
            File src = null;
            File dst = new File("/tmp/dst");
            dst.mkdir();
            PathUtil.copyDirectory(src, dst);
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not copy a directory from null source.", t);
    }

    @Test
    public void testCopyDirectoryWithNotExistingSource(){
        boolean t = false;
        try{
            File src = new File("/tmp/im/not/exist/in/this/universe");
            File dst = new File("/tmp/dst");
            dst.mkdir();
            PathUtil.copyDirectory(src, dst);
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not copy a directory if it doesn't exists.", t);
    }

    @Test
    public void testCopyDirectoryWithSameSourceAndDestination(){
        boolean t = false;
        try{
            File src = new File("/tmp/src");
            src.mkdir();
            PathUtil.copyDirectory(src, src);
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not copy a directory if source is equal to destination.", t);
    }

    @Test
    public void testCopyDirectoryWithFileAsSource(){
        boolean t = false;
        try{
            File src = new File("/tmp/file.txt");
            src.createNewFile();
            File dst = new File("/tmp/dst");
            dst.mkdir();
            PathUtil.copyDirectory(src, dst);
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not copy a directory if source is not a directory.",t);
    }

    @Test
    public void testCopyDirectoryWithFileAsDestination(){
        boolean t = false;
        try{
            File src = new File("/tmp/src");
            src.mkdir();
            File dst = new File("/tmp/file.txt");
            dst.createNewFile();
            PathUtil.copyDirectory(src, dst);
        }catch (Exception e){
            t = true;
        }
        Assert.assertTrue("Can not copy a directory if destination is not a directory.",t);
    }
}
