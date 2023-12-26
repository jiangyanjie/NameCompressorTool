package tfExpanderParseCode;

import extractMethodName.Util;

import java.io.File;
import java.util.ArrayList;

public class getAllMethodName {

    public static void getMethodName(String dirName){
        String path=dirName;
        File file = new File(path);
        File[] files = file.listFiles();
        for(File f:files){
            if(f.isDirectory()){
                String absPath=f.getPath();
                String absName=f.getName();
                String tempPath=absPath+"\\FFF_111_EXPANSION.csv";
                System.out.println(tempPath);
                File temF=new File(tempPath);
                File temp =new File("./trainingData/"+absName);
                temp.mkdir();
                if(temF.exists()){
                    ArrayList<String> con = Util.readFile(tempPath);
                    for(String str: con){
                        String type=str.split(",")[4];
                        if(type.startsWith("MethodName")){
                            Util.appendFile(str+"\n","./trainingData/"+absName+"/methodName.csv");
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        System.out.println("---start---");
        // the parsed projects
        String dirName = "D:\\Abbreviations\\downloadProject\\training200Project";
        getMethodName(dirName);
    }
}
