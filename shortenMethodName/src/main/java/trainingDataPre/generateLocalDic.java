package trainingDataPre;

import constructDictionary.Dic;
import constructDictionary.constructDic;
import extractMethodName.TokenSplitUtil;
import extractMethodName.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class generateLocalDic {


  // get the aePair
    public static void sameProjectDic(){
        String dir = "./trainingData";
        File file = new File(dir);
        File[] files = file.listFiles();

        String projectName="";
        for(File f: files){
            String path = f.getAbsolutePath()+"/data.csv";
             projectName = f.getName();
            File file1 = new File(path);
            if(file1.exists()){
                String path1= file1.getAbsolutePath();
                Map<String, Integer> aePair = new HashMap<>();
                ArrayList<String> ctx = Util.readFile(path1);

                for(int i=0;i< ctx.size();i++){
                    String[] line =ctx.get(i).split(",");
                    String abbr = line[1];
                    String expansion = constructDic.handleExp(constructDic.handleExpformat(line[4]));
                    String key = expansion +","+abbr;
                    if(aePair.containsKey(key)){
                        int temp = aePair.get(key);
                        aePair.put(key,temp+1);
                    }else{
                        aePair.put(key,1);
                    }
                }
                for(String key: aePair.keySet()){
                    System.out.println(key +" : "+aePair.get(key));
                    Util.appendFile(key+","+aePair.get(key)+"\n","./trainingData/"+projectName+"aePair.csv");
                }
            }
        }
    }

  // get the eePair

  public static void getEEpair(){
        String path = "./trainingData";
        File file = new File(path);
        File[] files = file.listFiles();
        String dirName = "";
        for(File f: files){
            String dirPath = f.getAbsolutePath()+"/data.csv";
             dirName = f.getName();
            File file1 = new File(dirPath);
            if(file1.exists()){
                String path1 = file1.getAbsolutePath();
                ArrayList<String> ctx = Util.readFile(path1);
                Map<String, Integer> eePair = new HashMap<>();
                for(int i=0;i<ctx.size();i++){
                    String[] line = ctx.get(i).split(",");
                    String abbr = line[1];
                    String identifier = line[2];
                    ArrayList<String> tempId = TokenSplitUtil.split(identifier);
                    for(int k=0;k< tempId.size();k++){
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
                for(String key : eePair.keySet()){
                    System.out.println(key + " : " + eePair.get(key));
                    Util.appendFile(key+","+eePair.get(key)+"\n","./trainingData/"+dirName+"eePair.csv");
                }
            }
        }
  }

  // combine aePair and eePair

    public static void combinePair(){
        String dir = "./trainingData";
        File file = new File(dir);
        File[] files = file.listFiles();
        ArrayList<String> allName = new ArrayList<>();
        for(File f:files){
            String fileName = f.getName();
            allName.add(fileName);
        }

        for(String fileName:allName){
            String aePath = "./trainingData/"+fileName+"aePair.csv";
            String eePath = "./trainingData/"+fileName+"eePair.csv";
            ArrayList<String> aePair = Util.readFile(aePath);
            ArrayList<String> eePair = Util.readFile(eePath);

            ArrayList<String> combine = new ArrayList<>();
            combine.addAll(aePair);
            combine.addAll(eePair);

            if(combine.size() !=0){
                for(int i=0;i< combine.size();i++){
                    String line = combine.get(i);
                    Util.appendFile(line+"\n","./trainingData/"+fileName+"combine.csv");
                }
            }
        }
    }

    public static String little2large(String str){
        String temp = str.substring(0,1).toLowerCase();
        String f = temp+str.substring(1,str.length());
        return  f;
    }


    public static void calculate(ArrayList<String> combine, String project){
        ArrayList<String> step1 = new ArrayList<>();
        ArrayList<String> step2 = new ArrayList<>();
        ArrayList<String> step3 = new ArrayList<>();
        for(int i=0;i<combine.size();i++){
            String[] line1 = combine.get(i).split(",");
            String exp1 = line1[0];
            int num1 = Integer.parseInt(line1[2]);
            int total = 0;

            for(int j=0;j<combine.size();j++){
                String[] line2 = combine.get(j).split(",");
                String exp2 = line2[0];
                int num2 = Integer.parseInt(line2[2]);
                if(exp1.equals(exp2)){
                    total += num2;
                }
            }
            String newRes = combine.get(i)+","+((num1*1.0)/total);
            step1.add(newRes);
        }
        // ignore First
        Map<String, Integer> ignoreDic = new HashMap<>();

        for(int i=0;i<step1.size();i++){
            String[] line = step1.get(i).split(",");
            String expansion = little2large(line[0]);
            String abbr = little2large(line[1]);
            int num = Integer.parseInt(line[2]);

            String key = expansion +","+abbr;

            if(ignoreDic.containsKey(key)){
                int temp = ignoreDic.get(key);
                ignoreDic.put(key,temp+num);
            }else{
                ignoreDic.put(key, num);
            }
        }

        for(String key:ignoreDic.keySet()){
            String item1= key+","+ignoreDic.get(key);
            step2.add(item1);
        }
        //---clean Abbr
        ArrayList<String> res = new ArrayList<>();

        for(int i=0;i< step2.size();i++){
            String[] line = step2.get(i).split(",");
            String exp = line[0];
            String abbr = line[1];
            if(exp.equalsIgnoreCase(abbr)){
                res.add(step2.get(i));
            }else{
                if(Dic.isInDict(abbr)){

                }else{
                    res.add(step2.get(i));
                }
            }
        }

        for(String str:res){
            step3.add(str);
        }

        for(int i=0;i<step3.size();i++){
            String[] line1 = step3.get(i).split(",");
            String exp1 = line1[0];
            int num1 = Integer.parseInt(line1[2]);
            int total = 0;

            for(int j=0;j<step3.size();j++){
                String[] line2 = step3.get(j).split(",");
                String exp2 = line2[0];
                int num2 = Integer.parseInt(line2[2]);
                if(exp1.equals(exp2)){
                    total += num2;
                }
            }
            String newRes = step3.get(i)+","+((num1*1.0)/total);
            Util.appendFile(newRes+"\n","./trainingData/"+project+"Dic.csv");
        }
    }

    public static void generateDic(){
        String path = "./trainingData/combinePair";
        File file = new File(path);
        File[] files = file.listFiles();
        for(File f:files){
            String fileName = f.getName();
            String filePath = f.getAbsolutePath();
            String project = fileName.replace("combine.csv","");
            ArrayList<String> combine = Util.readFile(filePath);
            calculate(combine,project);
        }
    }

    public static void main(String[] args){
        System.out.println("-----------------");
        sameProjectDic();
        getEEpair();
        combinePair();
        generateDic();
    }
}
