package untangle.sources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    public Iterator<InputStream> iterator() {
        try {
            return new JarIterator(jar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class JarIterator implements Iterator<InputStream> {

        private final JarFile jar;
        private final Enumeration<JarEntry> entries;
        private JarEntry currentEntry;

        public JarIterator(File jar) throws IOException {
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
        public InputStream next() {
            try {
                return this.jar.getInputStream(currentEntry);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
