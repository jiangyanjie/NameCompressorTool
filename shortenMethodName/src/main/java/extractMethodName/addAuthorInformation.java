package extractMethodName;

import extractMethodName.getAuthorInformationPre;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class addAuthorInformation {

    public static ArrayList<String> getSampleData = getAuthorInformationPre.readFile("./Step1/2springboot.csv");

    public static String getLogInfo(String p1, String line){
        String pathlog = "D:\\Abbreviations\\MethodName\\20220519\\Data\\Step3\\springboot";
        File files = new File(pathlog);
        String author="";
        if(files.isDirectory()){
            for(File one : files.listFiles()){
                String name = one.getName();
                String path4log= one.getPath();
                int pos1 = name.indexOf(".txt");
                String name1 = name.substring(0,pos1);

                if(name1.equals(p1)){
                    File file = new File(path4log);
                    BufferedReader reader;
                    try{
                        reader = new BufferedReader(new FileReader(file));
                        String each_line;
                        while((each_line = reader.readLine()) != null)
                        {
                            if(!each_line.equals("")){
                                int ykao = -1;
                                String tempString = null;
                                Pattern pattern = Pattern.compile("[0-9]+\\)");
                                Matcher matcher = pattern.matcher(each_line);

                                if(matcher.find()){
                                    ykao  = matcher.start();
                                    String ss = matcher.group();
                                    tempString =each_line.substring(0,ykao+ss.length());
                                }
                                if(tempString.endsWith(line+")")){
                                    int pos0 = tempString.indexOf("(");
                                    int pos01 = tempString.indexOf(line+")");

                                    String temp =tempString.substring(pos0,pos01+1);
                                    int newpos01 = -1;
                                    Pattern pattern1 = Pattern.compile("[0-9]");
                                    Matcher matcher1 = pattern1.matcher(temp);

                                    if(matcher1.find()){
                                        newpos01  = matcher1.start();
                                    }
                                    author = temp.substring(1,newpos01);
                                }
                            }
                        }
                        reader.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return author;
    }

    public static void combineAuthorInfo(){
        for(int i=0;i< getSampleData.size();i++){
            String[] eachLine = getSampleData.get(i).split(",");
            String path = eachLine[0].replace("\\", "/");
            System.out.println(path);
            int pos = path.indexOf("project/spring-boot");
            System.out.println(pos);
            String p1= path.substring(pos+20).replaceAll("/","##");
            System.out.println(p1);
            String line = eachLine[1];
            String author = getLogInfo(p1,line);
            System.out.println(author);
            getAuthorInformationPre.appendFile(getSampleData.get(i) + "," + author.replaceAll("[^a-zA-Z]", " ").trim()+"\n","./Step1/4springboot.csv");
        }
    }

    public static void main(String[] args){
        System.out.println("--start--");
        combineAuthorInfo();
    }
}
