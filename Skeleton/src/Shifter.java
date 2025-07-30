public class Shifter {
    public static void LeftShift(Word32 source, int amount, Word32 result) {
        int j;
        for (j = 0; j != amount; j++) {
            result.setBitN(31 - j, new Bit(false));
        }
        for (int i = 0; i < 32; i++) {
            if (31 - j - i >= 0) {
                result.setBitN(31 - j - i, source.getBits()[31 - i]);
            }
        }
    }

    public static void RightShift(Word32 source, int amount, Word32 result) {
        int k;
        for (k = 0; k != amount; k++) {
            result.setBitN(k, new Bit(false));
        }
        for (int i = 0; i < 32; i++) {
            if (i + k <= 31) {
                result.setBitN(i + k, source.getBits()[i]);
            }
        }
    }
}
