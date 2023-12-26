package constructDictionary;

import extractMethodName.Util;

import java.util.ArrayList;

public class calculateIdentifier {

    public static void main(String[] args){
        System.out.println("----------------");
        String dir ="D:\\Abbreviations\\Heuristic1\\updateCondition\\disableFNidentifier30.csv";
        ArrayList<String> ctx = Util.readFile(dir);
        int noAbbr=0;
        int hadAbbr=0;
        int successAbbr=0;
        int failAbbr=0;


        for(int i=0;i<ctx.size();i++){
            String line= ctx.get(i);
            String[] lines = line.split(",");
            String identifier = lines[1];
            String hand = lines[2];
            String jyjTemp= lines[3];
            String autoRes = lines[4];
            String heu=lines[4];

            if(heu.equalsIgnoreCase("null")){
                if(hand.replaceAll(" ","").equalsIgnoreCase(jyjTemp.replaceAll(" ",""))){
                    noAbbr++;
                }else{
                    hadAbbr++;
                    if(identifier.equalsIgnoreCase(jyjTemp.replaceAll(" ",""))){
                        successAbbr++;
                    }else{
                        failAbbr++;
//                    extractMethodName.Util.appendFile(line+"\n","D:\\Abbreviations\\dic\\onlyDic\\fail.csv");
                    }
                }
            }else{
                if(hand.replaceAll(" ","").equalsIgnoreCase(jyjTemp.replaceAll(" ",""))){
                    noAbbr++;
                }else{
                    hadAbbr++;
                    if(identifier.equalsIgnoreCase(jyjTemp.replaceAll(" ",""))){
                        successAbbr++;
                    }else{
                        failAbbr++;
//                    extractMethodName.Util.appendFile(line+"\n","D:\\Abbreviations\\dic\\onlyDic\\fail.csv");
                    }
                }
            }


        }

        System.out.println("noAbbr " + noAbbr);
        System.out.println("hadAbbr " + hadAbbr);
        System.out.println("successAbbr " + successAbbr);
        System.out.println("failAbbr " + failAbbr);

    }


}
