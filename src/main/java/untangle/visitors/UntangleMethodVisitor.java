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

    public UntangleMethodVisitor(Matcher filter) {
        super(Opcodes.ASM6);
        this.filter = filter;
        this.usages = new ArrayList<>();
    }

    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean isInterface) {
        if (filter.matches(owner)) {
            usages.add(new Usage(owner, name, desc, this.line));
        }
    }

    public void visitLineNumber(int line, Label start) {
        this.line = line;
    }

    public List<Usage> getUsages() {
        return this.usages;
    }
}
