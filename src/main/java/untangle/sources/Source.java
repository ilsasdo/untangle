package untangle.sources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Source {

    private final String name;
    private final InputStream inputStream;

    public Source(File file) throws FileNotFoundException {
        this(file, new FileInputStream(file));
    }

    public Source(File file, InputStream inputStream) throws FileNotFoundException {
        this(file.toPath().toString(), inputStream);
    }

    public Source(String name, InputStream inputStream) {
        this.name = name;
        this.inputStream = inputStream;
    }

    public String getName() {
        return name;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
