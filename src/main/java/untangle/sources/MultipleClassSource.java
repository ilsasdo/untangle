package untangle.sources;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultipleClassSource implements ClassSource {

    private final List<ClassSource> sources;

    public MultipleClassSource(List<String> files) {
        this.sources = new ArrayList<>();
        for (String file : files) {
            File f = new File(file);
            if (f.isFile() && f.getName().endsWith(".class")) {
                this.sources.add(new ClassFileSource(f));
            } else if (f.isFile() && f.getName().endsWith(".jar")) {
                this.sources.add(new JarClassSource(f));
            } else if (f.isDirectory()) {
                this.sources.add(new DirectoryClassSource(f));
            }
        }
    }

    @Override
    public Iterator<InputStream> iterator() {
        return new MultiIterator(sources);
    }

    private static class MultiIterator implements Iterator<InputStream> {

        private final Iterator<ClassSource> sources;
        private Iterator<InputStream> currentSource;

        public MultiIterator(List<ClassSource> sources) {
            this.sources = sources.iterator();
        }

        @Override
        public boolean hasNext() {
            if (this.currentSource == null || !this.currentSource.hasNext()) {
                if (this.sources.hasNext()) {
                    this.currentSource = this.sources.next().iterator();
                } else {
                    return false;
                }
            }

            return this.currentSource.hasNext();
        }

        @Override
        public InputStream next() {
            return this.currentSource.next();
        }
    }
}
