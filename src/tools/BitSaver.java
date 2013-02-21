package tools;

import core.Logger;
import form.SimpleForm;
import java.util.Vector;

/**
 *
 * @author Develop
 */
public class BitSaver implements Runnable {
    private String separator0;
    private String separator1;
    private String file_out;
    private Vector<String> cols;
    private Vector xls;
    private Thread thread;
    private Vector<String> csv_out;
    private int max_string;


    public BitSaver(String separator0, String separator1, String file_out, Vector<String> cols, Vector xls, int max_string) {
        this.separator0 = separator0;
        this.separator1 = separator1;
        this.file_out = file_out;
        this.cols = cols;
        this.xls = xls;
        this.max_string=max_string;
    }

    public void init() {
        thread = new Thread(this);
        thread.setName("BitSaver");
        thread.start();
    }

    public void run() {
        String out="";
        String header="";
        int min_string=max_string;
        csv_out= new Vector<String>();
        //books
        for (int i=0;i<xls.size();i++) {
            //rows
            Vector x = (Vector) xls.get(i);
            SimpleForm.setMax(x.size());
            for (int j=0;j<x.size();j++) {
                //cels
                SimpleForm.incBar();
                Vector y = (Vector) x.get(j);
                for (int z=0;z<y.size();z++) {
                    if (cols==null) {
                        out+=separator1+y.get(z).toString().trim()+separator1+((z==y.size()-1)?"":separator0);
                    }
                    else if (cols.contains(Integer.toString(z+1))) {
                        //out+=separator1+y.get(z).toString().trim()+separator1+((z==y.size()-1)?"":separator0);}
                        out+=separator1+y.get(z).toString().trim()+separator1+((z==y.size()-1 || z==(Integer.parseInt(cols.get(cols.size()-1))-1))?"":separator0);
                    } if (j==0 && z>=y.size()-1) header=out;
                }
                out+="\r\n";
                if (j==min_string) {
                    csv_out.add(out); out=header+"\r\n";
                    min_string+=max_string;
                }
            }
        }
        csv_out.add(out);
        Logger.println("[Preparing CSV complite!]");
        try {
            for (int i=0;i<csv_out.size();i++) {
                FileSaver fileSaver = new FileSaver(file_out+"_"+i+".csv", csv_out.get(i));
                Logger.println("[Save complite ("+file_out+"_"+i+".csv"+") - CSV!]");
            }
            Logger.println("[Save complite all CSV!]");
        } catch (Exception ex) {
            Logger.println("[Error save file CSV"+ex.getMessage()+"]");
        }
    }
}