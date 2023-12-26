package trainingDataPre;

import extractMethodName.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class extractFeature4Train {

    public static ArrayList<String> englishDic = Util.readFile("./EnglishDic.txt");

    public static ArrayList<String> filter1Kdic(){
       ArrayList<String> origDic = Util.readFile("./ignoreDic.csv");
       ArrayList<String> dic1K = new ArrayList<>();
       for(String item:origDic){
           String[] items = item.split(",");
           String exp  = items[0];
           String abbr = items[1];
           if((!exp.equals(abbr)) && (abbr.length() >=3)){
               dic1K.add(item);
           }
       }
       return cleanDic(dic1K);
    }

    public static ArrayList<String> filterLocalDic(ArrayList<String> localDic){
        ArrayList<String> dicLocal = new ArrayList<>();
        for(String item:localDic){
            String[] items = item.split(",");
            String exp  = items[0];
            String abbr = items[1];
            if((!exp.equals(abbr)) && (abbr.length() >=3)){
                dicLocal.add(item);
            }
        }
        return cleanDic(dicLocal);
    }


    public static ArrayList<String> cleanDic(ArrayList<String> dic){
        Map<String,String> cleanedDic = new HashMap<>();
        ArrayList<String> dicList = new ArrayList<>();
        for(String item: dic){
            String[] items = item.split(",");
            String exp  = items[0];
            String abbr = items[1];
            int v1 = Integer.parseInt(items[2]);
            double v2 = Double.parseDouble(items[3]);
            String key = exp.toLowerCase()+","+abbr.toLowerCase();
            if(cleanedDic.containsKey(key)){
                String value = cleanedDic.get(key);
                String[] values = value.split(",");
                int num1 = Integer.parseInt(values[0]);
                double num2 = Double.parseDouble(values[1]);
                String tempValue = ""+(v1+num1)+","+(v2+num2);
                cleanedDic.put(key,tempValue);
            }else{
                cleanedDic.put(key,""+v1+","+v2);
            }
        }

        Set keys = cleanedDic.keySet();
        for (Object key : keys) {
            dicList.add(key+","+cleanedDic.get(key));
        }
        return dicList;
    }

    public static ArrayList<String> localDic(String project){
        String dir = "D:\\Abbreviations\\dic\\sameProDic\\eachDic\\"+project+"Dic.csv";
        ArrayList<String> local = Util.readFile(dir);
        return local;
    }

    public static boolean listContain(ArrayList<String> list, String line){
        String[] lines = line.split(",");
        String testLine = lines[0].toLowerCase()+","+lines[1].toLowerCase();
        for(int i=0;i< list.size();i++){
            String[] dicEach = list.get(i).split(",");
            String dicLine = dicEach[0].toLowerCase()+","+dicEach[1].toLowerCase();
            if(dicLine.equalsIgnoreCase(testLine)){
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> isInDic(ArrayList<String> dic1k, ArrayList<String> localDic, String token){
        ArrayList<String> res = new ArrayList<>();

        for(int i=0;i<dic1k.size();i++){
            String line = dic1k.get(i);
            String[] lines = line.split(",");
            String exp = lines[0];
            String abbr = lines[1];
            if(exp.equalsIgnoreCase(token)){
                res.add(line);
            }
        }
        // handle local dictionary
        for(int k=0;k< localDic.size();k++){
            String line = localDic.get(k);
            String[] lines = line.split(",");
            String exp = lines[0];
            String abbr = lines[1];
            if(exp.equalsIgnoreCase(token)){
                if(listContain(res,line)){

                }else{
                    res.add(line);
                }
            }
        }
        return res;
    }

    public static ArrayList<String> generateCandidate(String input){

        ArrayList<String> candidate = new ArrayList<>();
        String[] words = input.split(" ");
        int length = words.length;
        for(int i=0;i<length;i++){
            String tok1 = words[i];
            String temp=tok1;
            candidate.add(temp);
            for(int j=i+1;j<length;j++){
                temp += " "+ words[j];
                candidate.add(temp);
            }
        }
        return candidate;
    }

    public static ArrayList<String> autoAbbrCandidate(String input, ArrayList<String> dic1K, ArrayList<String> localDic){
        ArrayList<String> res = new ArrayList<>();
        ArrayList<String> candidate = generateCandidate(input);
        for(int i=0;i< candidate.size();i++){
            String token = candidate.get(i);
            ArrayList<String> inDic = isInDic(dic1K,localDic,token);
            if(inDic.size() == 0){
            }else{
                for(String s: inDic){
                    String[] ss = s.split(",");
                    String exp  = ss[0];
                    String abbr = ss[1];
                    String curInput =input.toLowerCase().replace(exp,abbr);
                    res.add(curInput +","+s);
                }
            }
        }
        return res;
    }

    public static ArrayList<String> string2list(String s){
        ArrayList<String> res = new ArrayList<>();
        String[] ss = s.split(" ");
        for(int i=0;i< ss.length;i++){
            res.add(ss[i]);
        }
        return res;
    }

    public static boolean isAbbr(String token){
        for(String s:englishDic){
            if(s.equalsIgnoreCase(token)){
                return true;
            }
        }
        return false;
    }

    public static String getValueP(ArrayList<String> list, String line){
        String res = 0+","+0;
        String testLine = line.toLowerCase();
        for(int i=0;i< list.size();i++){
            String[] each = list.get(i).split(",");
            String eachLine = each[0].toLowerCase()+","+each[1].toLowerCase();
            if(eachLine.equalsIgnoreCase(testLine)){
                res = each[2]+","+each[3];
                return res;
            }else{
                res = 0+","+0;
            }
        }
        return res;
    }


    public static String terminateCondition(String ctx,String s, String input, String project, ArrayList<String> dic, ArrayList<String> localDic){
        String[] inputs = input.split(" ");
        String[] ss = s.split(",");
        String exp1 = ss[1];
        String abbr1 = ss[2];
        String dic1kValue = getValueP(dic,exp1+","+abbr1);
        int num1 = Integer.parseInt(dic1kValue.split(",")[0]);
        double num2 = Double.parseDouble(dic1kValue.split(",")[1]);
        int pos = 0;
        int prev = 0;
        int post = 0;
        int postLabel=0;
        int prevLabel=0;

        ArrayList<String> inputList = string2list(input);
           int index = input.toLowerCase().indexOf(exp1.toLowerCase());
               if(index == -1){
                  return "";
                }else{
                   for(int k=0;k<inputList.size();k++){
                       String temp = inputList.get(k);
                       if(exp1.split(" ")[0].equalsIgnoreCase(temp)){
                           pos =k;
                       }
                   }
                }
            if(pos == 0 ){
                prevLabel = -1;
                int post1 = pos+ exp1.split(" ").length;
                if(post1 > inputList.size()-1){
                    postLabel =-1;
                }else{
                       if(isAbbr(inputs[post1])){
                        postLabel =1;
                    }else{
                        postLabel=0;
                     }
                  }
            }else if(pos == inputs.length-1){
                postLabel = -1;
                prev =pos-1;//pos-exp1.split(" ").length;
                if(prev < 0){
                    prevLabel =-1;
                }else{
                    if(isAbbr(inputs[prev])){
                        prevLabel = 1;
                    }else{
                        prevLabel =0;
                    }
                }
            }else{
                prev = pos-1;//pos-exp1.split(" ").length;
                post = pos+exp1.split(" ").length;

                if(prev <0){
                    prevLabel =-1;
                }else{
                    if(isAbbr(inputs[prev])){
                        prevLabel = 1;
                    }else{
                        prevLabel = 0;
                    }
                }
                if(post > inputList.size()-1){
                    postLabel = -1;
                }else{
                    if(isAbbr(inputs[post])){
                        postLabel =1;
                    }else{
                        postLabel=0;
                    }
                }
            }

            int currentIdentifierLength = input.toLowerCase().replaceAll(" ","").length();
            int currentAbbrIdenLength = input.toLowerCase().replace(exp1,abbr1).replaceAll(" ","").length();
            int czIdentifier = currentIdentifierLength - currentAbbrIdenLength;
            String dicLocalValue = getValueP(localDic,exp1+","+abbr1);
            int locNum1 = Integer.parseInt(dicLocalValue.split(",")[0]);
            double locNum2 = Double.parseDouble(dicLocalValue.split(",")[1]);

            String tempRes = ctx+","+input+","+exp1+","+abbr1+","+prevLabel+","+postLabel+","+currentIdentifierLength+","+currentAbbrIdenLength+","+czIdentifier+","+num1+","+num2+","+
                    locNum1 +","+locNum2;
            return tempRes;
    }

    public static void recursive(String input, ArrayList<String> dic1k, ArrayList<String> localDic, String ctx, String project){

          Map<String, ArrayList<String>> allCandidate = new HashMap<>();
          Map<String, ArrayList<String>> mayCandidate = new HashMap<>();

          allCandidate.put(input, autoAbbrCandidate(input,dic1k,localDic));
          int hadAbbr=0;

          while(true){
              for (Map.Entry<String, ArrayList<String>> entry : allCandidate.entrySet()) {
                  String key = entry.getKey();
                  ArrayList<String> candidate = entry.getValue();
                  for(String str: candidate){
                      String feature = terminateCondition(ctx,str,key,project,dic1k,localDic);
                      String allFeature = feature+","+hadAbbr;
                      Util.appendFile(allFeature+"\n","./trainingData/"+project+"_feature.csv");
                      mayCandidate.put(str.split(",")[0],autoAbbrCandidate(str.split(",")[0],dic1k,localDic));
                  }
              }
              allCandidate.clear();
              allCandidate.putAll(mayCandidate);
              mayCandidate.clear();
              int n=0;
              for (Map.Entry<String, ArrayList<String>> may : allCandidate.entrySet()) {
                  if(may.getValue().size() == 0){
                      n++;
                  }
              }
              if(n == allCandidate.size()){
                  break;
              }
              hadAbbr++;
          }
    }

    public static void step3_1(){
        File file = new File("./trainingData/Heuristic1new");
        File[] files = file.listFiles();
        ArrayList<String> dic = filter1Kdic();
        for(File f: files){
            String path = f.getAbsolutePath();
            String name = f.getName();
            int pos = name.indexOf(".csv");
            String project = name.substring(0,pos);
            ArrayList<String> localDic = filterLocalDic(localDic(project));
            ArrayList<String> ctx = Util.readFile(path);
            for(int i=0;i<ctx.size();i++){
                String[] lines = ctx.get(i).split(",");
                String input = lines[4];
                System.out.println("input: "+ input);
                recursive(input, dic,localDic,ctx.get(i),project);
            }
        }
    }

    public static void main(String[] args){
        step3_1();
    }
}
