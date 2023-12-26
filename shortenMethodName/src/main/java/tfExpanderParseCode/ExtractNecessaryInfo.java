package tfExpanderParseCode;

import extractMethodName.Util;

import java.io.File;
import java.util.ArrayList;

public class ExtractNecessaryInfo {

    public static void getNecessaryCtx(){
        String path="./trainingData/";
        File file1= new File(path);
        File[] file1s = file1.listFiles();


        for(File f:file1s){
            String dirPath = f.getAbsolutePath()+"/methodName.csv";
            String dirName = f.getName();

            File f1 = new File("./trainingData/"+dirName);
            f1.mkdir();

            File file2= new File(dirPath);
            if(file2.exists()){
                ArrayList<String> con= Util.readFile(dirPath);
                for(String s: con){
                    String[] ss = s.split(",");
                    Util.appendFile(ss[1]+","+ss[2]+","+ss[3]+","+ss[4]+","+ss[65]+"\n","./trainingData/"+dirName+"/data.csv");
                }
            }
        }
    }

    public static void main(String[] args){
        System.out.println("-------");
        getNecessaryCtx();
    }
}
