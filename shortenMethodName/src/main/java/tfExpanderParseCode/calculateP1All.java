package tfExpanderParseCode;

import extractMethodName.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class calculateP1All {

    public static Map<String, Integer> calculateP1_1(){
      String path = "./trainingData";
      File file = new File(path);
      File[] files = file.listFiles();
      Map<String, Integer> res = new HashMap<>();
      for(File f: files){
          if(f.isDirectory()){
              String dirPath = f.getAbsolutePath()+"/data.csv";
              ArrayList<String> ctx = Util.readFile(dirPath);
              for(int i=0;i<ctx.size();i++){
                  String line = ctx.get(i);
                  String[] lines = line.split(",");
                  String key = lines[1].toLowerCase()+","+extractNecessaryFeature.handleExp(lines[4].toLowerCase());
                  if(res.containsKey(key)){
                      int temp = res.get(key);
                      res.put(key,temp+1);
                  }else{
                      res.put(key,1);
                  }
              }
          }
      }
        return res;
    }

    public static Map<String, Integer> calculateP1_2(Map<String,Integer> res){
        Map<String, Integer> total = new HashMap<>();
        for(String key : res.keySet()){
            String[] keys = key.split(",");
            String abbr = keys[0];
            int value = res.get(key);
            if(total.containsKey(abbr)){
                int temp = total.get(abbr);
                total.put(abbr, temp+value);
            }else{
                total.put(abbr,value);
            }
        }
        return total;
    }

    public static void calculateP1_3(Map<String,Integer> res, Map<String,Integer> total){
        for(String key: res.keySet()){
            String abbr = key.split(",")[0];
            int fz = res.get(key);
            int fm = total.get(abbr);
            double p1= (fz*1.0)/fm;
            System.out.println(key + " : " + p1);
            Util.appendFile(key +","+ p1+"\n","./Probability/p1.csv");
        }

    }

    public static void main(String[] args){
        Map<String,Integer> t1 = calculateP1_1();
        Map<String,Integer> t2 = calculateP1_2(t1);
        calculateP1_3(t1,t2);
    }
}
