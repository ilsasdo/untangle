package untangle;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import untangle.filters.ClassNameMatcher;
import untangle.filters.PackageNameMatcher;
import untangle.sources.ClassFileSource;
import untangle.sources.JarClassSource;
import untangle.sources.MultipleClassSource;

public class UntangleTest {

    @Test
    public void find_class_usage_in_class() {
        Untangle untangle = new Untangle(new ClassNameMatcher("test.Person"),
            new ClassFileSource(new File("build/classes/java/test/test/PersonPrinter.class")));
        List<Usage> usages = untangle.findUsages();
        Assert.assertEquals("find two usages of Person class", 2, usages.size());
        Assert.assertEquals(12, usages.get(0).getLine());
        Assert.assertEquals(12, usages.get(1).getLine());
    }

    @Test
    public void find_constructor_usage_in_class() {
        Untangle untangle = new Untangle(new ClassNameMatcher("test.Person"),
            new ClassFileSource(new File("build/classes/java/test/test/PersonPrinter2.class")));
        List<Usage> usages = untangle.findUsages();
        Assert.assertEquals("find constructor usage of Person class", 1, usages.size());
        Assert.assertEquals(8, usages.get(0).getLine());
    }

    @Test
    public void find_package_usage_in_class() {
        Untangle untangle = new Untangle(new PackageNameMatcher("test"),
            new ClassFileSource(new File("build/classes/java/test/test/PersonPrinter2.class")));
        List<Usage> usages = untangle.findUsages();
        Assert.assertEquals("find usages of Person class", 1, usages.size());
        Assert.assertEquals(8, usages.get(0).getLine());
    }

    @Test
    public void find_package_usage_in_classes() {
        Untangle untangle = new Untangle(new PackageNameMatcher("test"),
            new ClassFileSource(Arrays.asList(
                new File("build/classes/java/test/test/PersonPrinter.class"),
                new File("build/classes/java/test/test/PersonPrinter2.class"))));
        List<Usage> usages = untangle.findUsages();
        Assert.assertEquals("find usages of Person class", 3, usages.size());
    }

    @Test
    public void find_package_usage_in_jar() {
        File file = findTestLibJar();
        Untangle untangle = new Untangle(new ClassNameMatcher("untangletestlib.Address"),
            new JarClassSource(file));
        List<Usage> usages = untangle.findUsages();
        Assert.assertEquals("find usages of Person class", 2, usages.size());
    }

    @Test
    public void multiple_sources() {
        File file = findTestLibJar();
        Untangle untangle = new Untangle(new ClassNameMatcher("untangletestlib.Address"),
            new MultipleClassSource(Arrays.asList(
                file.getAbsolutePath(),
                "build/classes/java/test/test/PersonPrinter.class",
                "build/classes/java/test")));
        List<Usage> usages = untangle.findUsages();
        Assert.assertEquals("find usages of Person class", 2, usages.size());
    }

    private File findTestLibJar() {
        return new File("testlib/build/libs/testlib.jar");
    }
}
