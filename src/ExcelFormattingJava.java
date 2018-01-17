import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ExcelFormattingJava {

    public static void main(String[] args) throws Exception{

        DateParse dateParse = new DateParse();
        IsbnParse parse2 = new IsbnParse();
        Cell cell;
        String name;
        int loanDateLoc = 0;
        Scanner scanner = new Scanner(System.in);
        // gets file name for excel sheet
        //System.out.println("Enter the name of the Excel sheet");
        //String filename = scanner.nextLine();
        ArrayList<String[]> newYears = new ArrayList<>();
        InputStream input = null;
        OutputStream output = null;
        try {
            System.out.println("trying to open file");
            input = new FileInputStream("test.xlsx");
           // output = new FileOutputStream("test.xlsx");
            if(input ==  null )
                System.out.println("ERROR OPENING FILE(s)");
            else
                System.out.println("files opened successfully");

            Workbook wb = WorkbookFactory.create(input); // opens workbook

            Sheet sheet = wb.getSheetAt(0); // gets first sheet
            Row row = sheet.getRow(0); // gets first row

            // finds loan date
            for (int i = 0; i < row.getLastCellNum(); i++) {
                cell = row.getCell(i);// gets cell in row
                name = cell.getStringCellValue();// gets the name of the cell
                if (name.contains("Loan Date")) {
                    loanDateLoc = i;
                    break;
                }
            }
            String temp;
            if (loanDateLoc != 0) {
                int loc = 0; // the row number that the year is being changed in

                // goes through the rows
                for (int i = 1; i < sheet.getLastRowNum(); i++) {
                    // gets the cell in which loan dates are located
                    row = sheet.getRow(i);
                    temp = row.getCell(loanDateLoc).getStringCellValue();
                    // if temp is longer than 4 or if it doesnt have any numbers
                    if(temp.length() > 4 || StringUtils.indexOfAny(temp, "0123456789") == -1) {
                        if ((temp = dateParse.purge(temp)).length() > 4) {
                            temp = dateParse.separate(temp);
                            newYears.add(new String[]{"" + i, temp }); // this should add the row number and the year to replace
                            System.out.println();
                        }
                    }
                    Cell c = null;
                    c = row.getCell(loanDateLoc);
                    if(c.getStringCellValue() != temp)
                        c.setCellValue(temp);

                    System.out.println();
                }
            }
        }finally {
            try {
                if (input != null)
                    input.close();
            } catch (IOException e) {
                System.out.println("Failed to close stream");
            }
        }
    }
}
// loan date = cell 7

// implement the write after isbn is done
/*
    todo: implement the write to excel sheet and other formatting
    TODO: implement multi-threading to do ibsn and years at the same time

 */