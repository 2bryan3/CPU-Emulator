public class ALU {
    public Word16 instruction = new Word16();
    public Word32 op1 = new Word32();
    public Word32 op2 = new Word32();
    public Word32 result = new Word32();
    public Bit less = new Bit(false);
    public Bit equal = new Bit(false);

    public void doInstruction(){
        result = new Word32();
        Bit bit1 = new Bit(false);
        Bit bit2 = new Bit(false);
        Bit bit3 = new Bit(false);
        Bit bit4 = new Bit(false);
        Bit bit5 = new Bit(false);
        instruction.getBitN(0, bit1);
        instruction.getBitN(1, bit2);
        instruction.getBitN(2, bit3);
        instruction.getBitN(3, bit4);
        instruction.getBitN(4, bit5);

        if(bit1.getValue() == Bit.boolValues.FALSE && bit2.getValue() == Bit.boolValues.FALSE && bit3.getValue() == Bit.boolValues.FALSE
                && bit4.getValue() == Bit.boolValues.FALSE && bit5.getValue() == Bit.boolValues.TRUE){
            Adder.add(op1, op2, result);
        } else if(bit1.getValue() == Bit.boolValues.FALSE && bit2.getValue() == Bit.boolValues.FALSE && bit3.getValue() == Bit.boolValues.FALSE
                && bit4.getValue() == Bit.boolValues.TRUE && bit5.getValue() == Bit.boolValues.FALSE){
            Word32.and(op1, op2, result);
        } else if(bit1.getValue() == Bit.boolValues.FALSE && bit2.getValue() == Bit.boolValues.FALSE && bit3.getValue() == Bit.boolValues.FALSE
                && bit4.getValue() == Bit.boolValues.TRUE && bit5.getValue() == Bit.boolValues.TRUE){
            Multiplier.multiply(op1, op2, result);
        } else if (bit1.getValue() == Bit.boolValues.FALSE && bit2.getValue() == Bit.boolValues.FALSE && bit3.getValue() == Bit.boolValues.TRUE
                && bit4.getValue() == Bit.boolValues.FALSE && bit5.getValue() == Bit.boolValues.FALSE) {
            Shifter.LeftShift(op1, convertToInt(op2), result);
        } else if (bit1.getValue() == Bit.boolValues.FALSE && bit2.getValue() == Bit.boolValues.FALSE && bit3.getValue() == Bit.boolValues.TRUE
                && bit4.getValue() == Bit.boolValues.FALSE && bit5.getValue() == Bit.boolValues.TRUE){
            Adder.subtract(op1, op2, result);
        } else if(bit1.getValue() == Bit.boolValues.FALSE && bit2.getValue() == Bit.boolValues.FALSE && bit3.getValue() == Bit.boolValues.TRUE
                && bit4.getValue() == Bit.boolValues.TRUE && bit5.getValue() == Bit.boolValues.FALSE){
            Word32.or(op1, op2, result);
        } else if(bit1.getValue() == Bit.boolValues.FALSE && bit2.getValue() == Bit.boolValues.FALSE && bit3.getValue() == Bit.boolValues.TRUE
                && bit4.getValue() == Bit.boolValues.TRUE && bit5.getValue() == Bit.boolValues.TRUE){
            Shifter.RightShift(op1, convertToInt(op2), result);
        } else if(bit1.getValue() == Bit.boolValues.FALSE && bit2.getValue() == Bit.boolValues.TRUE && bit3.getValue() == Bit.boolValues.FALSE
                && bit4.getValue() == Bit.boolValues.TRUE && bit5.getValue() == Bit.boolValues.TRUE){
            if(op1.equals(op2)){
                less = new Bit(false);
                equal = new Bit(true);
            } else {
                equal = new Bit(false);
                Adder.subtract(op1, op2, result);
                Bit sign = new Bit(false);
                result.getBitN(0, sign);
                if (sign.getValue() == Bit.boolValues.TRUE) {
                    less = new Bit(true);
                } else {
                    less = new Bit(false);
                }
            }

        }
    }

    public int convertToInt(Word32 bits){
        int result = 0;

        for(int i = 0; i < 32; i++){
            Bit currentBit = new Bit(false);
            bits.getBitN(31-i, currentBit);
            if(i == 0 && currentBit.getValue() == Bit.boolValues.TRUE) {
                result += 1;
            }else if(currentBit.getValue() == Bit.boolValues.TRUE){
                result += 2 * i;
            }
        }
        return result;
    }
}