package untangle;

public class Usage {

    private final String classSource;
    private final String file;
    private final String className;
    private final String methodName;
    private final String methodDesc;
    private final int line;

    public Usage(String file,
        String classSource,
        String className,
        String methodName,
        String methodDescription,
        int line) {
        this.file = file;
        this.classSource = classSource;
        this.className = className;
        this.methodName = methodName;
        this.methodDesc = methodDescription;
        this.line = line;
    }

    public String getFile() {
        return this.file;
    }

    public String getClassSource() {
        return classSource.replace('/', '.');
    }

    public String getClassName() {
        return className.replace('/', '.');
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
}
