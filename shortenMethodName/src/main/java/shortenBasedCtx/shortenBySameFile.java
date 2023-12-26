package shortenBasedCtx;


import extractMethodName.TokenSplitUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class shortenBySameFile {

    public static boolean containsIgnoreCase(String list, String soughtFor) {

            if (list.toLowerCase().contains(soughtFor.toLowerCase())) {
                return true;
            }

        return false;
    }

    public static boolean containsIgnoreCase1(ArrayList<String> list, String soughtFor) {
        for (String current : list) {
            if (current.equalsIgnoreCase(soughtFor)) {
                return true;
            }
        }
        return false;
    }

    public static String calculate(String methodName, String tMethodName,String identifier){
        int correctAbbr=0;
        int incorrectAbbr=0;

        ArrayList<String> methodNames = calcAccuracy.str2List(methodName);
        ArrayList<String> tMethodNames = calcAccuracy.str2List(tMethodName);

        if(methodName.equals(tMethodName)){
            return correctAbbr+","+incorrectAbbr;
        }

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
        if(containsIgnoreCase1(iden,c2.get(0))){
            correctAbbr=c1.size();
        }else{
            incorrectAbbr=c1.size();
        }
        return correctAbbr+","+incorrectAbbr;
    }

     public static void match(String project){
         String dir = "./testingData/"+project+"Final.csv";
         File file = new File("./testingData/"+project+"F1.csv");
         if(file.exists()){
             file.delete();
         }
         ArrayList<String> zjTemp= new ArrayList<>();
         ArrayList<String> ctx = Util.readFile(dir);
         for(int i=0;i<ctx.size();i++){
             String line =ctx.get(i);
             String[] lines = line.split(",",-1);
             String identifier = lines[1];
             String fullName = lines[2].toLowerCase();
             int correct=0;
             int incorrect=0;
             if(lines.length == 4){
                 String base = lines[3];
                 String tMethod = fullName;
                 String[] bases = base.split(";");
                 for(int j=0;j<bases.length;j++){
                     String each = bases[j];
                     String[] eachs = each.split("@@");
                     if(eachs.length==1){
                        correct +=0;
                        incorrect +=0;
                     }else{
                         String b1= eachs[0].toLowerCase();
                         String b2= eachs[1].toLowerCase();

                         if(containsIgnoreCase(tMethod,b2)){
                             String temp="";
                             if((b2.endsWith("s") || b2.endsWith("es") || b2.endsWith("ies")) &&(!b1.endsWith("s"))){
                                 temp = tMethod.replaceAll(b2,b1+"s");
                             }else{
                                 temp = tMethod.replaceAll(b2,b1);
                             }
                             String num = calculate(tMethod,temp,identifier);
                             correct =Integer.parseInt(num.split(",")[0]);
                             incorrect =Integer.parseInt(num.split(",")[1]);
                             tMethod = temp;

                         }
                     }
                 }
                 Util.appendFile(line + ","+tMethod +"\n","./testingData/"+project+"F1.csv");
                 zjTemp.add(line + ","+tMethod +","+correct +","+incorrect);
             }else{
                 Util.appendFile(line + ","+fullName+"\n","./testingData/"+project+"F1.csv");
                 zjTemp.add(line + ","+fullName+","+correct +","+incorrect);
             }
         }

     }

    public static void main(String[] args){
        System.out.println("----------------");
        String[] projects = new String[]{"apollo","batik","dubbo","guava","jadx","springboot","zookeeper"};

        for(int i=0;i< projects.length;i++){
            String project = projects[i];
            getCandidate.getAbbr(project);
            match(project);
        }
    }
}
