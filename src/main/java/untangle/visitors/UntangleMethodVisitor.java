package untangle.visitors;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import untangle.Usage;
import untangle.filters.Matcher;

public class UntangleMethodVisitor extends MethodVisitor {

    private final Matcher filter;
    private final List<Usage> usages;
    private int line;
    private final String classSource;
    private final String fileSource;

    public UntangleMethodVisitor(Matcher filter, String fileSource, String classSource) {
        super(Opcodes.ASM6);
        this.fileSource = fileSource;
        this.filter = filter;
        this.classSource = classSource;
        this.usages = new ArrayList<>();
    }

    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean isInterface) {
        if (filter.matches(owner)) {
            usages.add(new Usage(fileSource, classSource, owner, name, desc, line));
        }
    }

    public void visitLineNumber(int line, Label start) {
        this.line = line;
    }

    public List<Usage> getUsages() {
        return this.usages;
    }
}
