package constructDictionary;

import extractMethodName.TokenSplitUtil;
import extractMethodName.Util;

import java.io.File;
import java.util.*;

public class matchIdentifier {

    public static ArrayList<String> dic = Util.readFile("./ignoreDic.csv");

    public static ArrayList<String> filterDic(){
        ArrayList<String> res = new ArrayList<>();
        for(int i=0;i<dic.size();i++){
            String[] line = dic.get(i).split(",");
            String exp = line[0];
            String abbr = line[1];
            int num = Integer.parseInt(line[2]);
            double pro = Double.parseDouble(line[3]);
            if(( pro > 0.6) && (!exp.equalsIgnoreCase(abbr))){
               res.add(dic.get(i));
            }
        }
        return res;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map)
    {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
            {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static ArrayList<String> sortDic(ArrayList<String> usedDic){

        ArrayList<String> res = new ArrayList<>();
        Map<String, Double> unsortDic = new HashMap<>();

        for(int i=0;i< usedDic.size();i++){
            String key = usedDic.get(i);
            String exp = key.split(",")[0];
            int len = exp.replace(" ","").length();
            int absNum = Integer.parseInt(key.split(",")[2]);
            double p = Double.parseDouble(key.split(",")[3]);
            double px = absNum*p*len;
            unsortDic.put(key,px);
        }
        unsortDic = sortByValueDescending(unsortDic);
        for (Map.Entry<String, Double> entry : unsortDic.entrySet()) {
            res.add(entry.getKey());
        }
        return res;
    }

    public static String inDic(String token){
        String res="";
        ArrayList<String> dic1= sortDic(filterDic());

        for(int i=0;i<dic1.size();i++){
            String[] line = dic1.get(i).split(",");
            String exp = line[0];
            String abbr = line[1];

            if(token.equalsIgnoreCase(exp)){
                res=abbr;
                return res;
            }else{
                res = token;
            }
        }
        return res;
    }

    public static ArrayList<String> genCandidate(String methodName){
        ArrayList<String> candidate = new ArrayList<>();

        String[] lines = methodName.split(" ");
        int length = lines.length;
        for(int i=0;i< length;i++){
            String tok1=lines[i];
            String temp=tok1;
            candidate.add(temp+","+inDic(temp));
            for(int j=i+1;j<length;j++){
                 temp+=" "+lines[j];
                 candidate.add(temp+","+inDic(temp));
            }
        }
        return candidate;
    }

    public static String filterCandidate(ArrayList<String> candidate, String methodName){
        ArrayList<String> res = new ArrayList<>();
        for(String s : candidate){
            String[] ss = s.split(",");
            String left = ss[0].trim();
            String right = ss[1].trim();

            if(!left.equalsIgnoreCase(right)){
                res.add(s);
            }
        }
        if(res.size()==0){
            return methodName;
        }else{
            String temp=res.get(0);
            int len = temp.split(",")[0].length();

            for(int i=1;i<res.size();i++){
                String ts = res.get(i);
                int curLen = ts.split(",")[0].length();
                if(curLen > len){
                    temp = ts;
                    len =curLen;
                }
            }
            String[] th = temp.split(",");
            return methodName.replace(th[0],th[1]);
        }
    }

    public static String autoAbbr(String methodName){
        ArrayList<String> t =genCandidate(methodName);
        String res =filterCandidate(t,methodName);
        return res;
    }

    public static ArrayList<String> cir(String curmethodName, String identifier){
        ArrayList<String> res = new ArrayList<>();
        String methodName = curmethodName;
        while(true){
            int correctAbbr=0;
            int incorrectAbbr=0;

            int methodNameLen = methodName.split("").length;
            String tMethodName = autoAbbr(methodName);
            int tMethodNameLen = tMethodName.split(" ").length;

            if(methodName.equals(tMethodName)){
                res.add(methodName+","+tMethodName+","+correctAbbr +","+incorrectAbbr);
                break;
            }

            ArrayList<String> methodNames = calcAccuracy.str2List(methodName);
            ArrayList<String> tMethodNames = calcAccuracy.str2List(tMethodName);

            List<String> sameDF = new ArrayList<>();
            sameDF.addAll(methodNames);
            sameDF.retainAll(tMethodNames);

            List<String> c1 = new ArrayList<String>();
            List<String> d1 = new ArrayList<String>();
            c1.addAll(methodNames);
            d1.addAll(sameDF);
            c1.removeAll(d1);

            List<String> c2 = new ArrayList<String>();
            List<String> d2 = new ArrayList<String>();
            c2.addAll(tMethodNames);
            d2.addAll(sameDF);
            c2.removeAll(d2);

            ArrayList<String> iden = TokenSplitUtil.split(identifier);
            res.add(methodName+","+tMethodName+","+correctAbbr +","+incorrectAbbr);
            methodName = tMethodName;
        }
        return res;
    }

    public static void match(){
        String path = "./testingData";
        File file = new File(path);
        File[] files = file.listFiles();
        int tokens = 0;
        for(File f : files){
            String dirPath = f.getAbsolutePath();
            if(dirPath.endsWith("F1.csv")){
                ArrayList<String> ctx = Util.readFile(dirPath);
                for(int i=0;i<ctx.size();i++){
                    String[] line = ctx.get(i).split(",");
                    String filePath = line[0];
                    String methodName = line[4];
                    String identifier = line[1];
                    String label = line[2];
                    String orgialMethod = methodName;
                    String[] tempToken = methodName.split(" ");
                    tokens += tempToken.length;

                if(methodName.replaceAll(" ","").length()<=30){
                    extractMethodName.Util.appendFile(filePath+","+identifier+","+label+","+orgialMethod+","+methodName+"\n","./testingData/testing.csv");
                }else{
                    ArrayList<String> res =cir(methodName,identifier);
                    int kcorrect =0;
                    int kincorrect = 0;
                    for(int k=0;k<res.size();k++){
                        String[] kline = res.get(k).split(",");
                        int temp1 = Integer.parseInt(kline[2]);
                        int temp2 = Integer.parseInt(kline[3]);
                        kcorrect+= temp1;
                        kincorrect+= temp2;
                    }
                    Util.appendFile(filePath+","+identifier+","+label+","+orgialMethod+","+res.get(res.size()-1).split(",")[1]+"\n","./testingData/testing.csv");
                }
                }
            }
        }
    }

        public static boolean containsIgnoreCase(ArrayList<String> list, String soughtFor) {
            for (String current : list) {
                if (current.equalsIgnoreCase(soughtFor)) {
                    return true;
                }
            }
            return false;
        }

    public static void test(String identifier, String label){
        List<String> identifierList = TokenSplitUtil.split(identifier);
        List<String> labelList = calcAccuracy.str2List(label);

        List<String> sameDF = new ArrayList<>();
        sameDF.addAll(identifierList);
        sameDF.retainAll(labelList);
        List<String> c2 = new ArrayList<String>();
        List<String> d2 = new ArrayList<String>();
        c2.addAll(labelList);
        d2.addAll(sameDF);
        c2.removeAll(d2);
    }

    public static void main(String[] args){
        System.out.println("---------------");
        match();
    }
}
