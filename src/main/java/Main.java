import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static HashMap<String, Integer> parseString(String path) throws Exception {
        InputStream in = Main.class.getResourceAsStream(path);
        if (in == null) {
            throw new Exception("resource not found: " + path);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        HashMap<String, Integer> wordMap = new HashMap<>();

        while (reader.ready()) {
            String line = reader.readLine().trim();
            if (line.length() > 0) {
                for (String word : line.toLowerCase().replaceAll("[^a-z' ]", " ").split("\\s+")) {
                    if (!word.matches("[']*")) {
                        Integer index = wordMap.get(word);
                        if (index == null) {
                            index = 0;
                        }
                        wordMap.put(word, ++index);
                    }
                }
            }
        }
        return wordMap;

    }

    public static HashMap<Integer, HashMap<String, Integer>> findMaxElements(HashMap<String, Integer> wordMap) {
        HashMap<String, Integer> initialEntries = new HashMap<>();
        HashMap<Integer, HashMap<String, Integer>> resultMap = new HashMap<>();
        initialEntries.put(" ", 0);
        for (int i = 0; i < 3; i++) {
            resultMap.put(i, initialEntries);
        }
        wordMap.keySet().forEach((k) -> {
            int max1 = resultMap.get(0).entrySet().stream().findFirst().get().getValue();
            int max2 = resultMap.get(1).entrySet().stream().findFirst().get().getValue();
            int max3 = resultMap.get(2).entrySet().stream().findFirst().get().getValue();

            if (wordMap.get(k) > max1) {
                resultMap.put(2, resultMap.get(1));
                resultMap.put(1, resultMap.get(0));
                HashMap<String, Integer> temp = new HashMap<>();
                temp.put(k, wordMap.get(k));
                resultMap.put(0, temp);
            } else if (wordMap.get(k) > max2) {
                resultMap.put(2, resultMap.get(1));
                HashMap<String, Integer> temp = new HashMap<>();
                temp.put(k, wordMap.get(k));
                resultMap.put(1, temp);
            } else if (wordMap.get(k) > max3) {
                HashMap<String, Integer> temp = new HashMap<>();
                temp.put(k, wordMap.get(k));
                resultMap.put(2, temp);
            }
        });
        return resultMap;
    }

    public static void main(String[] args) throws Exception {
        HashMap<String, Integer> wordMap = parseString(args[0]);
        HashMap<Integer, HashMap<String, Integer>> resultMap;
        resultMap = findMaxElements(wordMap);

        ArrayList<String> outputArr = new ArrayList<>();
        for (Map.Entry<Integer, HashMap<String, Integer>> e : resultMap.entrySet()) {
            e.getValue().keySet().forEach((k) -> {
                if (!k.matches("\\s*")) {
                    outputArr.add('"' + k + '"');
                }
            });
        }
        System.out.println(outputArr);
    }
}
