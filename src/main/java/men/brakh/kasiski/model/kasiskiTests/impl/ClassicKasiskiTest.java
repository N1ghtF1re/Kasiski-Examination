package men.brakh.kasiski.model.kasiskiTests.impl;

import men.brakh.kasiski.model.KasiskiMath;
import men.brakh.kasiski.model.kasiskiTests.KasiskiTest;

import java.util.ArrayList;

public class ClassicKasiskiTest extends KasiskiTest {

    private ArrayList<Integer> repeatCount = new ArrayList<>();

    /**
     * Создание классического теста Касиски
     * @param text текст
     * @param lgrammsLength размер l - грамм
     */
    public ClassicKasiskiTest(String text, int lgrammsLength) {
        super(text, lgrammsLength);
    }

    /**
     * Поиск повторений l-грамм и занесение их индексов в отдельный список
     */
    private void searchRepeatsGcd() {
        for(int i = 0; i < repeatCount.size(); i++) {
            for(int j = i + 1; j < repeatCount.size(); j++) {
                gcdsTable.addGcd(KasiskiMath.gcd(repeatCount.get(i), repeatCount.get(j))); // Находим НОДы всех расстояний
            }
        }
    }

    /**
     * Поиск НОД среди найденных расстояний
     */
    private void searchLGrammRepeats() {
        for (int i = 0; i < text.length() - lgrammsLength + 1; i++) { // Ищем одинаковые n-звучия (n символов, которые повторяюстя на протяжении текста
            String subStr1 = text.substring(i, i + lgrammsLength);
            for (int j = i+1; j < text.length() - lgrammsLength + 1; j++) {
                String subStr2 = text.substring(j, j + lgrammsLength);
                if(subStr1.equals(subStr2)) {
                    repeatCount.add(j-i); // И добавляем расстояние между ними в список
                }
            }
        }
    }

    /**
     * Классический тест Касиски
     * @return наиболее вероятный ключ
     */
    @Override
    public int test() {
        searchLGrammRepeats(); // Поиск повторений l-грамм и занесение их индексов в список
        searchRepeatsGcd(); // Поиск НОДа среди найденных расстояний
        return gcdsTable.getFirstKey(); // Возврат наиболее вероятного ключа
    }
}
