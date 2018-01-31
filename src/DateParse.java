import org.apache.commons.lang3.StringUtils;

public class DateParse {


    DateParse(){

    }

    // removes all non numbers
    public String purge(String line){
        String numbers = "0123456789";
        String letters = "abcdefghijklmnopqrstuvwxyz-/,$";
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "November", "December"};
        int loc = StringUtils.indexOfAnyBut(line, numbers);
        int firstNum = StringUtils.indexOfAny(line, numbers);
        if(firstNum > -1) {
            while (loc != -1) {
                if (loc == 0) { // if the first spot in the line is a letter
                    // checks against the months
                    for (int i = 0; i < months.length; i++) {
                        // if it contains a month then only keep past the mnoth, might be able to delete the next x amount of characters if its in month day, year format
                        if (line.contains(months[i]))
                            line = line.substring(months[i].length() + 1, line.length());
                    }
                    // if there are no numbers left
                    if (StringUtils.indexOfAny(line, numbers) == -1) {
                        line = "";
                        break;
                    }

                    // get the new loc so it doesnt goof
                    loc = StringUtils.indexOfAny(line, numbers);
                }
                // if the non-number is a -
                if (line.contains("-")) {
                    // if yyy-
                    if (loc == 4) {
                        // if yyyy-yyyy or yyyy-yy
                        if (line.length() == 9 || line.length() == 6 &&
                                StringUtils.indexOfAnyBut(line, numbers) != loc + 1) {
                            // returns the first year
                            return line.substring(0, loc);
                        } else
                            line = line.substring(0, loc);
                    }
                    // if yy/yy
                    else if (loc == 2 && line.length() == 5) {
                        // if the year is greater than 10 its definitely 19xx
                        if (line.charAt(loc) > 1)
                            line = "19" + line.charAt(0) + line.charAt(1);
                        // else
                        // ideally there would be some way to see if its from 1900-1910 vs 200-2010
                        // i'll probably figure that out sometime
                    }

                    // otherwise its not either of them and its a mm-dd-yyy or dd-mm-yyy
                    else {
                        // if the last location of a string is the last of the line
                        //***** check to see if this is right or it needs line.length() *******
                        if (StringUtils.lastIndexOfAny(line, numbers) == (line.length() - 1)) {
                            line = "";
                        }
                        // otherwise
                        else
                            line = line.substring(loc + 1);
                    }
                } else if (line.charAt(loc) == ',') {
                    // if dd, yyyy or d,yyyy or same with mm
                    if (line.substring(loc, line.length()).length() == 6) {
                        line = line.substring(loc + 2);
                    } else
                        line = line.substring(0, loc); // ******** check if loc-1
                }
                // else erase the non number
                else {
                    if (line.charAt(loc) == '$')
                        line = line.substring(0, loc);
                    else
                        line = line.substring(loc);
                }
                loc = StringUtils.indexOfAnyBut(line, numbers);
            }
        }
        else{
            return "";
        }
        return line;

    }
    // formats dates with no numbers
    public String separate(String line){
        // for yyyymmdd or any variant thereof
        if(line.length() == 8){
            // works unless its ddmmyyy and the day is the 18/19/20, if thats an issue then I'll fix it, or with more free time
            if(line.charAt(0) == '1' && line.charAt(1) == '9')
                line = line.substring(0,4);
            else if(line.charAt(0) == '2' && line.charAt(1) == '0')
                line = line.substring(0,4);
            else if(line.charAt(0) == '1' && line.charAt(1) == '8')
                line = line.substring(0,4);
            // if its in mmddyyyy or ddmmyyyy or mmdyyyy or dmmyyyy
            else{
                if(line.length() == 8)
                    line = line.substring(4);
                else{
                    line = line.substring(3);
                }
            }
        }
        // else if in mmyyyy or myyyy format
        else if (line.length() == 5 || line.length() == 6){
            // if not in yyyym or yyyymm format
            if(!(line.charAt(0) == '1' && line.charAt(1) == '9') && !(line.charAt(0) == '2' && line.charAt(1) == 0))
            {   // gets rid of the first character until the string length is 4
                while(line.length() > 4)
                    line = line.substring(1);
            }
        }
        return line;
    }



}

/*
    ideas
    -   maybe check if there are 2 - in a date like dd-mm-yyyy and mm-dd-yyyy
        if so then find the yyyy part and delete the rest
    TODO
       -    check the line.susbtring(loc) instances to see if its working right
       -    for the ddmmyyyy/yyyymmdd stuff add another if statement that checks the char spots 3/4 or 5/6 to makes
            sure we got the year
       -    Stop the loop getting stuck
 */