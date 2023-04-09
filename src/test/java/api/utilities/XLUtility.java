package api.utilities;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.ss.usermodel.DataFormatter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XLUtility {

    public FileInputStream fi;
    public FileOutputStream fo;
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    private String path;
    private String sheetName;

    public XLUtility(String path, String sheetName){
        this.path = path;
        this.sheetName = sheetName;
    }

    public int getRowCount() throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();
        workbook.close();
        fi.close();
        return rowCount;
    }

    public int getCellCount(int rowNum) throws IOException{
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowNum);
        int columnCount = row.getLastCellNum();
        workbook.close();
        fi.close();
        return columnCount;
    }

    public String getCellData(int rowNum, int columnNum) throws IOException{
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowNum);
        cell = row.getCell(columnNum);
        DataFormatter formatter = new DataFormatter();
        String data;
        try {
            data = formatter.formatCellValue(cell);
        } catch (Exception e) {
            data = "";
        }
        workbook.close();
        fi.close();
        return data;
    }

    public void setCellData(int rowNum, int columnNum, String data) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowNum);
        cell = row.createCell(columnNum);
        cell.setCellValue(data);
        fo = new FileOutputStream(path);
        workbook.write(fo);
        workbook.close();
        fo.close();
        fi.close();
    }

}
