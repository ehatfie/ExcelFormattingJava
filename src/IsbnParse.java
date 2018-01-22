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

        int loc = StringUtils.indexOfAnyBut(isbn,numbers); // get position of the first non number
        int length = isbn.length(); // gets length of the isbn
        while(loc != -1) {
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
                } else
                    isbn = isbn.substring(0, loc - 1);
            }
            loc = StringUtils.indexOfAnyBut(isbn, numbers);
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
            if(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == '-'){
                count++;
            }
        }
        // if there is not at least 8 sequential numbers
        if(count < 8)
            return false;





        return true;
    }


}
