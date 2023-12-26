package trainingDataPre;

import extractMethodName.Util;

import java.io.File;
import java.util.ArrayList;

public class combine {

    public static void combineAllFile() {
        String dir = "./trainingData/hadAbbrUpdate";
        File file = new File(dir);
        File[] files = file.listFiles();
        int n = 0;
        int m = 0;
        int positive = 0;
        int negative = 0;
        for (File f : files) {
            String filePath = f.getAbsolutePath();
            ArrayList<String> ctx = Util.readFile(filePath);
            m++;

            for (int i = 0; i < ctx.size(); i++) {
                String line = ctx.get(i);
                String lableStr = line.split(",")[20];
                int lable = Integer.parseInt(lableStr);
                n++;
                if(lable ==0){
                    negative++;
                }
                if(lable ==1){
                    positive++;
                }

                Util.appendFile(line+"\n", "./trainingData/tempModelHadAbbrUpdate.csv");
            }
        }

        System.out.println(n);
        System.out.println(positive);
        System.out.println(negative);

    }
    public static void main(String[] args){
        combineAllFile();
    }
}
