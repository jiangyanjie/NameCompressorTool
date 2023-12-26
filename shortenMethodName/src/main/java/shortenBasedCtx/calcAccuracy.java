package shortenBasedCtx;

import extractMethodName.TokenSplitUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class calcAccuracy {

    public static ArrayList<String> str2List(String str){
        ArrayList<String> res = new ArrayList<>();
        String[] strs = str.split(" ");
        for(int i=0;i<strs.length;i++){
            res.add(strs[i].toLowerCase());
        }
        return res;
    }

    public static void Abbr(String identifier, String handLabel, String output){

        int noAbbr =0;
        //首先判断有没有缩写词
        String[] token4hand = handLabel.split(" ");
        String tempOutput = output.replace(" ","");

        if(handLabel.equalsIgnoreCase(output)){
            noAbbr += token4hand.length;
        }else{
            // 有缩写存在
            List<String> list2hand = str2List(handLabel);
            List<String> list2output = str2List(output);
            List<String> list2identifier = TokenSplitUtil.split(identifier);

            //D--F之间相同的元素，不同的元素
            ArrayList<String> sameDF = new ArrayList<>();
            sameDF.addAll(list2hand);
            sameDF.retainAll(list2output);

            List<String> c1 = new ArrayList<String>();
            List<String> d1 = new ArrayList<String>();
            c1.addAll(list2hand);
            d1.addAll(sameDF);
            c1.removeAll(d1);
            System.out.println("left-hand："+c1.toString());

            List<String> c2 = new ArrayList<String>();
            List<String> d2 = new ArrayList<String>();
            c2.addAll(list2output);
            d2.addAll(sameDF);
            c2.removeAll(d2);
            System.out.println("left-output："+c2.toString());

            //C---D之间相同的元素，不同的元素
            ArrayList<String> sameCD = new ArrayList<>();
            sameCD.addAll(list2hand);
            sameCD.retainAll(list2identifier);

            List<String> c3 = new ArrayList<String>();
            List<String> d3 = new ArrayList<String>();
            c3.addAll(list2hand);
            d3.addAll(sameCD);
            c3.removeAll(d3);
            System.out.println("left-hand："+c3.toString());

            List<String> c4 = new ArrayList<String>();
            List<String> d4 = new ArrayList<String>();
            c4.addAll(list2identifier);
            d4.addAll(sameCD);
            c4.removeAll(d4);
            System.out.println("left-output："+c4.toString());









        }




    }

    public static void calculate(){
        String path = "D:\\Abbreviations\\dic\\onlyDic";
        File file = new File(path);
        File[] files = file.listFiles();
        for(File f : files){
            String dirPath = f.getAbsolutePath();
            ArrayList<String> fileCtx = Util.readFile(dirPath);

            int noExp=0;
            int exp=0;
            int successAbbr=0;

            for(String line : fileCtx){
                String[] lines = line.split(",");
                String identifier = lines[2];
                String handlabel = lines[3];
                String output = lines[5];

                String[] tokens = handlabel.split(" ");
                String tempOutput = output.replace(" ","");




                if(handlabel.equalsIgnoreCase(output)){
                   noExp++;
                   Util.appendFile(line+","+0+"\n","D:\\Abbreviations\\dic\\onlyDic\\test.csv");
                }else{
                    exp++;
                    String temp=output.replace(" ","");
                    if(identifier.equalsIgnoreCase(temp)){
                        successAbbr++;
                        Util.appendFile(line+","+1+"\n","D:\\Abbreviations\\dic\\onlyDic\\test.csv");
                    }else{
                        Util.appendFile(line+","+2+"\n","D:\\Abbreviations\\dic\\onlyDic\\test.csv");
                    }

                }
            }
            System.out.println("no expansion: " + noExp);
            System.out.println("had expansion: " + exp);
            System.out.println("success expansion: " + successAbbr);

            System.out.println("--------------------------");


        }


    }

    public static void main(String[] args){

//        calculate();
        String identifier="isJson";
        String handLabel ="is Java Script Object Notation";
        String output = "is json";
        Abbr(identifier,handLabel,output);



    }


}
