package tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 *
 * @author Develop
 */
public class FileSaver {

    public FileSaver(String fileName, String data) throws IOException {
        saveFileFromString(fileName,"UTF-8",data);
    }


    private void saveFile(String file, String dir, String text){
        try {
            saveFileFromString(dir+file,"UTF-8",text);
        } catch (Exception ex) {}
    }

    public static void saveFileFromString(String fileName, String encoding, String v) throws IOException {
        saveFileFromString(new File(fileName),encoding,v);
    }

    public static void saveFileFromString(File file, String encoding, String v) throws IOException
    {
        if (v==null) {
            file.delete();
            return;
        }
        char[] buf= new char[v.length()];
        v.getChars(0,buf.length,buf,0);
        saveFileFromChars(file,encoding,buf);
    }
    public static void saveFileFromChars(String fileName, String encoding,char[] buf) throws IOException {
            saveFileFromChars(new File(fileName),encoding,buf);
        }
    public static void saveFileFromChars(File file, String encoding, char[] buf) throws IOException {
        if (buf==null) {
            file.delete();
            return;
        }
        saveFileFromChars(file,encoding,buf,0,buf.length);
    }

    public static void saveFileFromChars(File file, String encoding,char[] buf, int off, int len) throws IOException
    {
        if (buf==null) {
            file.delete();
            return;
        }
        OutputStreamWriter f= encoding==null?
        new FileWriter(file):
        new OutputStreamWriter(new FileOutputStream(file),encoding);
        try {
            f.write(buf,off,len);
        }
        catch (IOException e) {
            try {f.close();} catch (Exception e1) {};
        return;
        }
        f.close();
    }

}
