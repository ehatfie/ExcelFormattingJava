import org.apache.commons.lang3.StringUtils;
public class IsbnParse {

    IsbnParse(){    }

    // adds a - if the isbn is 8 long
    public String edit(String isbn){
        String sub1, sub2;

        sub1 = isbn.substring(0, 4);// get first half
        sub2 = isbn.substring(4); // get second half
        isbn = sub1 + '-' + sub2;

        return isbn;
    }

    // removes all non numbers and non -
    public String purge(String isbn){
        String numbers = "0123456789-";
        int firstNon, firstNum, lastNum, spaceLoc;// firstNon are first/last non number, firstNum/LastNum are first number
        firstNon = StringUtils.indexOfAnyBut(isbn, numbers); // gets first non number
        firstNum = StringUtils.indexOfAny(isbn, numbers); // gets first number
        lastNum = StringUtils.lastIndexOfAny(isbn, numbers); // gets last number
        spaceLoc = isbn.indexOf(" ");
        String sub1, sub2;
        /*
            need to tell if the first non is a white space
                -   if its a white space at beginning then delete up to it
                -   if its a white space at end then delete past it
         */


        // based on where the space is first
        while(firstNon != -1) {
            if (spaceLoc > -1) {
                sub1 = isbn.substring(0, spaceLoc); // has the isbn up to the first space
                sub2 = isbn.substring(firstNon, isbn.length()); // has the isbn of the first space till end
                // if the first space is at the end of the isbn number
                if (sub1.length() >= 8) {

                    // gets the first non number of the substring up to the first space
                    firstNon = StringUtils.indexOfAnyBut(sub1, numbers);

                    if (firstNon > -1) // if there are non numbers
                    {   // if the first non number is an x or X then check if its valid and return/modify the string
                        if (sub1.charAt(firstNon) == 'x' || sub1.charAt(firstNon) == 'X') {
                            // if the string is a valid isbn
                            if (checkValid(sub1)) {

                            }
                            // if the first non number is an x or X and its not a valid isbn then erase the whole thing
                            else
                                sub1 = "";
                        }
                    }

                    System.out.println();
                    //else if sub1 is smaller then 8, then the whitespace SHOULD be at the front and sub2 might contain the isbn
                } else {
                    
                    System.out.println();
                }
            }
        }

        return isbn;
    }

    // checks if its in isbn format
    public Boolean checkValid(String isbn){
        int loc = 0, count = 0;

        if(isbn.length() < 8)
            return false;
        else if (StringUtils.indexOfAny(isbn,"1234567890") == -1)
            return false;
        for(int i = 0; i < isbn.length(); i++){

            char c;
            c = isbn.charAt(i);
            // if the character is a number
            if(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == '-'){
                count++;
            }
            // else if the character is an x or X and at the end if the isbn (Might be wrong, check here first)
            else if(count > 6)
                if(c == 'x' || c == 'X' && i == isbn.length())
                    count++;
                else
                    count = 0;
            else
                count = 0;
            // could at something checking based on the length of the string and current location to leave this early and return false
        }
        // if there is not at least 8 sequential numbers
        if(count < 8)
            return false;





        return true;
    }


}
/*
e(loc != -1) {
            if(StringUtils.indexOfAny(isbn, numbers) > - 1)
            // gets the string starting at the first number
            if (loc != 0 && loc != (length - 1)) {
                isbn = isbn.substring(0, loc);
                loc = StringUtils.indexOfAnyBut(isbn, numbers);
            }
            //int loc2 = StringUtils.lastIndexOfAnyBut(numbers, isbn); // location of last number

            // if the string contains only 0-9 and - or loc is the last spot in the string
            if (loc == -1 || loc == isbn.length())
                return isbn;
            else {
                // if the first non number is an x and its at the end of the string of numbers
                // then erase from 1 past last char to end

                if (isbn.charAt(loc) == 'x' || isbn.charAt(loc) == 'X'
                        && Character.isLetter(isbn.charAt(loc))) {
                    isbn = isbn.substring(0, loc + 1);
                    if(StringUtils.indexOfAnyBut(isbn, numbers + "xX") == -1)
                            return isbn;
                }else if(loc == isbn.length()){

                }else
                    isbn = isbn.substring(loc, isbn.length());
            }
            loc = StringUtils.indexOfAnyBut(isbn, numbers);
        }
        return isbn;
    }
 */
