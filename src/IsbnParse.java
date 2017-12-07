import org.apache.commons.lang3.StringUtils;
public class IsbnParse {

    IsbnParse(){    }

    // adds a - if the isbn is 8 long
    public String edit(String isbn){

        isbn = isbn.substring(0,3) + '-' + isbn.substring(4);

        return isbn;
    }

    // removes all non numbers and non -
    public String purge(String isbn){
        String numbers = "0123456789-";

        int loc = StringUtils.indexOfAnyBut(isbn,numbers);
        int length = isbn.length();

        // gets the string starting at the first number
        if(loc != 0 && loc != (length -1)) {
            isbn = isbn.substring(loc);
            loc = StringUtils.indexOfAnyBut(isbn,numbers);
        }

        //int loc2 = StringUtils.lastIndexOfAnyBut(numbers, isbn); // location of last number

        // if the string contains only 0-9 and - or loc is the last spot in the string
        if (loc == -1 || loc == isbn.length())
            return isbn;
        else{
            // if the first non number is an x and its at the end of the string of numbers
            // then erase from 1 psat last char to end

            if(isbn.charAt(loc) == 'x' || isbn.charAt(loc) == 'X'
                    && Character.isLetter(isbn.charAt(loc))){
                isbn = isbn.substring(0, loc+1);
            }
            else
                isbn = isbn.substring(0, loc-1);
        }
        return isbn;
    }


}
