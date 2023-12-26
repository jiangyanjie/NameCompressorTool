package extractMethodName;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
Parse the java file and extract the method name
 */

public class extractMethodName {
    public static ArrayList<String> paths = new ArrayList<>();
    public static List<Method> filterCodeParserResultList = new ArrayList<>();
    public static List<Method> splitCodeParserResultList = new ArrayList<>();
    public static ArrayList<String> result = new ArrayList<>();

    private static ArrayList<String> parseFiles(String path){
        File files = new File(path);
        if(files.isDirectory()){
            for(File one : files.listFiles()){
                if(one.isDirectory()){
                    parseFiles(one.getPath());
                }else{
                    if((one.getPath().endsWith(".java")) && (!one.getPath().contains("src\\test"))){
                        result.add(one.getAbsolutePath());
                    }
                }
            }
        }
        return result;
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


    public static void main(String[] args) {
        String projectPath = "D:\\Abbreviations\\MethodName\\20220519\\project\\spring-boot";
        ArrayList<String> result = parseFiles(projectPath);
        System.out.println(result.size());
        for(int k=0;k< result.size();k++){
            String each = result.get(k);
            List<Method> method = parser(each);
            for(int i=0;i<method.size();i++){
                appendFile(each+"##"+method.get(i).toStringJ()+"##"+method.get(i).getInterfaceInfo()+"##"+method.get(i).getReturnTypeInfo()+"\n","./Step1/springboot.txt");
//                System.out.println(each+","+method.get(i).toStringJ()+","+method.get(i).getInterfaceInfo()+","+method.get(i).getReturnTypeInfo());
            }
        }
    }

    public static List<Method> parser(String rootPath) {
        List<Method> parserCodeParserList = new ArrayList<>();
        try {
            List<Method> parserResult = JavaParserUtil.parser(new File(rootPath));//projectPath + str
            parserCodeParserList.addAll(parserResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parserCodeParserList;
    }

    public static void filter(List<Method> codeParserResultList) {
        for (Method method : codeParserResultList) {
            if (!method.getMethodName().equalsIgnoreCase(method.getClassName())) {
                if (!method.getMethodName().equals("main") && !method.getMethodName().equals("example") && !method.getMethodName().equals("test")) {
                    filterCodeParserResultList.add(method);
                }
            }
        }
//        System.out.println(filterCodeParserResultList.size());
    }

    public static void split(List<Method> filterCodeParserResultList) {
        for (int i = 0; i < filterCodeParserResultList.size(); i++) {
            Method method = filterCodeParserResultList.get(i);
            String methodName = method.getMethodName();
            String arguments = method.getInterfaceInfo();
            String returnType = method.getReturnTypeInfo();
            method.setMethodName(methodName);
            method.setInterfaceInfo(arguments);
            method.setReturnTypeInfo(returnType);
            splitCodeParserResultList.add(method);
        }
//        System.out.println(splitCodeParserResultList.size());
    }
}
