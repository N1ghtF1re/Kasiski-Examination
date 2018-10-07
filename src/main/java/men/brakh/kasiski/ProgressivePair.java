package men.brakh.kasiski;

public class ProgressivePair {
    private static String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private int index1;
    private int index2;
    private int shiftsCount;
    private int length;

    public int getShiftsCount() {
        return shiftsCount;
    }

    public ProgressivePair(int index1, int index2, int shiftsCount) {
        this.index1 = index1;
        this.index2 = index2;
        this.shiftsCount = shiftsCount;
        length = index2-index1*shiftsCount;
        if (length < 0) {
            length = 1;
        }
    }
    public int getLength() {
        return length % alphabet.length();
    }
}
