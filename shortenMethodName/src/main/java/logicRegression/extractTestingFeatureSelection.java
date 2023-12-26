package logicRegression;

import extractMethodName.Util;

import java.io.IOException;
import java.util.*;

import static logicRegression.invokeModel.invokeGenModel;
import static trainingDataPre.step3.*;

public class extractTestingFeatureSelection {
    public static ArrayList<String> EnglishDic = Util.readFile("./EnglishDic.txt");

    public static String getProjectNameFromPath(String path){
        String labelStr = "D:\\Abbreviations\\MethodName\\20220519\\project\\";
        int pos = path.indexOf(labelStr);
        String temp1 = path.substring(pos+ labelStr.length());
        int pos1 = temp1.indexOf("\\");
        String temp = temp1.substring(0,pos1);
        return temp;
    }

    public static ArrayList<String> localDic(String project){
        String dir = "D:\\Abbreviations\\LRegression\\project\\dic\\sameProDic\\eachDic\\"+project+"Dic.csv";
        ArrayList<String> local = Util.readFile(dir);
        return local;
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

    public static ArrayList<String> cir(String input, ArrayList<String> dic1k, ArrayList<String> localDic,String allInput, String handLabel){
        System.out.println("recursive began: "+input);
        ArrayList<String> candidate = generateCandidate(input);
        ArrayList<String> res = new ArrayList<>();
        ArrayList<String> mayRes = new ArrayList<>();

        for(int i=0;i< candidate.size();i++){
            String token = candidate.get(i);
            ArrayList<String> inDic = isInDic(dic1k,localDic, token);
            if(inDic.size() == 0){
//                System.out.println("not in dic");
                continue;
            }
            for(String s: inDic){
                res.add(input+"@@"+s);
            }
        }

        if(res.size() == 0){
            System.out.println("no dictionary word");
            return mayRes;
        }
        int hadAbbrNum=0;
        String[] allInputs = allInput.split(",");
        String allInput_2 = allInputs[2];
        String allInput_3 = allInputs[3];
        if(allInput_2.equalsIgnoreCase(allInput_3)){
            hadAbbrNum =0;
        }else{
            hadAbbrNum = allInput_2.split(" ").length - allInput_3.split(" ").length +1;
        }

        for(String s: res){
            String[] ss = s.split("@@")[1].split(",");
            String currentFullName = s.split("@@")[0];
            System.err.println("current: " + currentFullName);
            System.err.println(allInput);
            String exp1 = ss[0];
            String abbr1 = ss[1];

            int pos =0;
            int prev = 0;
            int post =0;
            int postLabel = 0;
            int prevLabel = 0;

            ArrayList<String> inputList = string2list(input);
            String[] inputs = input.split(" ");
            int index = input.toLowerCase().indexOf(exp1.toLowerCase());
            if(index == -1){
                System.out.println("input does not inclue this exp");
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
            int currentFullNameToken = currentFullName.split(" ").length;
            int currentAbbrToken = exp1.split(" ").length;
            int numOfHueristicAbbr = numOfHeuristic(input,handLabel);

            String dicLocalValue = getValueP(localDic,exp1+","+abbr1);
            int locNum1 = Integer.parseInt(dicLocalValue.split(",")[0]);
            double locNum2 = Double.parseDouble(dicLocalValue.split(",")[1]);
            String dic1kValue = getValueP(dic1k,exp1+","+abbr1);
            int num1 = Integer.parseInt(dic1kValue.split(",")[0]);
            double num2 = Double.parseDouble(dic1kValue.split(",")[1]);
            String tempRes1 = input+","+exp1+","+abbr1+","+prevLabel+","+postLabel+","+currentIdentifierLength+","+currentAbbrIdenLength+","+czIdentifier+","+num1+","+num2+","+
                    locNum1 +","+locNum2+","+ currentFullNameToken +","+currentAbbrToken+","+numOfHueristicAbbr+","+(numOfHueristicAbbr+currentAbbrToken)+","+hadAbbrNum;
            mayRes.add(tempRes1);
        }
        return mayRes;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortDescend(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> returnMap = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            returnMap.put(entry.getKey(), entry.getValue());
        }
        return returnMap;
    }


    public static String selectWord(ArrayList<String> prob){
        String res="";
        String label = "";
        Map<String,Double> myMap = new HashMap<>();
       for(String s: prob){
          String[] ss = s.split(",");
          String key =ss[0]+","+ss[1]+","+ss[2];
          double value = Double.parseDouble(ss[3]);
          myMap.put(key,value);
       }
       Map<String,Double> sortedMap = sortDescend(myMap);
       double maxProb = 0.0;
        for (String key : sortedMap.keySet()) {
            maxProb = sortedMap.get(key);
            label = key;
            break;
        }
        if(maxProb > 0.95){
            res = label;
        }
        return res;
    }
    public static ArrayList<String> whichAbbr(ArrayList<String> mayRes) throws IOException, InterruptedException {

        ArrayList<String> prob = new ArrayList<>();
          for(int i=0;i< mayRes.size();i++){
              String[] line = mayRes.get(i).split(",");
              String fullName = line[0];
              String exp = line[1];
              String abbr = line[2];
              String feature = line[3]+" "+line[4]+" "+line[5]+" "+line[6]+" "+line[7]+" "+line[10]+" "+line[11]+" "+line[12]+" "+line[13] +" "+line[14]+" "+line[15]+" "+line[16];
              double s = invokeGenModel(feature);
              prob.add(fullName+","+exp+","+abbr+","+s);
          }
          return prob;
    }

    public static int numOfHeuristic(String input, String origExp){
        String[] inputs = input.split(" ");
        ArrayList<String> inputList = new ArrayList<>();
        for(int i=0;i< inputs.length;i++){
            inputList.add(inputs[i].toLowerCase());
        }
        String[] origExps = origExp.split(" ");
        ArrayList<String> origList = new ArrayList<>();
        for(int i=0;i< origExps.length;i++){
            origList.add(origExps[i].toLowerCase());
        }
        int num=0;
        for(String str: origList){
            if(!inputList.contains(str)){
                num++;
            }
        }
        return num;
    }

    public static String recursive(String currentInput, ArrayList<String> dic1k,ArrayList<String> localDic,String allInput, String handLabel) throws IOException, InterruptedException {
        String input = currentInput;
        int invokeModel=0;
        while(true){
            ArrayList<String> mayRes = cir(input,dic1k,localDic,allInput, handLabel);
            if(mayRes.size() == 0){
                break;
            }
            ArrayList<String> prob =whichAbbr(mayRes);
            String s = selectWord(prob);
            invokeModel++;
            if(s.equalsIgnoreCase("")){
                break;
            }
            String[] newS = s.split(",");
            String newSidentifier = newS[0];
            String newSexp = newS[1];
            String newSabbr = newS[2];
            String tInput = newSidentifier.replace(newSexp,newSabbr);
            input = tInput;
        }
       return input;
    }

    public static String extractEachFeature(String input) throws IOException, InterruptedException {
          String[] inputs = input.split(",");
          String projectName = getProjectNameFromPath(inputs[0]);
         ArrayList<String> dic1k = filter1Kdic();
         ArrayList<String> localDic = filterLocalDic(localDic(projectName));
         String handLabel =inputs[2];
         String heuristic = inputs[3];
         String afterLG = recursive(inputs[3].toLowerCase(),dic1k,localDic,input,handLabel);
         return afterLG;
    }

    public static void extractFeature() throws IOException, InterruptedException {
        String dir = "./testingData/testing.csv";
        ArrayList<String> context = Util.readFile(dir);

        for(int i=0;i< context.size() ;i++){
            String[] lines = context.get(i).split(",");
            String line = lines[0]+","+lines[1]+","+lines[2]+","+lines[4];
            String prediction = extractEachFeature(line);
            String result = context.get(i)+","+prediction;
            Util.appendFile(result+"\n","./testingData/testing-feature.csv");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("-----------------");
        extractFeature();
    }
}
