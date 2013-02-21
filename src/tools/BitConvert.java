package tools;

import core.Logger;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Develop
 */
public class BitConvert implements Runnable {
    //private String separator0;
    private String separator1;
    private String file_xls;
    private Vector xls;

    public BitConvert(String separator1, String file_xls) {
        //this.separator0 = separator0;
        this.separator1 = separator1;
        this.file_xls = file_xls;
    }

    public Vector getXLS() {
        return xls;
    }
    
    @Override
    public void run() {
        InputStream is = null;
        POIFSFileSystem fs = null;
        Vector <Vector>v_rows_all = new Vector<Vector>();
        int NumOfSh=0;
        try {
            is = new FileInputStream(file_xls);
            fs = new POIFSFileSystem(is);
            HSSFWorkbook workBook = new HSSFWorkbook(fs);
            NumOfSh=workBook.getNumberOfSheets();
            for (int ir=0; ir<NumOfSh; ir++) {
                Vector <Vector>v_rows = new Vector<Vector>();
                HSSFSheet sheet = workBook.getSheetAt(ir);
                Iterator<Row> rows = sheet.rowIterator();
                while (rows.hasNext()) {
                    Row row = rows.next();
                    Vector <String>v_cells = new Vector<String>();
                    Iterator<Cell> cells = row.cellIterator();
                    while(cells.hasNext()) {
                        Cell cell = cells.next();
                        switch (cell.getCellType()) {
                            //case HSSFCell.CELL_TYPE_BLANK: break;
                            case HSSFCell.CELL_TYPE_FORMULA: v_cells.add((cell.getCellFormula().trim().replaceAll("\n", separator1).replaceAll("\r", " ")).trim()); break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    DateFormat dtf = new SimpleDateFormat("HH:mm");
                                    Date tt = new Date(cell.getDateCellValue().getTime());
                                    String datestr = dtf.format(tt);
                                    v_cells.add(datestr);
                                } else v_cells.add((Integer.toString((int)cell.getNumericCellValue()).trim().replaceAll("\n", separator1).replaceAll("\r", " ")).trim());
                                break;
                            case HSSFCell.CELL_TYPE_STRING: v_cells.add((cell.getStringCellValue().trim().replaceAll("\n", separator1).replaceAll("\r", " ")).trim()); break;
                            default: v_cells.add(""); break;
                        }
                    } v_rows.add(v_cells);
                }
                v_rows_all.add(v_rows);
            }
            Logger.println("[XLS parse ok!]");
        } catch  (Exception ex) {Logger.println("[XLS Error: "+ex.getMessage()+"]");}
        this.xls=v_rows_all;
        //new BitSaver(separator0, separator1, file_xls, file_out, cols, v_rows_all).run();
    }
}