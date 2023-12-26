package extractMethodName;

import java.util.ArrayList;
import java.util.Locale;

public class selectContainsAbbreviationMN {

    public static boolean containsAbbreviation(String methodName){
        ArrayList<String> tokens = TokenSplitUtil.split(methodName);
        ArrayList<String> englishDic = Util.readFile("./EnglishDic.txt");
        int n=0;
        for(int i=0;i< tokens.size();i++){
            String token = tokens.get(i);
            if(englishDic.contains(token)){
                n++;
            }
        }
        if(n==tokens.size()){
            return false;
        }else{
            return true;
        }
    }

    public static void selectData(String path){
        ArrayList<String> origData = transfer(path);
        for(int i=0;i<origData.size();i++){
            String line = origData.get(i);
            String methodName = line.split(",")[2];
            if(containsAbbreviation(methodName)){
                Util.appendFile(line+"\n","./Step1/2springboot.csv");
            }
        }
    }

    public static ArrayList<String> transfer(String path){
        ArrayList<String> file = Util.readFile(path);
        ArrayList<String> res = new ArrayList<String>();
        for(String str:file){
            String temp = str.replaceAll("##",",");
            res.add(temp);
//            System.out.println(temp);
        }
        return res;
    }

    public static void main(String[] args){
        System.out.println("----start------");
        String path ="./Step1/springboot.txt";
        selectData(path);
    }
}
