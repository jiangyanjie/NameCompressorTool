package constructDictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;

public class Dic {
    public static String computerAbbrDelimiter = ";";

    public static HashSet<String> englishDicHashSet = new HashSet<String>();
    public static HashMap<String, String> abbrDicHashMap = new HashMap<String, String>();
    public static HashMap<String, String> computerAbbrDicHashMap = new HashMap<>();
    static {
        // init english dic
        try {
            BufferedReader reader = new BufferedReader(new FileReader("D:\\Abbreviations\\MethodName\\JavaParser\\EnglishDic.txt"));
            String temp;
            while ((temp = reader.readLine()) != null) {
                englishDicHashSet.add(temp.toLowerCase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isInDict(String str){
        if (str.length() == 1) {
            return false;
        }
        if (englishDicHashSet.contains(str.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }
}
