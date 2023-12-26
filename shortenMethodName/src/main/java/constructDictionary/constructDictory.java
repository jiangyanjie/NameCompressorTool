package constructDictionary;

import extractMethodName.TokenSplitUtil;
import extractMethodName.Util;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class constructDictory {

    public static ArrayList<String> extractDic(){
        String path = "./trainingData";
        File file = new File(path);
        File[] files = file.listFiles();
        Map<String,Integer> aePair = new HashMap<>();
        ArrayList<String> res= new ArrayList<>();
        for(File f : files){
            if(f.isDirectory()){
                String dirPath = f.getAbsolutePath();
                ArrayList<String> ctx = Util.readFile(dirPath+"/data.csv");
                for(int i=0;i< ctx.size();i++){
                    String[] line = ctx.get(i).split(",");
                    String abbr = line[1];
                    String expansion = constructDic.handleExp(constructDic.handleExpformat(line[4]));
                    String key = expansion+","+abbr;
                    if(aePair.containsKey(key)){
                        int temp = aePair.get(key);
                        aePair.put(key,temp+1);
                    }else{
                        aePair.put(key,1);
                    }
                }
            }
        }
        for (String key : aePair.keySet()) {
            res.add(key+","+aePair.get(key));
        }
        return res;
    }

    public static ArrayList<String> getAbbrExpPair(){
        String path = "./trainingData";
        File file = new File(path);
        File[] files = file.listFiles();
        Map<String,Integer> eePair = new HashMap<>();
        ArrayList<String> res = new ArrayList<>();
        for(File f : files){
            if(f.isDirectory()){
                String dirPath = f.getAbsolutePath();
                ArrayList<String> ctx = Util.readFile(dirPath+"/data.csv");
                for(int i=0;i< ctx.size();i++){
                    String[] line = ctx.get(i).split(",");
                    String abbr = line[1];
                    String identifier = line[2];
                    ArrayList<String> tempId = TokenSplitUtil.split(identifier);
                    for(int k=0;k<tempId.size();k++){
                        String token = tempId.get(k);
                        String key = token + "," + token;
                        if(!token.equals(abbr)){
                            if(eePair.containsKey(key)){
                                int temp = eePair.get(key);
                                eePair.put(key,temp+1);
                            }else{
                                eePair.put(key,1);
                            }
                        }
                    }
                }
            }
        }
        for (String key : eePair.keySet()) {
            res.add(key+","+eePair.get(key));
        }
        return res;
    }

    public static ArrayList<String> combineList(ArrayList<String> aePair, ArrayList<String> eePair){
        ArrayList<String> combine = new ArrayList<>();
        combine.addAll(aePair);
        combine.addAll(eePair);
        return combine;
    }

    public static ArrayList<String> calculate(ArrayList<String> combine){
        ArrayList<String> constructDic = new ArrayList<>();
        for(int i=0;i<combine.size();i++){
            String[] line1 = combine.get(i).split(",");
            String exp1 = line1[0];
            int num1= Integer.parseInt(line1[2]);
            int total=0;
            for(int j=0;j< combine.size();j++){
                String[] line2 = combine.get(j).split(",");
                String exp2= line2[0];
                int num2= Integer.parseInt(line2[2]);
                if(exp1.equals(exp2)){
                    total += num2;
                }
            }
            String newRes = combine.get(i)+","+(num1*1.0)/total;
            constructDic.add(newRes);
        }
        return constructDic;
    }

    public static ArrayList<String> ignoreFirst(ArrayList<String> ctx){
        Map<String,Integer> ignoreDic = new HashMap<>();
        ArrayList<String> ignore = new ArrayList<>();
        for(int i=0;i< ctx.size();i++){
            String[] line = ctx.get(i).split(",");
            String expansion = little2large(line[0]);
            String abbr = little2large(line[1]);
            int num = Integer.parseInt(line[2]);
            String key = expansion +","+abbr;
            if(ignoreDic.containsKey(key)){
                int temp = ignoreDic.get(key);
                ignoreDic.put(key,temp+num);
            }else{
                ignoreDic.put(key,num);
            }
        }

        for (String key : ignoreDic.keySet()) {
            System.out.println(key + " : " + ignoreDic.get(key));
            ignore.add(key+","+ignoreDic.get(key));
        }
        return ignore;
    }

     public static String  little2large(String str){
         String temp = str.substring(0,1).toLowerCase();
         String f = temp+str.substring(1,str.length());
         return f;
     }

     public static ArrayList<String> cleanAbbr(ArrayList<String> ignore){
        ArrayList<String> res = new ArrayList<>();
        ArrayList<String> cleanIgnore = new ArrayList<>();

        for(int i=0;i<ignore.size();i++){
            String[] line = ignore.get(i).split(",");
            String exp = line[0];
            String abbr = line[1];
            if(exp.equalsIgnoreCase(abbr)){
                res.add(ignore.get(i));
            }else{
                if(Dic.isInDict(abbr)){

                }else{
                    res.add(ignore.get(i));
                }
            }
        }
        for(String str: res){
            cleanIgnore.add(str);
        }
        return cleanIgnore;
     }

     public static void calculateIgnoreDic(ArrayList<String> combine){
         for(int i=0;i<combine.size();i++){
             String[] line1 = combine.get(i).split(",");
             String exp1 = line1[0];
             int num1= Integer.parseInt(line1[2]);
             int total=0;
             for(int j=0;j< combine.size();j++){
                 String[] line2 = combine.get(j).split(",");
                 String exp2= line2[0];
                 int num2= Integer.parseInt(line2[2]);
                 if(exp1.equals(exp2)){
                     total += num2;
                 }
             }
             String newRes = combine.get(i)+","+(num1*1.0)/total;
             Util.appendFile(newRes+"\n","./ignoreDic.csv");
         }
     }

    public static void main(String[] args){
        extractDic();
        getAbbrExpPair();
        ArrayList<String> t = combineList( extractDic(), getAbbrExpPair());
        ArrayList<String> res =calculate(t);
        ArrayList<String> ignore = ignoreFirst(res);
        ArrayList<String> cleanIgnore =cleanAbbr(ignore);
        calculateIgnoreDic(cleanIgnore);
    }
}
