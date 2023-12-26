package extractMethodName;

public class runExtractMethodName {
    public static void main(String[] args) throws Exception {
        //1. extract method name
        extractMethodName.main(args);
        //2. select the method name contains abbreviations
        selectContainsAbbreviationMN.main(args);
        //3. handle path to prepare developer name
        getAuthorInformationPre.main(args);
        //4.  run bash get developer
        //5. combine developer name and source code
        addAuthorInformation.main(args);
    }
}
