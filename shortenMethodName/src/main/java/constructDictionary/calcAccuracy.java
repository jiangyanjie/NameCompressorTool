package constructDictionary;

import java.util.ArrayList;
public class calcAccuracy {

    public static ArrayList<String> str2List(String str){
        ArrayList<String> res = new ArrayList<>();
        String[] strs = str.split(" ");
        for(int i=0;i<strs.length;i++){
            res.add(strs[i].toLowerCase());
        }
        return res;
    }
}
