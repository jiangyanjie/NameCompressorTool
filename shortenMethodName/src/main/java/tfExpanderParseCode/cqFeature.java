package tfExpanderParseCode;

import extractMethodName.TokenSplitUtil;
import extractMethodName.Util;

import java.io.File;
import java.util.ArrayList;

public class cqFeature {

    public static boolean isContains(String[] orginal, String target){
        if(orginal == null || orginal.length == 0){
            return false;
        }
        for(String str : orginal){
            if(str.equalsIgnoreCase(target)){
                return true;
            }
        }
        return false;
    }

    public static int getIndex(String[] orginal, String target){
        for(int i=0;i<orginal.length;i++){
            if(orginal[i].equalsIgnoreCase(target)){
                return i;
            }
        }
        return -1;
    }

    public static void extract(){
        String path = "./Feature";
        File file = new File(path);
        File[] files = file.listFiles();
        for(File f: files){
            String dirPath= f.getAbsolutePath();
            String dirName = f.getName();

            ArrayList<String> ctx = Util.readFile(dirPath);
            for(int i=0;i<ctx.size();i++){
                String[] line = ctx.get(i).split(",");
                String abbr= line[1];
                String identifier = line[2];
                String expansion = line[3];
                String label = line[4];

                int len4Identifier= identifier.trim().length();
                int tok4Identifier=0;
                int containAbbr = 0;
                int hadAbbr=0;
                int curLen=0;
                int curtoken=0;
                int lenAbbr=0;
                int tokenAbbr=0;
                String curToken="";

                if(Integer.parseInt(label) ==1){
                    ArrayList<String> token = TokenSplitUtil.split(identifier);
                    tok4Identifier=token.size();
                    containAbbr=1;
                    for(String t:token){
                        if(t.equalsIgnoreCase(abbr)){
                            String temp= extractNecessaryFeature.handleExp(expansion);
                            curLen= temp.replace(" ","").length();
                            curtoken=temp.trim().split(" ").length;
                            lenAbbr = t.length();
                            tokenAbbr=TokenSplitUtil.split(t).size();
                            curToken=t;
                            String result = abbr+","+ identifier +","+ temp+","+label+","+curToken+","+len4Identifier+","+tok4Identifier+","+containAbbr+","+hadAbbr+","+curLen+","+
                                    curtoken+","+lenAbbr+","+tokenAbbr;
                            Util.appendFile(result+"\n","./Feature/2"+dirName);
                            hadAbbr++;
                        }else{
                            curLen=t.length();
                            curtoken =1;
                            lenAbbr=0;
                            tokenAbbr=0;
                            curToken=t;
                            String result = curToken+","+ identifier +","+ curToken+","+label+","+curToken+","+len4Identifier+","+tok4Identifier+","+containAbbr+","+hadAbbr+","+curLen+","+
                                    curtoken+","+lenAbbr+","+tokenAbbr;
                            Util.appendFile(result+"\n","./Feature/2"+dirName);
                        }
                    }
                }else{
                    String[] abbrs= abbr.trim().split(" ");
                    String[] expans =expansion.substring(1,expansion.length()).split("\\*");
                    if(abbrs.length != expans.length){

                    }else{
                       ArrayList<String> token = TokenSplitUtil.split(identifier);
                       tok4Identifier = token.size();
                       containAbbr = abbrs.length;

                       for(String t: token){
                           if(isContains(abbrs,t)){
                               int index = getIndex(abbrs,t);
                               String temp=extractNecessaryFeature.handleExp(expans[index]);
                               curLen =temp.replace(" ","").length();
                               curtoken = temp.trim().split(" ").length;
                               lenAbbr = t.length();
                               tokenAbbr = TokenSplitUtil.split(t).size();
                               curToken=t;

                               String result = abbr+","+ identifier +","+ temp+","+label+","+curToken+","+len4Identifier+","+tok4Identifier+","+containAbbr+","+hadAbbr+","+curLen+","+
                                       curtoken+","+lenAbbr+","+tokenAbbr;
                               Util.appendFile(result+"\n","./Feature/2"+dirName);
                               hadAbbr++;
                           }else{
                               curLen=t.length();
                               curtoken =1;
                               lenAbbr=0;
                               tokenAbbr=0;
                               curToken=t;
                               String result = curToken+","+ identifier +","+ curToken+","+label+","+curToken+","+len4Identifier+","+tok4Identifier+","+containAbbr+","+hadAbbr+","+curLen+","+
                                       curtoken+","+lenAbbr+","+tokenAbbr;
                               Util.appendFile(result+"\n","./Feature/2"+dirName);
                           }
                       }
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        extract();
    }
}
