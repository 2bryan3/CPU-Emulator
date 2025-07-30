public class Memory {
    public Word32 address= new Word32();
    public Word32 value = new Word32();

    private final Word32[] dram= new Word32[1000];

    public int addressAsInt() {
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

    public Memory() {
        for(int i= 0; i < 1000; i++){
            dram[i] = new Word32();
        }
    }

    public void read() {
        int address = addressAsInt();
        dram[address].copy(this.value);
    }

    public void write() {
        int address = addressAsInt();
        this.value.copy(dram[address]);
    }

    public void load(String[] data) {

        for(int j = 0; j < data.length; j++) {
            if(data[j].length() != 32){
                throw new ArithmeticException();
            }
            Word32 current = new Word32();
            for (int i = 0; i < data[j].length(); i++) {
                if (data[j].charAt(i) == 't') {
                    current.setBitN(i, new Bit(true));
                } else {
                    current.setBitN(i, new Bit(false));
                }
            }
            dram[j] = current;
        }
    }
}
