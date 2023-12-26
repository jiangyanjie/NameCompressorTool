package extractMethodName;

import java.util.ArrayList;
import java.util.List;

public class TokenSplitUtil {

    public static boolean isNum(char c) {
        return c >= '0' && c <= '9';
    }

    // str only consists of letters
    public static ArrayList<String> splitBigLetter(String str) {
        char[] list = str.toCharArray();
        str = "A" + str + "A";
        char[] tempList = str.toCharArray();

        for (int i = 1; i < tempList.length - 1; i++) {
            if (tempList[i] >= 'A' && tempList[i] <= 'Z' &&
                    tempList[i - 1] >= 'A' && tempList[i - 1] <= 'Z' &&
                    tempList[i + 1] >= 'A' && tempList[i + 1] <= 'Z') {
                list[i - 1] = (char) (tempList[i] - 'A' + 'a');
            } else {
                list[i - 1] = tempList[i];
            }
        }

        str = new String(list);
        ArrayList<String> result = new ArrayList<>();
        int startPositionOfSubstring = 0;
        str = str + 'A';
        for (int endPositionOfSubstring = 0; endPositionOfSubstring < str.length(); endPositionOfSubstring++) {
            if (str.charAt(endPositionOfSubstring) >= 'A' && str.charAt(endPositionOfSubstring) <= 'Z') {
                // to exclude initial up case letter
                if (str.substring(startPositionOfSubstring, endPositionOfSubstring).length() > 0) {
                    // to lower case
                    result.add(str.substring(startPositionOfSubstring, endPositionOfSubstring).toLowerCase());
                    startPositionOfSubstring = endPositionOfSubstring;
                }
            }
        }
        return result;
    }

    // identifiers consist of
    // letters
    // numbers
    // _
    // $
    // not start with numbers
    // the size of return value may be 0 (e.g., $)
    public static ArrayList<String> split(String str) {

        int temp1 = str.indexOf("<");
        int temp2 = str.lastIndexOf(">");
        if (temp1 != -1 && temp2 != -1) {
            str = str.substring(0, temp1) + str.substring(temp2 + 1);
        }

        str = str.replace("<", "");
        str = str.replace(">", "");
        str = str.replace("?", "");
        str = str.replace("&", "");
        str = str.replace("*", "");
        str = str.replace("-", "");

        temp1 = str.indexOf("[");
        temp2 = str.lastIndexOf("]");
        if (temp1 != -1 && temp2 != -1) {
            str = str.substring(0, temp1) + str.substring(temp2 + 1);
        }

        temp1 = str.indexOf("(");
        temp2 = str.lastIndexOf(")");
        if (temp1 != -1 && temp2 != -1) {
            str = str.substring(0, temp1) + str.substring(temp2 + 1);
        }

        ArrayList<String> result = new ArrayList<>();
        if (str.length() == 0) {
//            System.out.println("error: split");
            return result;
        }
        // delete $, _, numbers at the beginning of str
        while (str.length() > 0 && (str.charAt(0) == '$' || str.charAt(0) == '_' || isNum(str.charAt(0)))) {
            str = str.substring(1);
        }
        if (str.length() == 0) return result;
        // replace $, _, numbers with the separator #
        str = str.replaceAll("\\d", "#");
        str = str.replaceAll("\\_", "#");
        str = str.replaceAll("\\$", "#");

        for (String string : str.split("#")) {
            if (string.length() > 0) {
                result.addAll(splitBigLetter(string));
            }
        }
        return result;
    }

    // ori: index; sequence: idx
    public static boolean isSequence(String ori, String sequence) {

        if(ori.charAt(0)==(sequence.charAt(0))){
            int j = 0;
            for (int i = 0; i < ori.length(); i++) {
                if (j < sequence.length() && ori.charAt(i) == sequence.charAt(j)) {
                    j++;
                }
            }
            if (j == sequence.length()) {
                return true;
            }

        }
        return false;
    }


    public static void main(String[] args) {

//        List<Integer> list = new ArrayList<>();
//        System.out.println(list.getClass());
//
//        // test readFile
//        // test isSequence
////        System.out.println(isSequence("abcd", "adc"));
////
////        // test splitBigLetter
////        System.out.println(splitBigLetter("AabcADBAdfDDd"));
//
//        // test split
//        System.out.println(split("(fixed)"));

        String t1="dex";
        String t2 ="index";
        boolean a =isSequence(t2,t1);
        System.out.println(a);


    }
}		

