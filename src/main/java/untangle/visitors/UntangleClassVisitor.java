package untangle.visitors;

import java.util.List;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import untangle.Usage;
import untangle.filters.Matcher;

public class UntangleClassVisitor extends ClassVisitor {

    private UntangleMethodVisitor methodVisitor;
    private final Matcher matcher;
    private final String fileSource;

    public UntangleClassVisitor(Matcher filter, String fileSource) {
        super(Opcodes.ASM6);
        this.matcher = filter;
        this.fileSource = fileSource;
    }

    public List<Usage> getUsages() {
        return this.methodVisitor.getUsages();
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.methodVisitor = new UntangleMethodVisitor(matcher, fileSource, name);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return methodVisitor;
    }
}
