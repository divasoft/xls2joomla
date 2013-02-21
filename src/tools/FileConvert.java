package tools;

import core.Logger;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Develop
 */
public class FileConvert {
    private String separator0;
    private String separator1;
    private Vector file_xls;
    private String file_out;
    private Vector<String> cols;

    private int max_string;

    public FileConvert(String separator0, String separator1, Vector file_xls, Vector<String> cols, int max_string) {
        this.separator0 = separator0;
        this.separator1 = separator1;
        this.file_xls = file_xls;
        this.cols = cols;
        this.max_string=max_string;
        start();
    }
    
    public void saveFileDialog(String header, String ext, String text) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(header);
        fileChooser.setFileFilter(new FileNameExtensionFilter(text, ext));
        if (fileChooser.showSaveDialog(fileChooser)==JFileChooser.APPROVE_OPTION)
        {
            file_out=fileChooser.getSelectedFile().getAbsolutePath().toString();
            Logger.println("[Выбран файл для сохранения ("+ext+") :");
            Logger.println(file_out+"]");
            BitSaver bs = new BitSaver(separator0, separator1, file_out, cols, file_xls, max_string);
            bs.init();
        }
    }

    private void start() {
        saveFileDialog("Сохранить CSV файл...", "csv", "CSV File: *.csv");
    }
}