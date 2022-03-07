package untangle;

import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.fusesource.jansi.AnsiConsole;
import untangle.filters.ClassNameMatcher;
import untangle.filters.Matcher;
import untangle.filters.PackageNameMatcher;
import untangle.sources.ClassSource;
import untangle.sources.MultipleClassSource;

public class Main {

    public static void main(String args[]) throws ParseException {
        AnsiConsole.systemInstall();

        Options options = new Options();
        options.addOption("p", "package", true, "The package to search for");
        options.addOption("c", "class", true, "The class to search for");
        options.addOption("h", "help", false, "Shows this help.");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        if (cmd.hasOption("help")) {
            help(options);
            return;
        }

        try {
            Matcher matcher = getMatcher(cmd);
            ClassSource sources = new MultipleClassSource(cmd.getArgList());
            List<Usage> usages = new Untangle(matcher, sources).findUsages();
            new UsagePrinter(usages).print();
        } catch (IllegalArgumentException e) {
            help(options, e.getMessage() + "\n");
        }
    }

    private static Matcher getMatcher(CommandLine cmd) {
        if (cmd.hasOption("c")) {
            return new ClassNameMatcher(cmd.getOptionValue("c"));
        } else if (cmd.hasOption("p")) {
            return new PackageNameMatcher(cmd.getOptionValue("p"));
        } else {
            throw new IllegalArgumentException("Specify at least one option between '--package' or '--class'");
        }
    }

    private static void help(Options options) {
        help(options, "");
    }

    private static void help(Options options, String message) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(message + "DependencyFinder [options] [java package|class name] [jar files]", options);
    }
}
