package men.brakh.kasiski;



import men.brakh.kasiski.kasiskiTests.KasiskiTest;
import men.brakh.kasiski.kasiskiTests.impl.ClassicKasiskiTest;
import men.brakh.kasiski.kasiskiTests.impl.ProgressiveKasiskiTest;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Kasiski {
    private static String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private String text; // Исходный текст
    private int lgrammlength; // Размер l-грамм для поиска



    /**
     * Касиски с алфавитом по-умолчанию (русский)
     * @param text текст
     * @param lgrammlength размер l-грамм
     */
    public Kasiski(String text, int lgrammlength) {
        this.text = text;
        this.lgrammlength = lgrammlength;
    }

    /**
     * Касиски с заданным алфавитом
     * @param text текст
     * @param lgrammlength размер l-грамм
     * @param alphabet алфавит в строке. Пример: "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
     */
    public Kasiski(String text, int lgrammlength, String alphabet) {
        this.text = text;
        this.lgrammlength = lgrammlength;
        this.alphabet = alphabet;
    }



    /**
     * Сдвиг строки на одну позицию (Цезарь с ключем = 1)
     * @param str строка
     * @return "сдвинутая" строка
     */
    public static String shiftStrings(String str) {
        StringBuilder newStr = new StringBuilder(str);
        for(int i = 0; i < newStr.length(); i++) {
            int currCharIndex = alphabet.indexOf(newStr.charAt(i));
            newStr.setCharAt(i,  alphabet.charAt((currCharIndex + 1) % alphabet.length()) );
        }
        return newStr.toString();
    }





    /**
     * Классический тест Касиски
     * @return  Наиболее вероятный ключ
     */
    public int classicTest() {
        KasiskiTest kasiskiTest = new ClassicKasiskiTest(text, lgrammlength);
        return kasiskiTest.test();
    }

    public int progressiveTest() {
        KasiskiTest kasiskiTest = new ProgressiveKasiskiTest(text, lgrammlength, alphabet);
        return kasiskiTest.test();
    }




    public static void main (String[] args) throws IOException {
        String mem = shiftStrings("АБВ");
        String text = new String(Files.readAllBytes(Paths.get("output.txt")));
        Kasiski kasiski = new Kasiski(text, 3);
        kasiski.progressiveTest();

    }
}
