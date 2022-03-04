package untangle.sources;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ClassFileSource implements ClassSource {

    private final List<File> classFile;

    public ClassFileSource(File classFile) {
        this(Collections.singletonList(classFile));
    }

    public ClassFileSource(List<File> classFile) {
        this.classFile = classFile;
    }

    @Override
    public Iterator<InputStream> iterator() {
        return new SingleFileIterator(classFile);
    }

    private static class SingleFileIterator implements Iterator<InputStream> {

        private final Iterator<File> iterator;

        public SingleFileIterator(List<File> classFile) {
            this.iterator = classFile.iterator();
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public InputStream next() {
            try {
                return new BufferedInputStream(new FileInputStream(iterator.next()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
