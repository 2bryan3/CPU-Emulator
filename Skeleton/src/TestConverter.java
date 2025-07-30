public class TestConverter {
    public static void fromInt(int value, Word32 result) {
        int temp = value;
        int i = 0;

        if(value < 0){
            value = (value * -1) - 1;
        }

        for (int j = 0; j < 32; j++) {
            result.setBitN(j, new Bit(false));
        }

        while (value > 0){
            int remainder = value % 2;
            result.setBitN(31-i, new Bit(remainder == 1));
            value = value / 2;
            i++;
        }

        if (temp < 0) {
            result.not(result);
        }

        System.out.println(result);
    }

    public static int toInt(Word32 value) {
        Bit[] bits= new Bit[32];
        bits = value.getBits();
        Bit[] reversed = new Bit[32];
        int decimal = 0;
        boolean flag = false;

        if(bits[0].getValue() == Bit.boolValues.TRUE){
            value.not(value);
            flag = true;
        }

        for (int i = 0; i < 32; i++) {
            reversed[i] = bits[31-i];
        }

        for (int i = 0; i < 32; i++) {
            if(reversed[i].getValue() == Bit.boolValues.TRUE){
                decimal += Math.pow(2, i);
            } else {
                decimal += 0;
            }
        }

        if(flag){
            decimal = (decimal * -1) - 1;
        }
        System.out.println(decimal);
        return decimal;
    }

    public static void main(String[] args) {
        int num = 1;
        int num2 = -4;

        Word32 result = new Word32();
        fromInt(num, result);
        System.out.println(result);
        int number = toInt(result);
        System.out.println(number);

        Word32 result2 = new Word32();
        fromInt(num2, result2);
        System.out.println(result2);
        int number2 = toInt(result2);
        System.out.println(number2);
    }
}