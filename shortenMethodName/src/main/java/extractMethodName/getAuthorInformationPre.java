package extractMethodName;

import java.io.*;
import java.util.ArrayList;

public class getAuthorInformationPre {

    public static ArrayList<String> readFile(String fileName){
        ArrayList<String> result = new ArrayList<String>();
        File file = new File(fileName);
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            while((tempString = reader.readLine()) != null)
            {
                if(!tempString.equals("")){
                    result.add(tempString);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result;
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

    public static void getFileList(String path){
        ArrayList<String> res = readFile(path);
        for(int i=0;i< res.size();i++){
            String[] each = res.get(i).split(",");
            String p = each[0];
            int line = Integer.parseInt(each[1]);
            int pos = p.indexOf("project\\spring-boot");
            String p1= p.substring(pos+20);
            String fileName = p1.replaceAll("\\\\","##");
            System.out.println(fileName);
            appendFile(p1+","+line+","+fileName+"\n","./Step1/3springboot.txt");
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("--start--");

        String path ="./Step1/2springboot.csv";
        getFileList(path);
    }

}
