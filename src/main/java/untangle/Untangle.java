package untangle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.ClassReader;
import untangle.filters.Matcher;
import untangle.sources.ClassSource;
import untangle.visitors.UntangleClassVisitor;

public class Untangle {

    private final Matcher filter;
    private final ClassSource sources;

    public Untangle(Matcher filter, ClassSource sources) {
        this.filter = filter;
        this.sources = sources;
    }

    public List<Usage> findUsages() {
        return findUsages(filter, sources);
    }

    private List<Usage> findUsages(Matcher filter, ClassSource sources) {
        List<Usage> usages = new ArrayList<>();
        for (InputStream stream : sources) {
            try (InputStream classStream = new BufferedInputStream(stream)) {
                ClassReader reader = new ClassReader(classStream);
                UntangleClassVisitor appClassVisitor = new UntangleClassVisitor(filter);
                reader.accept(appClassVisitor, 0);
                usages.addAll(appClassVisitor.getUsages());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return usages;
    }
}
