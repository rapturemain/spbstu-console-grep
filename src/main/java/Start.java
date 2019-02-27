
public class Start {

    public static void main(String[] args) {
        if (args.length < 1) throw new IllegalArgumentException();
        StringBuilder argsG = new StringBuilder();
        for (int i = 0; i < args.length - 1; i++) {
            argsG.append(args[i]);
            argsG.append(' ');
        }
        argsG.append(args[args.length - 1]);
        try {
            Grep grep = new Grep(argsG.toString());
            for (String it : grep.getStrings()) {
                System.out.println(it);
            }
        } catch (Exception e) {
            System.out.println("Wrong args");
        }
    }
}
