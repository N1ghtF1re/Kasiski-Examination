package men.brakh.kasiski;



import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Kasiski {
    private static String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private String text;
    private int digramLength;

    private ArrayList<ProgressivePair> progressivePairs = new ArrayList<>();

    private ArrayList<Integer> repeatCount = new ArrayList<Integer>();
    private HashMap<Integer, Integer> gcds = new HashMap<Integer, Integer>();

    void addGCD(int gcd) {
        if(gcds.containsKey(gcd)) {
            gcds.put(gcd, gcds.get(gcd)+1);
        } else {
            gcds.put(gcd, 1);
        }
    }


    public Kasiski(String text, int digramLength) {
        this.text = text;
        this.digramLength = digramLength;
    }


    public static int gcd(int a,int b) {
        while (b !=0) {
            int tmp = a%b;
            a = b;
            b = tmp;
        }
        return a;
    }

    public static int min(int a, int b) {
        if (a < b) {
            return a;
        }
        return b;
    }

    public ArrayList getSortedGCDs() {
        ArrayList list = new ArrayList(gcds.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
                return b.getValue() - a.getValue();
            }
        });
        return list;
    }

    public void test() {
        for (int i = 0; i < text.length() - digramLength + 1; i++) { // Ищем одинаковые n-звучия (n символов, которые повторяюстя на протяжении текста
            String subStr1 = text.substring(i, i + digramLength);
            for (int j = i+1; j < text.length() - digramLength + 1; j++) {
                String subStr2 = text.substring(j, j + digramLength);
                if(subStr1.equals(subStr2)) {
                    repeatCount.add(j-i); // И добавляем расстояние между ними в список
                }
            }
        }

        for(int i = 0; i < repeatCount.size(); i++) {
            for(int j = i + 1; j < repeatCount.size(); j++) {
                addGCD(gcd(repeatCount.get(i), repeatCount.get(j))); // Находим НОДы всех расстояний
            }
        }
        ArrayList sortedGcd = getSortedGCDs();
        Map.Entry<Integer, Integer> max = (Map.Entry<Integer, Integer>) sortedGcd.get(0);
        System.out.println("Наиболее вероятный ключ: " + max.getKey());
    }

    public static String shiftStrings(String str) {
        StringBuilder newStr = new StringBuilder(str);
        for(int i = 0; i < newStr.length(); i++) {
            int currCharIndex = alphabet.indexOf(newStr.charAt(i));
            newStr.setCharAt(i,  alphabet.charAt((currCharIndex + 1) % alphabet.length()) );
        }
        return newStr.toString();
    }


    private int getMaxProgressivePairsRepeated() {
        int maxCount = -1;
        int maxNumber = 0;
        for(int i = 0; i < progressivePairs.size(); i++) {
            int count = 0;
            for(int j = 0; j < progressivePairs.size(); j++) {
                if(progressivePairs.get(i).getLength() == progressivePairs.get(j).getLength()) {
                    count++;
                }
            }
            if((count > maxCount) && (progressivePairs.get(i).getLength() > 1) ) {
                maxCount = count;
                maxNumber = progressivePairs.get(i).getLength();
            }
        }
        return maxNumber;
    }

    public int progressiveTest(JProgressBar progressBar2) {
        ArrayList<Integer> lineRepeat = new ArrayList();
        for (int i = 0; i < text.length() - digramLength + 1; i++) { // Ищем одинаковые n-звучия (n символов, которые повторяюстя на протяжении текста
            String subStr1 = text.substring(i, i + digramLength);
            int predI = i;
            for (int j = i + 1; j < text.length() - digramLength + 1; j++) {
                String subStr2 = text.substring(j, j + digramLength);
                String comparedSubStr = shiftStrings(subStr1);
                for(int k = 1; k < alphabet.length(); k++) {
                    if(comparedSubStr.equals(subStr2)) {
                        progressivePairs.add(new ProgressivePair(predI, j, k));
                    }
                    comparedSubStr = shiftStrings(comparedSubStr);
                }
            }
            if(getMaxProgressivePairsRepeated() > 0)
                System.out.println(getMaxProgressivePairsRepeated());
            lineRepeat.add(getMaxProgressivePairsRepeated());
            progressivePairs.clear();
            if(progressBar2 != null) {
                progressBar2.setValue((int) (((float)i / (float)(text.length() - digramLength + 1))*100));
            }
        }
        for (int j = 0; j < lineRepeat.size(); j++) {
            //System.out.println(progressivePairs.get(j).getLength());
            for(int k = j+1; k < lineRepeat.size(); k++){
                int currGcd = gcd(lineRepeat.get(j), lineRepeat.get(k));
                if ((currGcd > 1))
                    addGCD(currGcd);
            }
        }
        progressivePairs.clear();

        ArrayList sortedGcd = getSortedGCDs();
        int n = sortedGcd.size() >= 5 ? 5 : sortedGcd.size();
        for(int i = 0; i < n; i++) {
            Map.Entry<Integer, Integer> max = (Map.Entry<Integer, Integer>) sortedGcd.get(i);
            System.out.println("Наиболее вероятный ключ: " + max.getKey());
        }
        if(sortedGcd.size() == 0) {
            return 1;
        }
        Map.Entry<Integer, Integer> max = (Map.Entry<Integer, Integer>) sortedGcd.get(0);
        return max.getKey();
    }

    public static void main (String[] args) throws IOException {
        String mem = shiftStrings("АБВ");
        String text = new String(Files.readAllBytes(Paths.get("output.txt")));
        Kasiski kasiski = new Kasiski(text, 3);
        kasiski.progressiveTest(null);

    }
}
