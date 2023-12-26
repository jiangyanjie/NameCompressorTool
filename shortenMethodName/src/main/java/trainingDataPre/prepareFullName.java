package trainingDataPre;

import extractMethodName.TokenSplitUtil;
import extractMethodName.Util;

import java.io.File;
import java.util.ArrayList;

public class prepareFullName {

    public static String handleExp(String expan){
        int index = expan.indexOf("[+");
        if(index != -1){
            String res = expan.substring(0,index);
            return res;
        }else{
            return expan;
        }
    }
    public static String getFullName(String abbr,String identifier, String exp){
        ArrayList<String> tokens = TokenSplitUtil.split(identifier);
        String newExp = handleExp(exp);
        String fullName="";
        for(String s:tokens){
            System.out.println(s);
            if(s.equalsIgnoreCase(abbr)){
                fullName += newExp+" ";
            }else{
                fullName += s+" ";
            }
        }
        String res = fullName.trim();
        return res;
    }


    public static void formateData(){
        String dir = "./trainingData";
        File file = new File(dir);
        File[] files = file.listFiles();
        for(File f: files){
            String fileName = f.getName();
            String filePath = f.getAbsolutePath()+"/data.csv";
            File tempFile = new File(filePath);
            if(tempFile.exists()){
              ArrayList<String> ctx = Util.readFile(filePath);
              for(String str: ctx){
                  String[] line = str.split(",");
                  String abbr = line[1];
                  String identifier = line[2];
                  String exp = line[4];
                  String fullName = getFullName(abbr,identifier,exp);
                  String res = line[0]+","+line[1]+","+line[2]+","+line[4]+","+fullName;
                  Util.appendFile(res+"\n","./trainingData/"+fileName+".csv");
              }
            }
        }
    }

    public static void main(String[] args){
        System.out.println("--------------");
        formateData();
    }
}
