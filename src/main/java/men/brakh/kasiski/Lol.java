package men.brakh.kasiski;


import java.util.ArrayList;
import java.util.List;

public class Lol {

    private String text;
    protected final int ALPHABET_LENGTH = 32;
    public static int digramLength = 3;

    public Lol(String cryptoText) {
        this.text = String.valueOf(removeSymbol(cryptoText));
    }

    private char[] removeSymbol(String cryptoText) {
        ArrayList<Character> array = new ArrayList<Character>();
        for (int i = 0; i < cryptoText.length(); i++) {
            int currentChar = (int) cryptoText.charAt(i);
            if ((currentChar > 1039 && currentChar < 1072)
                    || currentChar == 1025) {
                array.add((char) currentChar);
            }
        }
        char[] charArray = new char[array.size()];

        for (int i = 0; i < array.size(); i++) {
            charArray[i] = array.get(i);
        }

        return charArray;
    }

    public int kasiskiAlhoritm() {
        int[] distance = new int[text.length()];
        List<Integer> repeatCount = new ArrayList<Integer>();

        for (int i = 0; i < text.length() - digramLength; i++) {
            String temp = text.substring(i, i + digramLength);
            for (int j = i + 1; j < text.length() - digramLength; j++) {
                String temp2 = text.substring(j, j + digramLength);
                if (temp.equals(temp2)) {
                    if ((j - i) % ALPHABET_LENGTH == 0)
                        System.out
                                .println(temp + " : " + String.valueOf(j - i));
                    distance[(j - i) / ALPHABET_LENGTH]++;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            int max = 0;
            int maxIndex = 0;
            for (int j = 0; j < distance.length; j++) {
                if (distance[j] > max) {
                    max = distance[j];
                    maxIndex = j;
                }
            }
            repeatCount.add(maxIndex);
            distance[maxIndex] = 0;
        }

        if (repeatCount.isEmpty()) {
            System.out.println("No repetitive sequences");
            System.exit(1);
        }

        int[] nods = new int[text.length()];
        for (int i = 0; i < repeatCount.size(); i++) {
            for (int j = i + 1; j < repeatCount.size(); j++) {
                nods[nodEuclid(repeatCount.get(i), repeatCount.get(j))]++;
            }
        }
        nods[0] = 0;

        int max = 0;
        int maxIndex = 1;
        for (int i = 1; i < nods.length; i++) {
            if (nods[i] > max) {
                max = nods[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private int nodEuclid(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return nodEuclid(b, a % b);
        }
    }

}