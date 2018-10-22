package men.brakh.kasiski.kasiskiTests.impl;

import men.brakh.kasiski.KasiskiMath;
import men.brakh.kasiski.ProgressivePair;
import men.brakh.kasiski.kasiskiTests.KasiskiTest;

import java.util.ArrayList;
import java.util.List;

public class ProgressiveKasiskiTest extends KasiskiTest {
    private String alphabet;

    /**
     * Повторения прогрессивных пар
     * @see ProgressivePair
     */
    private List<ProgressivePair> progressivePairs = new ArrayList<>();

    public ProgressiveKasiskiTest(String text, int lgrammsLength, String alphabet) {
        super(text, lgrammsLength);
        this.alphabet = alphabet;
    }

    /**
     * Сдвиг строки на одну позицию (Цезарь с ключем = 1)
     * @param str строка
     * @return "сдвинутая" строка
     */
    public String shiftStrings(String str) {
        StringBuilder newStr = new StringBuilder(str);
        for(int i = 0; i < newStr.length(); i++) {
            int currCharIndex = alphabet.indexOf(newStr.charAt(i));
            newStr.setCharAt(i,  alphabet.charAt((currCharIndex + 1) % alphabet.length()) );
        }
        return newStr.toString();
    }

    /**
     * Получение расстояния, повторяющегося больше всего в текущей итерации прогрессивных пар
     * @return расстояние, повторяющееся больше всего в текущей итерации прогрессивных пар
     */
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

    private ArrayList<Integer> getFillLineRepeats() {
        ArrayList<Integer> lineRepeat = new ArrayList();
        for (int i = 0; i < text.length() - lgrammsLength + 1; i++) { // Ищем одинаковые n-звучия (n символов, которые повторяюстя на протяжении текста)
            String subStr1 = text.substring(i, i + lgrammsLength);
            int predI = i;
            for (int j = i + 1; j < text.length() - lgrammsLength + 1; j++) {
                String subStr2 = text.substring(j, j + lgrammsLength);
                String comparedSubStr = shiftStrings(subStr1);
                for(int k = 1; k < alphabet.length(); k++) {
                    if(comparedSubStr.equals(subStr2)) {
                        progressivePairs.add(new ProgressivePair(predI, j, k)); // Добавляем прогрессивные пары
                    }
                    comparedSubStr = shiftStrings(comparedSubStr);
                }
            }
            if(getMaxProgressivePairsRepeated() > 0)
                System.out.println(getMaxProgressivePairsRepeated()); // Из найденных прогрессивных пар определяем максимально повторяющуюся длину
            lineRepeat.add(getMaxProgressivePairsRepeated()); // Эту длину добавляем в отдельный список
            progressivePairs.clear(); // и отчищаем список прогрессивных пар
            /*if(progressBar2 != null) {
                // Если отрисовка из гуишки и там есть прогрессбар - обновляем его
                progressBar2.setValue((int) (((float)i / (float)(text.length() - digramLength + 1))*100));
            }*/
        }
        return lineRepeat;
    }

    private void searchRepeatsGcd(List<Integer> lineRepeats) {
        for (int j = 0; j < lineRepeats.size(); j++) {
            for(int k = j+1; k < lineRepeats.size(); k++){
                int currGcd = KasiskiMath.gcd(lineRepeats.get(j), lineRepeats.get(k));
                if ((currGcd > 1))
                    gcdsTable.addGcd(currGcd);
            }
        }
        progressivePairs.clear();
    }

    @Override
    public int test() {
        List<Integer> lineRepeats = getFillLineRepeats();
        searchRepeatsGcd(lineRepeats);

        if(gcdsTable.size() == 0) {
            return 1;
        }

        return gcdsTable.getFirstKey();
    }
}
