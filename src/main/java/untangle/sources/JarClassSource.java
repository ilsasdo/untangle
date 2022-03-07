package untangle.sources;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarClassSource implements ClassSource {

    private final File jar;

    public JarClassSource(File jar) {
        this.jar = jar;
    }

    @Override
    public Iterator<Source> iterator() {
        try {
            return new JarIterator(jar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class JarIterator implements Iterator<Source> {

        private final File jarFile;
        private final JarFile jar;
        private final Enumeration<JarEntry> entries;
        private JarEntry currentEntry;

        public JarIterator(File jar) throws IOException {
            this.jarFile = jar;
            this.jar = new JarFile(jar);
            this.entries = this.jar.entries();
        }

        @Override
        public boolean hasNext() {
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    this.currentEntry = entry;
                    return true;
                }
            }
            return false;
        }

        @Override
        public Source next() {
            try {
                return new Source(jarFile, jar.getInputStream(currentEntry));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
