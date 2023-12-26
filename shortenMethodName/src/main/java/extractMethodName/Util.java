package extractMethodName;

import java.io.*;
import java.util.ArrayList;

public class Util {
    public static void appendFile(String line, String path){
        FileWriter fw = null;

        try{
            File f = new File(path);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter pw = new PrintWriter(fw);
        pw.print(line);
        pw.flush();
        try{
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static ArrayList<String> readFile(String fileName){
        ArrayList<String> result = new ArrayList<String>();
        File file = new File(fileName);
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            while((tempString = reader.readLine()) != null)
            {
                if(!tempString.equals("")){
                    result.add(tempString);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result;
    }


    public static boolean equalOfWord(String str1, String str2) {
        str1 = str1.trim();
        str2 = str2.trim();
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();

        if (str1.length() == 0 || str2.length() == 0) {
            return false;
        }

        if (str1.equals(str2)) {
            return true;
        } else {
            String str1Single = str1;
            String str2Single = str2;
            if (str1.charAt(str1.length()-1) == 's' &&
                    str1.length()>1) {
                str1Single = str1.substring(0, str1.length()-1);
            }
            if (str2.charAt(str2.length()-1) == 's' &&
                    str2.length()>1) {
                str2Single = str2.substring(0, str2.length()-1);
            }
            return str1Single.equals(str2) ||
                    str2Single.equals(str1) ||
                    str1Single.equals(str2Single);
        }
    }

}
