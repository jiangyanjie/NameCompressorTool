package tfExpanderParseCode;

import extractMethodName.Util;

import java.io.File;
import java.util.*;

public class extractFeature {
    public static void generateId(){
        String path="./trainingData";
        File file1= new File(path);
        File[] file1s = file1.listFiles();

        for(File f : file1s){
            String dirPath = f.getAbsolutePath()+"/data.csv";
            String dirName = f.getName();

            ArrayList<String> ctx = Util.readFile(dirPath);
            ArrayList<String> base = Util.readFile(dirPath);

            for(int i=0;i<ctx.size();i++){
                String str1 = ctx.get(i);
                String[] str1s = str1.split(",");
                String path1= str1s[0];
                String abbr1 = str1s[1];
                String identifier1 = str1s[2];
                int num=1;

                for(int j=0;j<base.size();j++){
                    String str2 = base.get(j);
                    String[] str2s = str2.split(",");
                    String path2= str2s[0];
                    String abbr2= str2s[1];
                    String identifier2= str2s[2];
                    if(path1.equals(path2) && identifier1.equals(identifier2) && (!abbr1.equals(abbr2))){
                        num++;
                    }
                }
                Util.appendFile(str1+ "," + num +"\n","./trainingData/"+dirName+".csv");
            }
        }
    }

    public static void main(String[] args){
        System.out.println("----------------");
        generateId();
    }
}
