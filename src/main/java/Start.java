import java.util.List;

public class Start {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Wrong args entered");
            System.out.println(Grep.help());
            return;
        }
        if (args.length == 1 && args[0].equals("help")) {
            System.out.println(Grep.help());
            return;
        }
        StringBuilder argsG = new StringBuilder();
        for (int i = 0; i < args.length - 1; i++) {
            argsG.append(args[i]);
            argsG.append(' ');
        }
        argsG.append(args[args.length - 1]);
        try {
            Grep grep = new Grep(argsG.toString());
            List<String> strings = grep.getStrings();
            if (strings.isEmpty()) System.out.println("/// No matches found ///");
            else System.out.println("Found " + strings.size() + " matches:");
            for (String it : strings) {
                System.out.println(it);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
