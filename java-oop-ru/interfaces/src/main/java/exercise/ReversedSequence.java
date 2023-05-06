package exercise;

// BEGIN
public class ReversedSequence implements CharSequence {
    private char[] chars;

    public ReversedSequence(String s) {
        char[] o = s.toCharArray();
        chars = new char[o.length];

        for (int i = 0; i < o.length; i++) {
            chars[i] = o[o.length - i - 1];
        }
    }

    @Override
    public int length() {
        return chars.length;
    }

    @Override
    public char charAt(int index) {
        return chars[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return toString().subSequence(start, end);
    }

    @Override
    public String toString() {
        return new String(chars);
    }
}
// END
