public class InstructionCache{
    public Word32 address = new Word32();
    public Word32 value = new Word32();
    public Word32 firstAddress = new Word32();
    public Word32 lookUpAddress = new Word32();

    public Word32[] cache = new Word32[8];

    public int addressAsInt(Word32 address) {
        Bit result = new Bit(false);
        int valueAsInt = 0;
        for(int i = 0; i < 32; i++) {
            address.getBitN(31-i,result);
            if(result.getValue() == Bit.boolValues.TRUE){
                valueAsInt += Math.pow(2, i);
            }
        }
        if(valueAsInt > 999){
            throw new ArithmeticException();
        } else {
            return valueAsInt;
        }
    }

    public boolean read(){
        int lookUpAddress = addressAsInt(address);
        int addressStart = addressAsInt(firstAddress);
        if(addressStart == 0 && lookUpAddress == 0){
            addressStart = lookUpAddress;
            address.copy(firstAddress);
        }
        int addressEnd = addressStart + 7;
        int addressInCache = lookUpAddress - addressStart;
        if(lookUpAddress >= addressStart && lookUpAddress <= addressEnd){
            if(cache[addressInCache] == null){
                return false;
            }
            cache[addressInCache].copy(this.value);
            return true;
        } else {
            return false;
        }
    }

    public void loadIntoCache(Memory mem) {
        Word32 temp = new Word32();
        fromInt(1,temp);
        for (int i = 0; i < 8; i++) {
            firstAddress.copy(mem.address);
            mem.read();
            cache[i] = new Word32();
            mem.value.copy(cache[i]);
            Adder.add(temp, firstAddress, firstAddress);
        }
        cache[0].copy(value);
        address.copy(firstAddress);
    }


    public static void fromInt(int value, Word32 result) {
        int temp = value;
        int i = 0;
        if (value < 0){
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
    }
}
