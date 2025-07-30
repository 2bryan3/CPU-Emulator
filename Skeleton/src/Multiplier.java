public class Multiplier {
    public static void multiply(Word32 a, Word32 b, Word32 result) {
        Bit currentBitA = new Bit(false);
        Bit currentBitB = new Bit(false);
        Word32 tempResult2 = new Word32();
        Word32 tempResult3 = new Word32();

        for (int i = 0; i < 32; i++){
            Word32 tempResult1 = new Word32();
            b.getBitN(31-i, currentBitB);
            if(currentBitB.getValue() == Bit.boolValues.TRUE) {
                for (int j = 0; j < 32; j++) {
                    a.getBitN(31-j, currentBitA);
                    for(int k = 0; k < i; k++) {
                        tempResult1.getBitN(31-k, new Bit(false));
                    }
                    if(31-j-i >= 0) {
                        tempResult1.setBitN(31 - j - i, currentBitA);
                    }
                }
            }
//            else {
//                //TODO remove because it's not needed
//                for (int j = 0; j < 32; j++) {
//                    tempResult1.setBitN(31 - i, new Bit(false));
//                }
//            }
            Adder.add(tempResult1, tempResult2, tempResult3);
            tempResult3.copy(tempResult2);
            //System.out.println(tempResult2);
        }
        //result.copy(tempResult2);
        tempResult2.copy(result);
    }
}