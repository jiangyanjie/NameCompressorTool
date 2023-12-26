package constructDictionary;
public class constructDic {
    public static String handleExp(String expan){
        int index = expan.indexOf("[+");
        if(index != -1){
            String res = expan.substring(0,index);
            return res;
        }else{
            return expan;
        }
    }

    public static String handleExpformat(String expan){
        int index = expan.indexOf("#");
        if(index != -1){
            String res = expan.substring(0,index);
            return res;
        }else{
            return expan;
        }
    }
}
