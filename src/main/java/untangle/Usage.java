package untangle;

public class Usage {

    private final String className;
    private final String methodName;
    private final String methodDesc;
    private final int line;

    public Usage(String className, String methodName, String methodDescription, int line) {
        this.className = className;
        this.methodName = methodName;
        this.methodDesc = methodDescription;
        this.line = line;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMethodDesc() {
        return methodDesc;
    }

    public int getLine() {
        return line;
    }

    public String toString() {
        return getClassName() + " " + getMethodName() + " " + getMethodDesc();
    }
}
