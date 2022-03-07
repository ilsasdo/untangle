package untangle;

import static org.fusesource.jansi.AnsiRenderer.render;

import java.util.List;
import org.fusesource.jansi.AnsiConsole;

public class UsagePrinter {

    private final List<Usage> usages;

    public UsagePrinter(List<Usage> usages) {
        this.usages = usages;
    }

    public void print() {
        // print header:
        AnsiConsole.out().println(render(String.format("@|yellow %30s | %30s | %5s | %15s |@",
            "Usage Found", "Source", "Line", "File")));

        for (Usage usage : usages) {
            AnsiConsole.out().println(render(
                String.format("%30s | %30s | %5d | %15s",
                    usage.getClassName(), usage.getClassSource(), usage.getLine(), usage.getFile())));
        }
    }
}
