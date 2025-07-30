public class Adder {
    private Bit[] bits;

    public static void subtract(Word32 a, Word32 b, Word32 result) {
        Word32 negateB = new Word32();
        Word32 temp = new Word32();
        Word32 one = new Word32();
        one.setBitN(31, new Bit(true));
//        Word32 temp32A = new Word32();
//        Word32 temp32B = new Word32();
//        a.copy(temp32A);
//        b.copy(temp32B);
        //System.out.println("The original result: " + result);
        for(int i = 0; i < 32; i++){
            result.setBitN(i, new Bit(false));
        }

        b.not(negateB);

        add(negateB, one, temp);

        add(a, temp, result);
    }

    public static void add(Word32 a, Word32 b, Word32 result) {
        Bit currentBitA = new Bit(false);
        Bit currentBitB = new Bit(false);
        Bit carryIn = new Bit(false);
        Word32 temp32A = new Word32();
        Word32 temp32B = new Word32();
        a.copy(temp32A);
        b.copy(temp32B);


        for(int i = 0; i < 32; i++){
            result.setBitN(i, new Bit(false));
        }

        for (int i = 0; i < 32; i++) {
            temp32A.getBitN(31-i, currentBitA);
            temp32B.getBitN(31-i, currentBitB);

            Bit temp = new Bit(false);
            Bit temp2 = new Bit(false);
            Bit temp3 = new Bit(false);
            Bit sum = new Bit(false);
            Bit carryOut = new Bit(false);
            //X XOR Y XOR C_in
            Bit.xor(currentBitA, currentBitB, temp);
            Bit.xor(temp, carryIn, sum);
            //X AND Y OR ((X XOR Y) AND C_in)
            Bit.and(currentBitA, currentBitB, temp3);
            Bit.and(carryIn, temp, temp2);
            Bit.or(temp3, temp2, carryOut);

            result.setBitN(31 - i, sum);

            carryIn.assign(carryOut.getValue());
        }
    }
}
