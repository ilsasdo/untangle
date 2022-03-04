package untangle.filters;

public class ClassNameMatcher implements Matcher {

    private final String targetClassName;

    public ClassNameMatcher(String targetClassName) {
        this.targetClassName = targetClassName.replace('.','/');
    }

    @Override
    public boolean matches(String className) {
        return this.targetClassName.equals(className);
    }
}
