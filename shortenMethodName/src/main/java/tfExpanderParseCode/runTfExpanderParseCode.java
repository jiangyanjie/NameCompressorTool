package tfExpanderParseCode;

public class runTfExpanderParseCode {
    public static void main(String[] args){
        //1. get method name
        getAllMethodName.main(args);
        //2. extract abbr identifier expansion
        ExtractNecessaryInfo.main(args);
        //3. calculate p1 and p2
        calculateP1All.main(args);
        calculateP2All.main(args);
    }
}
