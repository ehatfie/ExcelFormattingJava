import org.apache.commons.lang3.StringUtils;

public class DateParse {


    DateParse(){

    }


    public String purge(String line){
        String letters = "abcdefghijklmnopqrstuvwxyz-/";
        int loc = StringUtils.indexOfAny(line, letters);
        while(loc != -1){
            // if the non-number is a -
            if (line.contains("-")){
                // if yyy-
                if(loc == 4) {
                    // if yyy-yyy or yyy-yy
                    if(line.length() == 9 || line.length() == 6 &&
                            StringUtils.indexOfAny(line, letters) != loc + 1) {
                        // returns the first year
                        return line.substring(0, loc);
                    }
                }
            }
        }
        return line;
    }

}
