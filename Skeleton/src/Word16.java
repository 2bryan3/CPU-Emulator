public class Word16 {
    private Bit[] bits;

    public Bit[] getBits() {
        return bits;
    }

    public void setBits(Bit[] bits) {
        this.bits = bits;
    }

    public Word16() {
        bits = new Bit[16];
        for (int i = 0; i < 16; i++) {
            bits[i] = new Bit(false);
        }
    }

    public Word16(Bit[] in) {
        //bits = in;
        bits = new Bit[16];
        for (int i = 0; i < 16; i++) {
            if(in[i].getValue() == Bit.boolValues.TRUE){
                bits[i] = new Bit(true);
            } else {
                bits[i] = new Bit(false);
            }
        }
    }

    public void copy(Word16 result) { // sets the values in "result" to be the same as the values in this instance; use "bit.assign"
        for (int i = 0; i < 16; i++) {
            //bits[i].assign(result.bits[i].getValue());
            result.bits[i].assign(bits[i].getValue());
        }
    }

    public void setBitN(int n, Bit source) { // sets the nth bit of this word to "source"
        //bits[n] = source;
        bits[n].assign(source.getValue());
    }

    public void getBitN(int n, Bit result) { // sets result to be the same value as the nth bit of this word
        //bits[n].assign(result.getValue());
        result.assign(bits[n].getValue());
    }

    public boolean equals(Word16 other) { // is other equal to this
        return equals(this, other);
    }

    public static boolean equals(Word16 a, Word16 b) {
        return a.toString().equals(b.toString());
    }

    public void and(Word16 other, Word16 result) {
        and(this, other, result);
    }

    public static void and(Word16 a, Word16 b, Word16 result) {
        for (int i = 0; i < 16; i++) {
            Bit.and(a.bits[i], b.bits[i], result.bits[i]);
        }
    }

    public void or(Word16 other, Word16 result) {
        or(this, other, result);
    }

    public static void or(Word16 a, Word16 b, Word16 result) {
        for (int i = 0; i < 16; i++) {
            Bit.or(a.bits[i], b.bits[i], result.bits[i]);
        }
    }

    public void xor(Word16 other, Word16 result) {
        xor(this, other, result);
    }

    public static void xor(Word16 a, Word16 b, Word16 result) {
        for (int i = 0; i < 16; i++) {
            Bit.xor(a.bits[i], b.bits[i], result.bits[i]);
        }
    }

    public void not( Word16 result) {
        not(this, result);
    }

    public static void not(Word16 a, Word16 result) {
        for (int i = 0; i < 16; i++) {
            Bit.not(a.bits[i], result.bits[i]);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Bit bit : bits) {
            sb.append(bit.toString());
            sb.append(",");
        }
        return sb.toString();
    }
}