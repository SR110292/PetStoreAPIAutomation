package api.utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {


    @DataProvider(name = "Data")
    public Object[][] getAllData() throws IOException{
        String path = System.getProperty("user.dir")+"//testData//Userdata.xlsx";
        String sheetName = "Sheet1";

        XLUtility xl = new XLUtility(path,sheetName);
        int rowNum = xl.getRowCount();
        int colCount = xl.getCellCount(1);
        String[][] apiData = new String[rowNum][colCount];
        for(int i=1; i<=rowNum; i++){
            for(int j=0; j<colCount; j++){
                apiData[i-1][j] = xl.getCellData(i, j);
            }
        }
        return apiData;
    }

    @DataProvider(name = "UserNames")
    public Object[][] getUserNames() throws IOException{
        String path = System.getProperty("user.dir")+"//testData//Userdata.xlsx";
        String sheetName = "Sheet1";

        XLUtility xl = new XLUtility(path,sheetName);
        int rowNum = xl.getRowCount();
        String[][] apiData = new String[rowNum][1];
        for(int i=1; i<=rowNum; i++){
            apiData[i-1][0] = xl.getCellData(i, 1);
        }
        return apiData;
    }
}
