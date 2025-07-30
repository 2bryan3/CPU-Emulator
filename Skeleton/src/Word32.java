public class Word32 {
    private Bit[] bits;

    public Bit[] getBits() {
        return bits;
    }

    public void setBits(Bit[] bits) {
        this.bits = bits;
    }

    public Word32() {
        bits = new Bit[32];
        for (int i = 0; i < 32; i++) {
            bits[i] = new Bit(false);
        }
    }

    public Word32(Bit[] in) {
        bits = new Bit[32];
        for (int i = 0; i < 32; i++) {
            if(in[i].getValue() == Bit.boolValues.TRUE){
                bits[i] = new Bit(true);
            } else {
                bits[i] = new Bit(false);
            }
        }
    }

    public void getTopHalf(Word16 result) { // sets result = bits 0-15 of this word. use bit.assign
        for (int i = 0; i < 16; i++) {
            //bits[i].assign(result.getBits()[i].getValue());
            result.getBits()[i].assign(bits[i].getValue());
        }
    }

    public void getBottomHalf(Word16 result) { // sets result = bits 16-31 of this word. use bit.assign
        for (int i = 0; i < 16; i++) {
            //bits[i+16].assign(result.getBits()[i].getValue());
            result.getBits()[i].assign(bits[i+16].getValue());
        }
    }

    public void copy(Word32 result) { // sets result's bit to be the same as this. use bit.assign
        for (int i = 0; i < 32; i++) {
            //this.bits[i].assign(result.bits[i].getValue());
            result.bits[i].assign(bits[i].getValue());
        }
    }

    public boolean equals(Word32 other) {
        return equals(this, other);
    }

    public static boolean equals(Word32 a, Word32 b) {
        return a.toString().equals(b.toString());
    }

    public void getBitN(int n, Bit result) { // use bit.assign
        //bits[n].assign(result.getValue());
        result.assign(bits[n].getValue());
    }

    public void setBitN(int n, Bit source) { //  use bit.assign
        //bits[n] = source;
        bits[n].assign(source.getValue());
    }

    public void and(Word32 other, Word32 result) {
        and(this, other, result);
    }

    public static void and(Word32 a, Word32 b, Word32 result) {
        for (int i = 0; i < 32; i++) {
            Bit.and(a.bits[i], b.bits[i], result.bits[i]);
        }
    }

    public void or(Word32 other, Word32 result) {
        or(this, other, result);
    }

    public static void or(Word32 a, Word32 b, Word32 result) {
        for (int i = 0; i < 32; i++) {
            Bit.or(a.bits[i], b.bits[i], result.bits[i]);
        }
    }

    public void xor(Word32 other, Word32 result) {
        xor(this, other, result);
    }

    public static void xor(Word32 a, Word32 b, Word32 result) {
        for (int i = 0; i < 32; i++) {
            Bit.xor(a.bits[i], b.bits[i], result.bits[i]);
        }
    }

    public void not( Word32 result) {
        not(this, result);
    }

    public static void not(Word32 a, Word32 result) {
        for (int i = 0; i < 32; i++) {
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
