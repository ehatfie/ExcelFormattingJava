import org.apache.commons.lang3.StringUtils;

public class DateParse {


    DateParse(){

    }


    public String purge(String line){
        String numbers = "0123456789";
        String letters = "abcdefghijklmnopqrstuvwxyz-/,$";
        int loc = StringUtils.indexOfAnyBut(line, numbers);
        while(loc != -1){
            // if the non-number is a -
            if (line.contains("-")){
                // if yyy-
                if(loc == 4) {
                    // if yyyy-yyyy or yyyy-yy
                    if(line.length() == 9 || line.length() == 6 &&
                            StringUtils.indexOfAnyBut(line, numbers) != loc + 1) {
                        // returns the first year
                        return line.substring(0, loc);
                    }
                    else
                        line = line.substring(0, loc);
                }
                // if yy/yy
                else if (loc == 2 && line.length() == 5){
                    // if the year is greater than 10 its definitely 19xx
                    if (line.charAt(loc) > 1)
                        line = "19" + line.charAt(0) + line.charAt(1);
                    // else
                        // ideally there would be some way to see if its from 1900-1910 vs 200-2010
                        // i'll probably figure that out sometime
                }

                // otherwise its not either of them and its a mm-dd-yyy or dd-mm-yyy
                else{
                    // if the last location of a string is the last of the line
                    //***** check to see if this is right or it needs line.length() *******
                    if(StringUtils.lastIndexOfAny(line, numbers) == (line.length() - 1)){
                        line = "";
                    }
                    // otherwise
                    else
                        line = line.substring(loc+1);
                }
            }
            else if (line.charAt(loc) == ','){
               // if dd, yyyy or d,yyyy or same with mm
                if(line.substring(loc, line.length()).length() == 6){
                    line = line.substring(loc+2);
                }
                else
                    line = line.substring(0, loc); // ******** check if loc-1
            }
            // else erase the non number
            else{
                if (line.charAt(loc) == '$')
                    line = line.substring(0, loc);
                else
                    line = line.substring(loc);
            }
            loc = StringUtils.indexOfAnyBut(line, numbers);
        }
        return line;
    }

    public String separate(String line){
        return line;
    }

    

}

/*
    ideas
    -   maybe check if there are 2 - in a date like dd-mm-yyyy and mm-dd-yyyy
        if so then find the yyyy part and delete the rest
    TODO
       -    check the line.susbtring(loc) instances to see if its working right
 */