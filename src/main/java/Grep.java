import java.io.*;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.*;

    public class Grep {

        Grep(String args) throws IllegalArgumentException {
            if (!Pattern.compile("^((-v )|(-i )|(-r ))*(\\S+ )+[.:\\w\\\\]+$").matcher(args).find()) {
                throw new IllegalArgumentException("Wrong command, not matches to \"-flags -words -file\"");
            }
            String[] parts = args.split(" ");

            // Парсинг флагов
            String[] flagsList = {"-v", "-i", "-r"};
            int flagsCount = 0;
            for (int i = 0; i < flagsList.length; i++) {
                for (int j = 0; (j < flagsList.length && j < parts.length); j++) {
                    if (flagsList[i].equals(parts[j])) {
                        flags.set(i, true);
                        flagsCount += 1;
                        break;
                    }
                    flags.set(i, false);
                }
            }

            // Парсинг слов
            words = new String[parts.length - flagsCount - 1];
            for (int i = flagsCount; i < parts.length - 1; i++) {
                if (flags.get(1) && !flags.get(2)) {
                    words[i - flagsCount] = parts[i].toLowerCase();
                } else {
                    words[i - flagsCount] = parts[i];
                }
            }

            // Парсинг фийлов
            String[] inputFiles = new String[1];
            inputFiles[0] = parts[parts.length - 1];
            for (int i = 0; i < inputFiles.length; i++) {
                files[i] = new File(inputFiles[i]);
                if (!files[i].exists() || !files[i].isFile()) {
                    throw new IllegalArgumentException("File path is not valid or not point to a file");
                }
            }
        }

        private File[] files = new File[1];
        private BitSet flags = new BitSet();
        private String[] words;

        public List<String> getStrings() throws IOException {
            List<String> strings = new LinkedList<>();
            for (File file : files) {
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                boolean regex = flags.get(2);
                if (regex) strings.addAll(filterRegex(br));
                else strings.addAll(filterNotRegex(br));
            }
            return strings;
        }

        private List<String> filterRegex(BufferedReader br) throws IOException {
            String line;
            List<String> strings = new LinkedList<>();
            while ((line = br.readLine()) != null) {
                boolean toVice = flags.get(0);
                boolean ignore = flags.get(1);
                Integer counter = 0;
                for (String word : words) {
                    if (ignore
                            && (Pattern.compile(word, Pattern.CASE_INSENSITIVE).matcher(line).find())) {
                        counter += 1;
                    } else if (Pattern.compile(word).matcher(line).find()) {
                        counter += 1;
                    }
                }
                if (toVice && counter.equals(0)) strings.add(line);
                if (!toVice && counter > 0) strings.add(line);
            }
            return strings;
        }

        private List<String> filterNotRegex(BufferedReader br) throws IOException {
            String line;
            List<String> strings = new LinkedList<>();
            while ((line = br.readLine()) != null) {
                boolean toVice = flags.get(0);
                boolean ignore = flags.get(1);
                Integer counter = 0;
                for (String word : words) {
                    String[] parts = line.split("\\s");
                    for (String part : parts) {
                        if (ignore && (part.toLowerCase().equals(word.toLowerCase()))) {
                            counter++;
                        } else if (part.equals(word)) {
                            counter++;
                        }
                    }
                }
                if (toVice && counter.equals(0)) strings.add(line);
                if (!toVice && counter > 0) strings.add(line);
            }
            return strings;
        }

        public static String help() {
            return "-flags(-r - regex, -i - ignore case, -v - return lines that NOT matches) -words -file";
        }


    }
