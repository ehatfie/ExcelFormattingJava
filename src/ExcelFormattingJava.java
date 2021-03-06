import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.*;

public class ExcelFormattingJava {

    public static void main(String[] args) throws Exception {

        DateParse dateParse = new DateParse();
        IsbnParse parse2 = new IsbnParse();
        Cell cell;
        String name, temp;
        int loanDateLoc = 0, issnLoc = 0, statusLoc = 0;
        InputStream input = null;
        try {
            System.out.println("trying to open file");
            input = new FileInputStream("test.xlsx");
            if (input == null)
                System.out.println("ERROR OPENING FILE(s)");
            else
                System.out.println("files opened successfully");

            Workbook wb = WorkbookFactory.create(input); // opens workbook

            Sheet sheet = wb.getSheetAt(0); // gets first sheet
            Row row = sheet.getRow(0); // gets first row
            // ------------------------------------------ Loan Date Formatting -----------------------------------------
            // finds loan date
            for (int i = 0; i < row.getLastCellNum(); i++) {

                cell = row.getCell(i);// gets cell in row
                name = cell.getStringCellValue();// gets the name of the cell
                // finds the location of the Loan Date Cells
                if (name.contains("Loan Date")) {
                    loanDateLoc = i;
                }
                // finds the location of the ISSN cells
                else if (name.contains("ISSN")) {
                    issnLoc = i;

                } else if (name.contains("Status")) {
                    statusLoc = i;
                }
                // if both have been found then leave the loop
                if (loanDateLoc > 0 && issnLoc > 0 && statusLoc > 0)
                    break;
            }
            if (loanDateLoc == 0)
                System.out.println("Could not find a cell named Loan Date");
            else if (issnLoc == 0)
                System.out.println("Could not find a cell named ISSN");
            else if (statusLoc == 0)
                System.out.println("Could not find a cell named Status");

            if (loanDateLoc != 0) {
                // goes through the rows
                for (int i = 1; i < sheet.getLastRowNum(); i++) {
                    Cell c = null;

                    // gets the cell in which loan dates are located
                    row = sheet.getRow(i);
                    c = row.getCell(loanDateLoc);
                    temp = row.getCell(loanDateLoc).getStringCellValue();
                    // if temp is longer than 4 or if it does not have any numbers
                    if (temp.length() > 4 || StringUtils.indexOfAny(temp, "0123456789") == -1) {
                        if ((temp = dateParse.purge(temp)).length() > 4) {
                            temp = dateParse.separate(temp);
                        }
                    }
                    // checks if the value has been changed, if so then change it
                    if (c.getStringCellValue() != temp)
                        c.setCellValue(temp);

                }
            }
            // ------------------------------ ISBN/ISSN Formatting --------------------------------------
            if (issnLoc != 0) {
                // goes through the rows
                for (int i = 0; i < sheet.getLastRowNum(); i++) {
                    Cell c = null;

                    if (i == 0) {
                        c = row.getCell(issnLoc);
                        c.setCellValue("ISSBN/ISSN");
                        System.out.println();
                    } else {
                        // gets the row
                        row = sheet.getRow(i);

                        // gets the cell in the row
                        c = row.getCell(issnLoc);

                        // gets the value of the cell that has the ISSN
                        temp = row.getCell(issnLoc).getStringCellValue();
                        // removes dashes
                        temp = parse2.removeDashes(temp);
                        // if there is a non number then it gets put in the cleanup function
                        if (StringUtils.indexOfAnyBut(temp, "0123456789-") > -1) {

                            temp = parse2.purge(temp);
                        }
                        // if the ISSN is 8 long then add a dash
                        if (temp.length() == 8)
                            temp = parse2.edit(temp);

                        // if the value of the cell has changed then put the new value into the cell
                        if (c.getStringCellValue() != temp) {
                            c.setCellValue(temp);
                        }
                    }
                }
            }
            //----------------------------------Consolidating Status Fields---------------------------
            if (statusLoc != 0) {
                String value, newValue;
                List<String> toGrad = Arrays.asList("DistanceEd", "DISTANCE EDUCAT", "Distance Ed Grad", "DistantGrad",
                        "Staff/Grad");
                List<String> toFac = Arrays.asList("FACULTY", "DistantFaculty");
                List<String> toAffil = Arrays.asList("Affiliated Faculty", "");
                HashMap<Integer, String> hmap = new HashMap<Integer, String>();

                // goes through the rows
                for (int i = 1; i < sheet.getLastRowNum(); i++) {
                    Cell c = null;
                    // gets the row
                    row = sheet.getRow(i);

                    // gets the cell in the row
                    c = row.getCell(statusLoc);

                    // gets the value in the cell
                    value = c.getStringCellValue();
                    newValue = value;
                    // convert the values
                    if (toGrad.contains(value)) {
                        newValue = "Graduate";
                    } else if (toFac.contains(value)) {
                        newValue = "Faculty";
                    } else if (toAffil.contains(value)) {
                        newValue = "Affiliate";
                    }

                    if (newValue != value) {
                        c.setCellValue(newValue);
                    }
                    // used to find what is in the sheet first time around
                    /*
                    if(map.containsKey(value)){
                        map.put(value, map.get(value) +1);
                    }else
                        map.put(value, 1);
                    */

                }
                System.out.println();
            }


            // --------------------------------- Writing To File --------------------------------
            FileOutputStream fileOut = new FileOutputStream("test.xlsx");   // opens the output stream
            wb.write(fileOut);  // write to the workbook
            fileOut.flush();
            fileOut.close();
            System.out.println();
        } finally {
            try {
                if (input != null)
                    input.close();
            } catch (IOException e) {
                System.out.println("Failed to close stream");
            }
        }

        System.out.println("Finished!");

    }

}