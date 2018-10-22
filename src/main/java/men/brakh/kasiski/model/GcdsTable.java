package men.brakh.kasiski.model;

import java.util.*;

public class GcdsTable {
    // Таблица Наибольший общий делитель --- Число его повторений
    private Map<Integer, Integer> gcds = new HashMap<>();

    /**
     * Добавление наибольшего общего делителя вв хеш-таблицу
     * @param gcd НОД
     */
    public void addGcd(int gcd) {
        if(gcds.containsKey(gcd)) {
            gcds.put(gcd, gcds.get(gcd)+1);
        } else {
            gcds.put(gcd, 1);
        }
    }

    /**
     * Презобразование hash-map в отсортированный список
     * @return отспортированный список
     */
    private ArrayList<Map.Entry<Integer, Integer>> getSortedGCDs() {
        ArrayList<Map.Entry<Integer, Integer> > list = new ArrayList(gcds.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
                return b.getValue() - a.getValue();
            }
        });
        return list;
    }

    /**
     * Получение первых N по повторениям НОДов
     * @param n число N
     * @return первые N по повторениям НОДов (первых по вероятностей ключей)
     */
    public int[] getFirstNKeys(int n) {
        ArrayList<Map.Entry<Integer, Integer> > sortedGcd = getSortedGCDs();

        int[] firstNKeys = new int[n];
        for(int i = 0; i < n; i++) {
            firstNKeys[i] = sortedGcd.get(i).getKey();
        }

        return firstNKeys;
    }

    /**
     * Получение наиболее вероятного ключ
     * @return наиболее вероятный ключ
     */
    public int getFirstKey(){
        return getFirstNKeys(1)[0];
    }

    public int size() {
        return gcds.size();
    }

}
