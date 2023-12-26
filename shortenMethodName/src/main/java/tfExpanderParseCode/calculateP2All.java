package tfExpanderParseCode;

import extractMethodName.TokenSplitUtil;
import extractMethodName.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class calculateP2All {

    public static Map<String, Integer> calculateP2_1(){

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

    public static Map<String, Integer> calculateP2_2(Map<String,Integer> res){
        Map<String, Integer> total = new HashMap<>();
        for(String key : res.keySet()){
            String[] keys = key.split(",");
            String exp = keys[1];
            int value = res.get(key);
            if(total.containsKey(exp)){
                int temp = total.get(exp);
                total.put(exp, temp+value);
            }else{
                total.put(exp,value);
            }
        }
        for (String key : total.keySet()) {
            System.out.println(key + " : " + total.get(key));
        }
        return total;
    }

    public static Map<String, Integer> calculateP2_3(){
        String path = "./trainingData";
        File file = new File(path);
        File[] files = file.listFiles();
        Map<String, Integer> res = new HashMap<>();
        for(File f: files){
            if(f.isDirectory()){
                String dirPath = f.getAbsolutePath()+"/data.csv";
                ArrayList<String> ctx = Util.readFile(dirPath);
                for(int i=0;i< ctx.size();i++){
                    String[] lines = ctx.get(i).split(",");
                    String identifier = lines[2];
                    ArrayList<String> tokens = TokenSplitUtil.split(identifier);
                    for(int j=0;j<tokens.size();j++){
                        String temp = tokens.get(j).toLowerCase();
                        if(Dic.isInDict(temp)){
                            if(res.containsKey(temp)){
                                int n = res.get(temp);
                                res.put(temp,n+1);
                            }else{
                                res.put(temp,1);
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    public static void calculateP1_4(Map<String,Integer> res, Map<String,Integer> total, Map<String, Integer> methodExp){
        for(String key: methodExp.keySet()){
            int f3= methodExp.get(key);
            int f2=0;
            if(total.get(key) != null){
                f2= total.get(key);
            }else{
                f2=0;
            }
            int f1=0;
            String temp="";
            for(String kr: res.keySet()){
                String exp = kr.split(",")[1];
                if(key.equalsIgnoreCase(exp)){
                    f1+=res.get(kr);
                    temp=kr;
                }
            }
            double p21=(f1*1.0)/(f2+f3);
            double p22=(f3*1.0)/(f2+f3);
            if(f1==0){
                Util.appendFile(key +","+key+","+p22+"\n","./Probability/p2.csv");
            }else{
                Util.appendFile(temp +","+ p21+"\n","./Probability/p2.csv");
            }
        }
    }



    public static void main(String[] args){
        Map<String,Integer> t1 = calculateP2_1();
        Map<String,Integer> t2 = calculateP2_2(t1);
        Map<String, Integer> t3 = calculateP2_3();
        calculateP1_4(t1,t2,t3);
    }

}
