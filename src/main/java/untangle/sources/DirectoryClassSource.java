package untangle.sources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Locale;
import java.util.stream.Collectors;

public class DirectoryClassSource implements ClassSource {

    private final File directory;

    public DirectoryClassSource(File directory) {
        if (!directory.exists()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " must exists.");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " must be a directory!");
        }

        this.directory = directory;
    }

    @Override
    public Iterator<Source> iterator() {
        try {
            return new DirectoryIterator(this.directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class DirectoryIterator implements Iterator<Source> {

        private final Iterator<Path> files;

        public DirectoryIterator(File directory) throws IOException {
            this.files = Files.walk(directory.toPath(), FileVisitOption.FOLLOW_LINKS)
                .filter(t -> t.toFile().isFile() && t.toString().toLowerCase(Locale.ROOT).endsWith(".class"))
                .collect(Collectors.toList()).iterator();
        }

        @Override
        public boolean hasNext() {
            return files.hasNext();
        }

        @Override
        public Source next() {
            try {
                return new Source(files.next().toFile());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
