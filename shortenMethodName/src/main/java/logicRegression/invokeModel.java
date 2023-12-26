package logicRegression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class invokeModel {

    /**
     * 将科学计数法的字符串传入
     * @param str
     * @return 返回double类型
     */
    public static double getDoubleNumber(String str){
        double number = 0;
        BigDecimal bd = new BigDecimal(str);
        number =  Double.parseDouble(bd.toString());
        return number;
    }

    /**
     * 得到标签为1的概率值
     * @param str
     * @return double 类型的的概率值
     */
    public static String getPro4Positive(String str){
        // probability:[[9.99999324e-01 6.76393267e-07]]
        int idx1 = str.indexOf("probability:[[");
        String temp = str.substring(idx1+14,str.length()-2);
        System.out.println(temp);
        String positive = temp.split(" ")[1];
        System.out.println(positive);
        return positive;
    }

    public static String getPro4Positive1(String str){
        // probability:[[9.99999324e-01 6.76393267e-07]]
        int idx1 = str.indexOf("probability:[");
        String temp = str.substring(idx1+13,str.length()-1);
        System.out.println(temp);
        String positive = temp.split(" ")[0];
        System.out.println(positive);
        return positive;
    }


    public static double invokeGenModel(String input) throws IOException, InterruptedException{
        String[] arg = new String[]{"python", "./LG/useModel.py", input};
        //参数2：python文件路径；参数3，参数4：python代码的输入参数。
        Process pr = Runtime.getRuntime().exec(arg);

        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line;
        double res = 0;
        while ((line = in.readLine()) != null) {
            System.out.println("probability:"+line);
            res = getDoubleNumber(getPro4Positive("probability:"+line));
        }
        in.close();
        System.out.println("end");
        pr.waitFor();
        return res;
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("start python");
        //process apollo json value
        String modelinputs = "0 -1 22 20 2 4 1 1 0 0";
        double s = invokeGenModel(modelinputs);
        String test = "probability:[[9.99999324e-01 6.76393267e-07]]";
//        getPro4Positive(s);
        System.out.println(s > 0.9);

    }





}
