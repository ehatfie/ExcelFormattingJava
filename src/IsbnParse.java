import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        int spaceLoc;// firstNon are first/last non number, firstNum/LastNum are first number
        String newIsbn = "";

        Pattern checkRegex = Pattern.compile("(([0-9Xx][- ]*){13}|([0-9Xx][- ]*){10})");
        Matcher regexMatcher = checkRegex.matcher(isbn);
        isbn = removeDashes(isbn);


        // while its finding matches
        while(regexMatcher.find()){
            // if the string has a length
            if(regexMatcher.group().length() != 0)
                newIsbn = regexMatcher.group().trim();
        }
        // if there is a found isbn
        if(newIsbn.length() > 0){
            // find the location of a space
            spaceLoc = newIsbn.indexOf(" ");
            // if there is a space then use the isbn number up to the space
            if(spaceLoc > - 1){
                newIsbn = newIsbn.substring(0, spaceLoc);
            }

            return newIsbn;
        }
        else{
            return "";
        }



    }


    // removes all the dashes
    public String removeDashes(String isbn){
        isbn = StringUtils.remove(isbn, '-');
        isbn = StringUtils.remove(isbn, '–');
        return isbn;
    }


    // checks if its in isbn format
    public Boolean checkValid(String isbn){
        int loc = 0, count = 0, maxCount = 0;

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
                if(c == 'x' || c == 'X' && i == isbn.length()-1)
                    count++;
                else {
                    if(maxCount < count){
                        maxCount = count;
                    }
                    count = 0;
                }
            else{
                if(maxCount < count){
                    maxCount = count;
                }
                count = 0;
            }
            // could at something checking based on the length of the string and current location to leave this early and return false
        }
        if(maxCount < count){
            maxCount = count;
        }
        // if there is not at least 8 sequential numbers
        if(maxCount < 8)
            return false;





        return true;
    }


}
/*
        // based on where the space is first
        while(firstNon != -1) {
            System.out.println(isbn.charAt(firstNon));
            if (spaceLoc > -1) {
                sub1 = isbn.substring(0, spaceLoc); // has the isbn up to the first space
                sub2 = isbn.substring(spaceLoc + 1, isbn.length()); // has the isbn of the first space till end

                // if the first space is at the end of the isbn number
                if (sub1.length() >= 8 && checkValid(sub1)) {

                    // gets the first non number of the substring up to the first space
                    firstNon = StringUtils.indexOfAnyBut(sub1, numbers);
                    if (firstNon > -1) // if there are non numbers
                    {   // if the first non number is an x or X then check if its valid and return/modify the string
                        if (sub1.charAt(firstNon) == 'x' || sub1.charAt(firstNon) == 'X') {
                            // if the string is a valid isbn
                            if (checkValid(sub1)) {
                                return sub1;
                            }
                            // if the first non number is an x or X and its not a valid isbn then erase the whole thing
                            else
                                sub1 = "";
                        }
                        // if there is a non number and it isnt an x or X then remove it
                        else {
                            sub1 = sub1.substring(0, firstNon - 1);
                            return sub1;
                        }
                    }
                    // else if there are no non numbers
                    else{
                        if(checkValid(sub1))
                            return sub1;

                    }
                    System.out.println();
                    //else if sub1 is smaller then 8, then the whitespace SHOULD be at the front and sub2 might contain the isbn
                } else {

                    System.out.println();
                    //
                    if(StringUtils.indexOfAnyBut(sub2, numbers) == -1)
                        return sub2;
                    else{
                        firstNon = StringUtils.indexOfAnyBut(sub2, numbers); // maybe using firstNon messes with things if the fixed isbn isnt returned right away
                        if(sub2.charAt(firstNon) == ' ' && firstNon == 1 && sub2.charAt(0) == '-') { // if the non character is a space and its the second spot and the first spot is a dash
                            sub2 = sub2.substring(firstNon + 1, sub2.length()); // get rid of the dash and the space
                            isbn = sub1 + sub2;
                            // this is a specific solution to the isbn of XXX - XXXXXXXXXX, which is an isbn with 13 numbers but only 1 dash which is surrounded by whitespaces
                        }
                    }
                    checkValid(sub1);
                }
            }
            // else if there are no spaces
            else{
                // if the character that is not a number is an x or X, alternatively could use the check valid
                if(checkValid(isbn)){
                    return isbn;
                }
                if(isbn.charAt(firstNon) == 'x' || isbn.charAt(firstNon) == 'X' && firstNon == isbn.length() - 1){
                    return isbn;
                }
                else{
                    sub1 = isbn.substring(0, firstNon);
                    sub2 = isbn.substring(firstNon, isbn.length());
                    System.out.println();
                    // if the first substring, from beginning to the first non number
                    int len = sub1.length();
                    // if the substring is 8 or longer, might need to do some checking with the second substring to be sure its right
                    if(sub1.length() >= 8){
                        if(sub2.length() < 8)
                            return sub1;
                        else
                            System.out.println(); // use this to check if there needs to be something here
                    }
                    //else if(sub1.length() == 0 && sub2 = isbn)
                }
            }
            if(loopCounter > 5){
                String tempIsbn;
                tempIsbn = removeSpaces(isbn);
                if(!checkValid(tempIsbn)){
                    return "";
                }
                else{
                    return tempIsbn;
                }
            }
            loopCounter++; // if the isbn is not an isbn it will keep looping, once loops are over 5 the isbn gets returned as ""
        }
 */

