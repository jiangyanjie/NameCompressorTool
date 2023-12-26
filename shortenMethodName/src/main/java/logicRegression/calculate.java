package logicRegression;

import constructDictionary.calcAccuracy;
import extractMethodName.TokenSplitUtil;
import extractMethodName.Util;

import java.util.ArrayList;
import java.util.List;

public class calculate {

    public static void calculateByIdentifier(){
        String dir ="./testingData/testing-feature.csv";
        ArrayList<String> ctx = Util.readFile(dir);
        int noAbbr=0;
        int hadAbbr=0;
        int successAbbr=0;
        int successAbbr1=0;
        int failAbbr=0;
        int failAbbr1=0;

        for(int i=0;i<ctx.size();i++){
            String line= ctx.get(i);
            String[] lines = line.split(",");
            String identifier = lines[1];
            String hand = lines[2];
            String heuristic = lines[3].replaceAll(" ","");
            String afterLG = lines[5];
            if(identifier.equalsIgnoreCase(afterLG.replaceAll(" ",""))){
                successAbbr1++;}
            if(!identifier.equalsIgnoreCase(afterLG.replaceAll(" ",""))){
                failAbbr1++;}

            if(hand.replaceAll(" ","").equalsIgnoreCase(afterLG.replaceAll(" ",""))){
                noAbbr++;
            }else{
                hadAbbr++;
                if(identifier.equalsIgnoreCase(afterLG.replaceAll(" ",""))){
                    successAbbr++;
                }else{
                    failAbbr++;
                }
            }
        }
        double precision = (successAbbr*1.0)/(successAbbr+failAbbr);
        double recall = (successAbbr*1.0)/(700);
        System.out.println("noAbbr " + noAbbr);
        System.out.println("hadAbbr " + hadAbbr);
        System.out.println("successAbbr " + successAbbr);
        System.out.println("failAbbr " + failAbbr);
        System.out.println("precision: " + precision);
        System.out.println("recall: " + recall );
        System.out.println("F1: " + (2*precision*recall)/(precision+recall));
        System.out.println("---------------------------------");
        System.out.println("failAbbr1 " + failAbbr1);
        System.out.println("successAbbr " + successAbbr1);
    }

    public static ArrayList<String> calculateByWord(){
        ArrayList<String> res = new ArrayList<>();
        String dir ="./testingData/testing-feature.csv";
        ArrayList<String> ctx = Util.readFile(dir);
        for(int i=0;i<ctx.size();i++){

            int shouldAbbr=0;
            int numToken = 0;
            int correctAbbr = 0;
            int incorrectAbbr = 0;

            String[] line = ctx.get(i).split(",");
            String identifier = line[1];
            String fullName = line[2];
            String generate = line[5];

            ArrayList<String> temp = TokenSplitUtil.split(identifier);
            ArrayList<String> identifierList = new ArrayList();
            for(String s: temp){
                identifierList.add(s.toLowerCase());
            }
            ArrayList<String> fullNameList = calcAccuracy.str2List(fullName);
            ArrayList<String> generateList = calcAccuracy.str2List(generate);

            numToken = fullNameList.size();

            List<String> sameIdentifier2fullName = new ArrayList<>();
            sameIdentifier2fullName.addAll(identifierList);
            sameIdentifier2fullName.retainAll(fullNameList);

            List<String> c1 = new ArrayList<String>();
            List<String> d1 = new ArrayList<String>();
            c1.addAll(identifierList);
            d1.addAll(sameIdentifier2fullName);
            c1.removeAll(d1);
            List<String> c2 = new ArrayList<String>();
            List<String> d2 = new ArrayList<String>();
            c2.addAll(fullNameList);
            d2.addAll(sameIdentifier2fullName);
            c2.removeAll(d2);
            shouldAbbr= c2.size();

            if(identifier.equalsIgnoreCase(generate.replaceAll(" ",""))){
                correctAbbr = c2.size();
            }else{
                for(String s: generateList){
                    if(sameIdentifier2fullName.contains(s)){

                    }else if(c1.contains(s)){
                        correctAbbr= c2.size();
                    }else if(fullNameList.contains(s)){

                    }else{
                        incorrectAbbr++;
                    }
                }
            }
            String tjRes = ctx.get(i)+","+numToken+","+ shouldAbbr+","+correctAbbr+","+incorrectAbbr;
            String tjRes1 = numToken+","+ shouldAbbr+","+correctAbbr+","+incorrectAbbr;
            res.add(tjRes);
        }
        return res;
    }


    public static void calToken(ArrayList<String> ctx){
        int shouldAbbr=0;
        int successAbbr=0;
        int failAbbr=0;
        for(String line: ctx){
            String[] lines = line.split(",");
            int shouldAbbr1= Integer.parseInt(lines[7]);
            int successAbbr1= Integer.parseInt(lines[8]);
            int failAbbr1= Integer.parseInt(lines[9]);
            shouldAbbr +=shouldAbbr1;
            successAbbr+=successAbbr1;
            failAbbr+=failAbbr1;
        }
        double precision = (successAbbr*1.0)/(successAbbr+failAbbr);
        double recall = (successAbbr*1.0)/(shouldAbbr);
        System.out.println("shouldAbbr: " + shouldAbbr);
        System.out.println("successAbbr: " + successAbbr);
        System.out.println("failAbbr: " + failAbbr);
        System.out.println("precision: " + precision);
        System.out.println("recall: " + recall);
        System.out.println("F1: " + (2*precision*recall)/(precision+recall));
    }

    public static void main(String[] args){
        calculateByIdentifier();
        calToken(calculateByWord());
    }
}
