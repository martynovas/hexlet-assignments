package exercise;

import java.util.Arrays;
import java.util.ArrayList;

// BEGIN
class App {

    public static boolean scrabble(String chars, String word) {
        String[] chr = chars.split("");
        ArrayList<String> lst = new ArrayList<>(Arrays.asList(chr));

        for (String c : word.split("")) {
            if (!lst.remove(c.toLowerCase())) {
                return false;
            }
        }

        return true;
    }
}
//END
