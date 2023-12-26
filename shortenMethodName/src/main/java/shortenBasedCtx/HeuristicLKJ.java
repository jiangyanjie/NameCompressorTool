package shortenBasedCtx;

import extractMethodName.Util;
import java.util.ArrayList;

public class HeuristicLKJ {

    public static ArrayList<String> getBaseMethod(String project, String testPath){
        String dir = "./testingData/"+project+"_new.csv";
        ArrayList<String> methods = new ArrayList<>();
        ArrayList<String> ctx = Util.readFile(dir);
        for(int i=0;i<ctx.size();i++){
            String[] line = ctx.get(i).split(",");
            String path = line[0];
            String name = line[2];
            String parameters = line[3];
            String returnType = line[4];
            String zjTemp = name + ";"+parameters+";"+returnType;
            if(testPath.equals(path)){
                methods.add(zjTemp);
            }
        }
        return methods;
    }

    public static void getTestData(String project){
        String dir = "./testingData/5"+project+".csv";
        ArrayList<String> ctx = Util.readFile(dir);
        for(int i=0;i<ctx.size();i++){
            String[] line = ctx.get(i).split(",");
            String testPath = line[0];
            String testName = line[2];
            String testFullName = line[3];
            ArrayList<String> methods = getBaseMethod(project,testPath);
            String temp="";
            for(String s:methods){
                if(!s.equals(testName)){
                   temp +=s+";";
                }
            }
            Util.appendFile(testPath+","+testName+","+testFullName+","+temp+"\n","./testingData/"+project+".csv");
        }
    }

    public static void main(String[] args){
        System.out.println("------");
        String[] projects = new String[]{"apollo","batik","dubbo","guava","jadx","springboot","zookeeper"};
        for(int i=0;i< projects.length;i++){
            String project = projects[i];
            getTestData(project);
        }
    }
}
