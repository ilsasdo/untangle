package untangle.filters;

public class PackageNameMatcher implements Matcher {

    private final String targetPackageName;

    public PackageNameMatcher(String targetPackageName) {
        this.targetPackageName = targetPackageName.replace('.', '/') + "/";
    }

    @Override
    public boolean matches(String className) {
        return className.startsWith(targetPackageName);
    }
}
