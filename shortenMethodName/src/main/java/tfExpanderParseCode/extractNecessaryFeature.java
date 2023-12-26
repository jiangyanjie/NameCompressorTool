package tfExpanderParseCode;

import extractMethodName.Util;

import java.io.File;
import java.util.ArrayList;

public class extractNecessaryFeature {

    public static String handleExp(String expan){
        int index = expan.indexOf("[+");
        if(index != -1){
            String res = expan.substring(0,index);
            return res;
        }else{
            return expan;
        }
    }

    public static void multiple(ArrayList<String> multipleLine, String dirName){
        int numLabel = multipleLine.size();
        String Abbr="";
        String Exp="";
        String identifier="";
        String lpath="";
        String label="";
        for(int i=0;i< numLabel;i++){
            String line = multipleLine.get(i);
            String[] lines = line.split(",");
            lpath = lines[0];
            Abbr += " "+lines[1];
            identifier = lines[2];
            Exp += "*"+lines[4];
            label = lines[5];
        }
        Util.appendFile(lpath + "," + Abbr+","+identifier+","+Exp+ ","+label+"\n","./Feature/"+dirName);
    }

    public static void extractNecessaryFeature(){
        String path ="./trainingData";
        File file = new File(path);
        File[] files = file.listFiles();

        for(File f : files){
            String dirPath = f.getAbsolutePath();
            if(dirPath.endsWith(".csv")){
                String dirName = f.getName();
                ArrayList<String> ctx = Util.readFile(dirPath);
                ArrayList<String> multipleAbbr = new ArrayList<>();
                boolean flag = false;
                for(int i=0;i< ctx.size();i++){
                    String line = ctx.get(i);
                    String[] lines = line.split(",");
                    String lpath = lines[0];
                    String abbr = lines[1];
                    String identifier = lines[2];
                    String expan = lines[4];
                    String lable = lines[5];

                    if(Integer.parseInt(lable) == 1){
                        flag = true;
                        Util.appendFile(lpath + "," + abbr+","+identifier+","+expan+ ","+lable+"\n","./Feature/"+dirName);
                    }else{
                        flag = false;
                        multipleAbbr.add(line);
                    }
                    if(flag == true){ // handle multipleAbbr
                        if(multipleAbbr.size() ==0){
                        }else{
                            multiple(multipleAbbr,dirName);
                            multipleAbbr.clear();
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        extractNecessaryFeature();
    }
}
