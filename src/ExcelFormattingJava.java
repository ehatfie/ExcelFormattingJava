import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class ExcelFormattingJava {
    public static void main(String[] args) throws Exception{

        DateParse parser = new DateParse();
        Cell cell;
        String name;
        Scanner scanner = new Scanner(System.in);
        // gets file name for excel sheet
        //System.out.println("Enter the name of the Excel sheet");
        //String filename = scanner.nextLine();
        InputStream input = new FileInputStream("test.xlsx");
        Workbook wb = WorkbookFactory.create(input); // opens workbook
        Sheet sheet = wb.getSheetAt(0); // gets first sheet
        Row row = sheet.getRow(0); // gets first row
        // finds loan date
        for(int i = 0; i < row.getLastCellNum(); i++){
            cell = row.getCell(i);// gets cell in row
            name = cell.getStringCellValue();// gets the name of the cell
            if(name.contains("Loan Date")){
                System.out.println(name);
            }
       }
        String temp = parser.purge("05-26-1996");
        System.out.println(temp);

    }
}
// loan date = cell 7