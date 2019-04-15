package infos;

import java.util.ArrayList;
import java.util.List;

public class MethodInfo {
    private String affiliatedFileId;
    private String methodId;
    private String methodName;
    private String className;

    private String accessRight; // public/private/protected/default
    private String returnType;
    private List<String> paramTypes;
    private List<String> exceptionTypes;

    private List<String> rawLineCodes;  // raw codes in file
    private List<String> tokenSequence;
    private int startIndexInFile;
    private int endIndexInFile; // tokens = [startIndexInFile,endIndexInFile]
    private int startLine;
    private int endLine;

    private String docComments;
    private List<String> inMethodComments;

    public MethodInfo(String affiliatedFileId, String methodId, String methodName, String className, String accessRight,
                      String returnType, List<String> paramTypes, List<String> exceptionTypes, List<String> tokenSequence,
                      int startIndexInFile, int endIndexInFile, int startLine, int endLine,
                      String docComments, List<String> inMethodComments) {
        this.affiliatedFileId = affiliatedFileId;
        this.methodId = methodId;
        this.methodName = methodName;
        this.className = className;
        this.accessRight = accessRight;
        this.returnType = returnType;
        this.paramTypes = paramTypes;
        this.exceptionTypes = exceptionTypes;
        this.tokenSequence = tokenSequence;

        this.startIndexInFile = startIndexInFile;
        this.endIndexInFile = endIndexInFile;
        this.startLine = startLine;
        this.endLine = endLine;

        this.docComments = docComments;
        this.inMethodComments = inMethodComments;
    }

    public MethodInfo(String affiliatedFileId, String methodId, String methodName, String className, String accessRight,
                      String returnType, List<String> paramTypes, List<String> exceptionTypes,
                      List<String> rawLineCodes, List<String> tokenSequence,
                      int startIndexInFile, int endIndexInFile, int startLine, int endLine,
                      String docComments, List<String> inMethodComments) {
        this.affiliatedFileId = affiliatedFileId;
        this.methodId = methodId;
        this.methodName = methodName;
        this.className = className;
        this.accessRight = accessRight;
        this.returnType = returnType;
        this.paramTypes = paramTypes;
        this.exceptionTypes = exceptionTypes;
        this.rawLineCodes = rawLineCodes;
        this.tokenSequence = tokenSequence;

        this.startIndexInFile = startIndexInFile;
        this.endIndexInFile = endIndexInFile;
        this.startLine = startLine;
        this.endLine = endLine;

        this.docComments = docComments;
        this.inMethodComments = inMethodComments;
    }

    public MethodInfo(String affiliatedFileId, String methodId, String accessRight, List<String> paramTypes) {
        this.affiliatedFileId = affiliatedFileId;
        this.methodId = methodId;
        this.accessRight = accessRight;
        this.paramTypes = paramTypes;

        this.paramTypes = new ArrayList<>();
        this.exceptionTypes = new ArrayList<>();
        this.tokenSequence = new ArrayList<>();
        this.inMethodComments = new ArrayList<>();
    }

    public String getAffiliatedFileId() {
        return affiliatedFileId;
    }

    public String getMethodId() {
        return methodId;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getClassName() {
        return className;
    }

    public String getAccessRight() {
        return accessRight;
    }

    public String getReturnType() {
        return returnType;
    }

    public List<String> getParamTypes() {
        return paramTypes;
    }

    public List<String> getExceptionTypes() {
        return exceptionTypes;
    }

    public List<String> getTokenSequence() {
        return tokenSequence;
    }

    public int getStartIndexInFile() {
        return startIndexInFile;
    }

    public int getEndIndexInFile() {
        return endIndexInFile;
    }

    public String getDocComments() {
        return docComments;
    }

    public List<String> getInMethodComments() {
        return inMethodComments;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public List<String> getRawLineCodes() {
        return rawLineCodes;
    }

    public void setRawLineCodes(List<String> rawLineCodes) {
        this.rawLineCodes = rawLineCodes;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ClassName:" + className + "\n");
        builder.append("AccessRight:" + accessRight + "\n");
        builder.append("ReturnType:" + returnType + "\n");
        builder.append("MethodName:" + methodName + "\n");
        builder.append("ParamTypes:" + String.join(",", paramTypes) + "\n");
        builder.append("ExceptionTypes:" + String.join(",", exceptionTypes) + "\n");
        builder.append("DocComment:" + docComments + "\n");
        builder.append("InMethodComments:" + String.join("\n", inMethodComments) + "\n");

        return builder.toString();
    }
}
