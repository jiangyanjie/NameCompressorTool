package extractMethodName;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JavaParserUtil {


    public static List<Method> parser(String code) throws FileNotFoundException {
        CompilationUnit compilationUnit = StaticJavaParser.parse(code);
        return parser(compilationUnit);
    }

    public static List<Method> parser(File file) throws FileNotFoundException {
        CompilationUnit compilationUnit = StaticJavaParser.parse(file);
        return parser(compilationUnit);
    }

    public static List<Method> parser(CompilationUnit compilationUnit) throws FileNotFoundException {

        List<String> packageNameList = new ArrayList<>();
        compilationUnit.findAll(PackageDeclaration.class).forEach(packageDeclaration -> {
            packageNameList.add(packageDeclaration.getName().asString());
        });

        String packageName = packageNameList.size() == 0 ? "" : packageNameList.get(0);

        ArrayList<Method> totalMethods = new ArrayList<>();

        compilationUnit.findAll(ClassOrInterfaceDeclaration.class).forEach(classOrInterfaceDeclaration -> {
            if (!classOrInterfaceDeclaration.isInterface()) {
                String className = classOrInterfaceDeclaration.getName().asString();

                if (!className.equals("main") && !className.contains("example") && !className.contains("sample") && !className.contains("template")) {
                    ArrayList<Method> classMethods = new ArrayList<>();

                    classOrInterfaceDeclaration.findAll(MethodDeclaration.class).forEach(methodDeclaration -> {
                        String methodName = methodDeclaration.getName().asString();
                        String returnType = methodDeclaration.getType().asString();
                        int startPos = methodDeclaration.getName().getBegin().get().line;
                        ArrayList<String> parameterTypes = new ArrayList<>();
                        ArrayList<String> methodTokens = new ArrayList<>();
                        ArrayList<String> structTokens = new ArrayList<>();
                        ArrayList<String> returnTokens = new ArrayList<>();


                        NodeList<Parameter> parameters = methodDeclaration.getParameters();
                        for (Parameter parameter : parameters) {
                            parameterTypes.add(parameter.getType().asString());
                            parameterTypes.add(parameter.getName().asString());
                        }
                        Optional<BlockStmt> body = methodDeclaration.getBody();
                        body.ifPresent(blockStmt -> blockStmt.findAll(SimpleName.class).forEach(simpleName -> {
                            methodTokens.add(simpleName.asString());
                        }));

                        List<Node> returnNodeList = new ArrayList<>();
                        body.ifPresent(blockStmt -> returnNodeList.addAll(blockStmt.findAll(ReturnStmt.class)));
                        body.ifPresent(blockStmt -> blockStmt.findAll(ReturnStmt.class).forEach(node -> {
                            if (node.getChildNodes().size() > 0) {
                                Node node1 = node.getChildNodes().get(0);
                                if (node1 instanceof FieldAccessExpr) {
                                    String[] split = node1.getTokenRange().get().toString().split("\\.");
                                    returnTokens.add(split[split.length - 1]);
                                } else if (node1 instanceof MethodCallExpr) {
                                    MethodCallExpr node11 = (MethodCallExpr) node1;
                                    returnTokens.add(node11.getName().getTokenRange().get().toString());
                                } else if (node1 instanceof ConditionalExpr || node1 instanceof BooleanLiteralExpr) {
                                    returnTokens.add("bool");
                                } else if (node1 instanceof SimpleName) {
                                    returnTokens.add(node1.getTokenRange().get().toString());
                                } else if (node1 instanceof ArrayAccessExpr) {
                                    ArrayAccessExpr node11 = (ArrayAccessExpr) node1;
                                    String[] split = node11.getName().getTokenRange().get().toString().split("\\.");
                                    returnTokens.add(split[split.length - 1]);
                                }
//                                else if (node1 instanceof LiteralExpr) {
//                                    LiteralExpr node11 = (LiteralExpr) node1;
//                                    String s = node11.getTokenRange().get().toString();
//                                    returnTokens.add(s.substring(1, s.length() - 1));
//                                }
                                else {
                                    node1.findAll(Node.class).forEach(node11 -> {
                                        if (node11 instanceof SimpleName) {
                                            returnTokens.add(node11.getTokenRange().get().toString());
                                        } else if (node11 instanceof BooleanLiteralExpr) {
                                            returnTokens.add("bool");
                                        }
                                    });
                                }
                            }
                        }));

//                        structTokens.add(AST.parseDot(printer.output(methodDeclaration)).toString());

                        body.ifPresent(blockStmt -> blockStmt.findAll(Statement.class).forEach(node -> {
                            if (node instanceof AssertStmt) {
                                structTokens.add("AssertStmt");
                            } else if (node instanceof BreakStmt) {
                                structTokens.add("BreakStmt");
                            } else if (node instanceof ContinueStmt) {
                                structTokens.add("ContinueStmt");
                            } else if (node instanceof DoStmt) {
                                structTokens.add("DoStmt");
                            } else if (node instanceof EmptyStmt) {
                                structTokens.add("EmptyStmt");
                            } else if (node instanceof ExpressionStmt) {
                                structTokens.add("ExpressionStmt");
                            } else if (node instanceof ForEachStmt) {
                                structTokens.add("ForEachStmt");
                            } else if (node instanceof ForStmt) {
                                structTokens.add("ForStmt");
                            } else if (node instanceof IfStmt) {
                                structTokens.add("IfStmt");
                            } else if (node instanceof ReturnStmt) {
                                structTokens.add("ReturnStmt");
                            } else if (node instanceof SwitchStmt) {
                                structTokens.add("SwitchStmt");
                            } else if (node instanceof ThrowStmt) {
                                structTokens.add("ThrowStmt");
                            } else if (node instanceof TryStmt) {
                                structTokens.add("TryStmt");
                            } else if (node instanceof WhileStmt) {
                                structTokens.add("WhileStmt");
                            }
                        }));

//                        body.ifPresent(blockStmt -> blockStmt.findAll(Statement.class).forEach(node -> {
//                            if (node instanceof WhileStmt) {
//                                structTokens.add("while");
//                            } else if (node instanceof ForStmt) {
//                                structTokens.add("for");
//                            } else if (node instanceof DoStmt) {
//                                structTokens.add("do");
//                            } else if (node instanceof IfStmt) {
//                                structTokens.add("if");
//                            }
//                        }));

                        Method myMethod = new Method(methodName, returnType, parameterTypes, methodTokens, structTokens, returnTokens,startPos);
                        classMethods.add(myMethod);
                    });
                    for (Method classMethod : classMethods) {
                        classMethod.setClassName(className);
                        classMethod.setPackageName(packageName);
                    }
                    totalMethods.addAll(classMethods);
                }

            }
        });

        for (Method method : totalMethods) {

            method.setInterfaceInfo(listToString(method.getParameterTypes()));
            method.setMethodBodyInfo(listToString(method.getMethodTokens()));
            method.setReturnInfo(listToString(method.getReturnTokens()));
            method.setStructInfo(listToString(method.getStructTokens()));
        }

        return totalMethods;

    }

    static String listToString(List<String> strList) {

        if (strList.size() == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (String s : strList) {
                sb.append(s).append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
    }

    public static boolean isCharacter(char c) {
        return (c <= 'z' && c >= 'a') || (c <= 'Z' && c >= 'A');
    }

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("ab.c".split(".").length);

        System.out.println("".split(" ").length);

        String str = "public void addTabletServer(TabletServer tablet) {\n" +
                "\t\tservers.add(tablet);\n" +
                "\t}";
        List<Method> parser = JavaParserUtil.parser("class A{" + str + "}");
        System.out.println(parser.toString());
    }

}
