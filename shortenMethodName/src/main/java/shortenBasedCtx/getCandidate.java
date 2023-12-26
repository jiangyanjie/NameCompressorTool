package shortenBasedCtx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class getCandidate {

    public static ArrayList<String> enDic(){
        return Util.readFile("./EnglishDic.txt");
    }

    public static ArrayList<String> dic = enDic();

    public static ArrayList<String> str2List(String str){
        String[] lines = str.split(" ");
        ArrayList<String> res = new ArrayList<>();
        for(int i=0;i< lines.length;i++){
            res.add(lines[i]);
        }
        return res;
    }
public static String handleFs(String str){
        String[] strs = str.split("@@");
        String abbr = strs[0];
        String full = strs[1];
        return abbr+"s"+"@@"+full;
}
    public static ArrayList<String> handleMethods(String methods,String fullName){
        String[] method = methods.split(";");
        ArrayList<String> res = new ArrayList<>();
        for(int i=0;i< method.length;i++){
            if(!method[i].equals("")){
                ArrayList<String> methodTokens = Util.split(method[i]);
                for(String s:methodTokens){

                      String temp1= Heu.H1(s.toLowerCase(),str2List(fullName));
                      if(!temp1.contains("@@null")){
                          res.add(temp1+"@@H1");
                      }

                      String temp2= Heu.H2(s.toLowerCase(),str2List(fullName));

                      if(!temp2.contains("@@null")){
                          res.add(temp2+"@@H2");
                      }
                      String temp3= Heu.H3(s,str2List(fullName));
                      if(!temp3.contains("@@null")){
                          res.add(temp3+"@@H3");
                      }
                }
            }
        }
        return res;
    }


    public static String getAbbrExpPair(ArrayList<String> res){
        String result="";
           Map<String,Integer> myMap = new HashMap<>();
           ArrayList<String> finalList = new ArrayList<>();
           ArrayList<String> updateFinalList = new ArrayList<>();
           ArrayList<String> updateFinalList1 = new ArrayList<>();
           for(int i=0;i<res.size();i++){
               String[] line = res.get(i).split("@@");
               String abbr = line[0];
               String full = line[1];
               if(!abbr.equals(full)){
                   if(myMap.containsKey(res.get(i))){
                       int n = myMap.get(res.get(i));
                       myMap.put(res.get(i),n+1);
                   }else{
                       myMap.put(res.get(i),1);
                   }
               }
           }

        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(myMap.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });

        for(Map.Entry<String,Integer> mapping:list){
            String key = mapping.getKey().split("@@")[0];
            if(!isInList(finalList,key)){
                finalList.add(mapping.getKey());
            }
        }

        for(String s:finalList){
           String[] ss = s.split("@@");
           String abbr1= ss[0];
           String exp1= ss[1];
           String heu1= ss[2];
           if(dic.contains(abbr1) || (abbr1+"s").equals(exp1)){

           }else{
               updateFinalList.add(s);
           }
        }

        for(String s:updateFinalList){
            String[] ss = s.split("@@");
            String abbr1= ss[0];
            String exp1= ss[1];
            String heu1= ss[2];

            if(abbr1.length() !=1 ){

                if(abbr1.endsWith("s")){
                    if(abbr1.length()!=2){
                        if(heu1.equals("H3")){
                            for(int i=0;i<updateFinalList.size();i++){
                                String[] line =updateFinalList.get(i).split("@@");
                                String abbr2= line[0];
                                String exp2= line[1];
                                if((!s.equals(updateFinalList.get(i))) && (s.contains(exp2)) && (abbr2.length()>=3)){
                                    updateFinalList1.add(s);
                                }
                            }
                        }else{
                            updateFinalList1.add(s);
                        }
                    }
                }else{
                    if(abbr1.length() !=2){
                        if(heu1.equals("H3")){
                            for(int i=0;i<updateFinalList.size();i++){
                                String[] line =updateFinalList.get(i).split("@@");
                                String abbr2= line[0];
                                String exp2= line[1];
                                if((!s.equals(updateFinalList.get(i))) && (s.contains(exp2)) && (abbr2.length()>=3)){
                                    updateFinalList1.add(s);
                                }
                            }
                        }else{
                            updateFinalList1.add(s);
                        }
                    }

                }
            }
        }

        for(String s:updateFinalList1){
            result += s+";";
        }

        return result;
    }


    public static boolean isInList(ArrayList<String> list, String str){
        if(list.size() == 0){
            return false;
        }
        for(int i=0;i< list.size();i++){
            String line = list.get(i);
            if(line.startsWith(str)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public static void getAbbr(String project){
        String dir = "./testingData/"+project+".csv";

        File file = new File("./testingData/"+project+"Final.csv");
        if(file.exists()){
            file.delete();
        }

        ArrayList<String> ctx = Util.readFile(dir);
        for(int i=0;i<ctx.size();i++){
            String[] line = ctx.get(i).split(",",-1);
            String fullName = line[2];
            String methods = line[3];
            ArrayList<String> candidate = handleMethods(methods,fullName);
            String result = getAbbrExpPair(candidate);
            appendFile(line[0]+","+line[1]+","+line[2]+","+result+"\n","./testingData/"+project+"Final.csv");
        }
    }

    public static void appendFile(String line, String path){
        FileWriter fw = null;

        try{
            File f = new File(path);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter pw = new PrintWriter(fw);
        pw.print(line);
        pw.flush();
        try{
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
