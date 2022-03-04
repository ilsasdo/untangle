package untangle.visitors;

import java.util.List;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import untangle.Usage;
import untangle.filters.Matcher;

public class UntangleClassVisitor extends ClassVisitor {

    private final UntangleMethodVisitor methodVisitor;

    public UntangleClassVisitor(Matcher filter) {
        super(Opcodes.ASM6);
        this.methodVisitor = new UntangleMethodVisitor(filter);
    }

    public List<Usage> getUsages() {
        return this.methodVisitor.getUsages();
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return methodVisitor;
    }
}
