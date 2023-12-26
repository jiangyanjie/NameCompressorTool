package trainingDataPre;

import extractMethodName.Util;

import java.io.File;
import java.util.ArrayList;

public class getLabel {
    public static void getLabel(){
        String dir = "./trainingData/cutFeatureHadAbbrUpdate";
        File file = new File(dir);
        File[] files = file.listFiles();
        for(File f : files){
            String projectPath = f.getAbsolutePath();
            String projectName = f.getName();

            ArrayList<String> ctx = Util.readFile(projectPath);
            for(int i=0;i< ctx.size();i++){
                String line = ctx.get(i);
                String[] lines = line.split(",");
                String abbr = lines[1];
                String generateAbbr = lines[7];
                int label = 0;

                if(abbr.equalsIgnoreCase(generateAbbr)){
                    label = 1;
                }else{
                    label = 0;
                }
                String res = line +","+label;
                Util.appendFile(res+"\n","./trainingData/"+projectName);
            }
        }
    }

    public static void main(String[] args){
        getLabel();
    }


}
