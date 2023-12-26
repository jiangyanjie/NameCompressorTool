package extractMethodName;

import java.util.ArrayList;

public class Method {
    private String projectName;
    private String packageName;
    private String className;
    private String methodName;
    private String oldMethodName;

    private String returnTypeInfo;
    private String interfaceInfo;
    private String methodBodyInfo;
    private String returnInfo;
    private String structInfo;

    private String AST2019Info;
    private String key;

    private ArrayList<String> parameterTypes;
    private ArrayList<String> methodTokens;
    private ArrayList<String> structTokens;
    private ArrayList<String> returnTokens;

    private int beginPos;

    public Method() {
    }

    public Method(String methodName, String returnTypeInfo, ArrayList<String> parameterTypes, ArrayList<String> methodTokens) {
        this.methodName = methodName;
        this.returnTypeInfo = returnTypeInfo;
        this.parameterTypes = parameterTypes;
        this.methodTokens = methodTokens;
    }

    public Method(String methodName, String returnTypeInfo, ArrayList<String> parameterTypes, ArrayList<String> methodTokens, ArrayList<String> structTokens, ArrayList<String> returnTokens, int beginPos) {
        this.methodName = methodName;
        this.returnTypeInfo = returnTypeInfo;
        this.parameterTypes = parameterTypes;
        this.methodTokens = methodTokens;
        this.structTokens = structTokens;
        this.returnTokens = returnTokens;
        this.beginPos=beginPos;
    }

    public Method(String packageName, String className, String methodName, String returnTypeInfo, ArrayList<String> parameterTypes, ArrayList<String> methodTokens) {
        this.packageName = packageName;
        this.className = className;
        this.methodName = methodName;
        this.returnTypeInfo = returnTypeInfo;
        this.parameterTypes = parameterTypes;
        this.methodTokens = methodTokens;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getAST2019Info() {
        return AST2019Info;
    }

    public void setAST2019Info(String AST2019Info) {
        this.AST2019Info = AST2019Info;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStructInfo() {
        return structInfo;
    }

    public void setStructInfo(String structInfo) {
        this.structInfo = structInfo;
    }


    public String getOldMethodName() {
        return oldMethodName;
    }

    public void setOldMethodName(String oldMethodName) {
        this.oldMethodName = oldMethodName;
    }

    public String getInterfaceInfo() {
        return interfaceInfo;
    }

    public void setInterfaceInfo(String interfaceInfo) {
        this.interfaceInfo = interfaceInfo;
    }

    public String getMethodBodyInfo() {
        return methodBodyInfo;
    }

    public void setMethodBodyInfo(String methodBodyInfo) {
        this.methodBodyInfo = methodBodyInfo;
    }

    public String getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(String returnInfo) {
        this.returnInfo = returnInfo;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getReturnTypeInfo() {
        return returnTypeInfo;
    }

    public void setReturnTypeInfo(String returnTypeInfo) {
        this.returnTypeInfo = returnTypeInfo;
    }

    public ArrayList<String> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(ArrayList<String> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public ArrayList<String> getMethodTokens() {
        return methodTokens;
    }

    public ArrayList<String> getStructTokens() {
        return structTokens;
    }

    public void setStructTokens(ArrayList<String> structTokens) {
        this.structTokens = structTokens;
    }

    public ArrayList<String> getReturnTokens() {
        return returnTokens;
    }

    public void setReturnTokens(ArrayList<String> returnTokens) {
        this.returnTokens = returnTokens;
    }

    public void setMethodTokens(ArrayList<String> methodTokens) {
        this.methodTokens = methodTokens;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return projectName + "," + packageName + "," + className + "," + methodName + "," + oldMethodName + "," + returnTypeInfo + "," +
                interfaceInfo + "," + methodBodyInfo + "," + returnInfo + "," + structInfo;

    }

    public String toStringJ(){
            return beginPos+ "##" +methodName;// + "," + beginPos;
    }

    public String toStringWithoutStruct() {

        return projectName + "," + packageName + "," + className + "," + methodName + "," + oldMethodName + "," + returnTypeInfo + "," +
                interfaceInfo + "," + methodBodyInfo.replace("\\n", "") + "," + returnInfo.replace("\\n", "");

    }

    public static void main(String[] args) {
        System.out.println(new Method());
    }
}
