import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Processor {
    private InstructionCache cache;
    private L2Cache l2Cache;
    private Memory mem;
    ALU alu = new ALU();
    public List<String> output = new LinkedList<>();
    private Word16 currentInstruction = new Word16();
    private Word32 OPcode, valueCode1, valueCode2;
    private Word32[] registerArray = new Word32[32];
    private int register1, register2, mode;
    private boolean flag = false;
    private boolean run = true;
    private int counter = 0;
    private Bit currentBit = new Bit(false);
    private Word32 temp = new Word32();
    private String OPcodeString = "";
    private Stack<Word32> stack = new Stack<>();
    private int format;
    int currentClockCycle = 0;
    private boolean cacheFound = false;

    public Processor(Memory m) {
        mem = m;
        cache = new InstructionCache();
        l2Cache = new L2Cache();
    }

    public void run() {
        initializeArray(registerArray, 32);
        while (run) {
            fetch();
            decode();
            execute();
            store();
        }
    }

    private void fetch() {
        fromInt(counter, cache.address);
        if(!cache.read()){
            fromInt(counter, l2Cache.lookupAddress);
            if(!l2Cache.read(cache)){
                l2Cache.loadIntoL2Cache(mem);
                l2Cache.read(cache);
                cache.read();
                currentClockCycle += 400;
            } else {
                cache.read();
                currentClockCycle += 50;
            }
        } else {
            currentClockCycle += 10;
        }
        Word16 instructions1 = new Word16();
        Word16 instructions2 = new Word16();

        if (!flag) {
            cache.value.getTopHalf(instructions1);
            instructions1.copy(currentInstruction);
            flag = true;
        } else {
            cache.value.getBottomHalf(instructions2);
            instructions2.copy(currentInstruction);
            flag = false;
            counter++;
        }
    }

    private void decode() {
        OPcode = getOPCodes(currentInstruction, 0, 4, false);
        OPcodeString = convertToString(currentInstruction, 0);
        currentInstruction.getBitN(5, currentBit);
        if (currentBit.getValue() == Bit.boolValues.TRUE){
            valueCode1 = getOPCodes(currentInstruction, 6, 10, true);
        } else {
            valueCode1 = getOPCodes(currentInstruction, 6, 10, false);
        }
        valueCode2 = getOPCodes(currentInstruction, 11, 15, false);

        if (OPcodeString.equals("ftfff")) { //syscall
            format = 7;
        } else if(OPcodeString.equals("ftfft") || OPcodeString.equals("ftftf")){ //call & return
            format = 8;
            if(OPcodeString.equals("ftfft")){
                valueCode1 = getOPCodes(currentInstruction, 11, 15, true);
            }
        } else if (OPcodeString.equals("fttff") || OPcodeString.equals("fttft") || OPcodeString.equals("ftttf") || OPcodeString.equals("ftttt") ||
                OPcodeString.equals("tffff") || OPcodeString.equals("tffft")) { // ble & blt & bge & bgt & beq & bne
            format = 4;
            valueCode1 = getOPCodes(currentInstruction, 11, 15, true);
        } else if (OPcodeString.equals("fftff") || OPcodeString.equals("ffttt")) { //leftshift or rightshift
            format = 2;
            valueCode1.copy(alu.op2);
            registerArray[toInt(valueCode2)].copy(alu.op1);
            currentClockCycle += 2;
        } else if (OPcodeString.equals("tftff")) { //copy
            Word32 temp = new Word32();
            format = 3;
            if (currentBit.getValue() == Bit.boolValues.FALSE) {
                register1 = toInt(valueCode1);
                register2 = toInt(valueCode2);
            } else {
                register2 = toInt(valueCode2);
            }
        } else if (OPcodeString.equals("tfftt")) { //store
            format = 5;
            currentClockCycle += 50;
            if (currentBit.getValue() == Bit.boolValues.FALSE) {
                registerArray[toInt(valueCode2)].copy(mem.address);
                registerArray[toInt(valueCode1)].copy(mem.value);
            } else {
                registerArray[toInt(valueCode2)].copy(mem.address);
                valueCode1.copy(mem.value);
            }
        } else if (OPcodeString.equals("tfftf")) { // load
            format = 6;
            currentClockCycle += 50;
            if (currentBit.getValue() == Bit.boolValues.FALSE) {
                registerArray[toInt(valueCode1)].copy(mem.address);
                mem.read();
            } else {
                temp = registerArray[toInt(valueCode2)];
                Adder.add(temp, valueCode1, temp);
                temp.copy(mem.address);
            }
        } else if (currentBit.getValue() == Bit.boolValues.FALSE) {
            if(OPcodeString.equals("ffftt")){ //multiply
                currentClockCycle += 10;
            }
            format = 1;
            register1 = toInt(valueCode1);
            registerArray[register1].copy(alu.op1);
            register2 = toInt(valueCode2);
            registerArray[register2].copy(alu.op2);

        } else if (OPcodeString.equals("fftft")){ //subtract
            if (currentBit.getValue() == Bit.boolValues.TRUE){
                registerArray[toInt(valueCode2)].copy(alu.op1);
                valueCode1.copy(alu.op2);
            } else {
                registerArray[toInt(valueCode2)].copy(alu.op2);
                registerArray[toInt(valueCode1)].copy(alu.op1);
            }
            register2 = toInt(valueCode2);
            format = 2;
            currentClockCycle += 2;
        } else if (OPcodeString.equals("ftftt")){ //compare
            registerArray[toInt(valueCode2)].copy(alu.op1);
            valueCode1.copy(alu.op2);
            format = 0;
        } else {
            format = 2;
            valueCode1.copy(alu.op1);
            register2 = toInt(valueCode2);
            registerArray[register2].copy(alu.op2);
            currentClockCycle += 2;
        }
    }

    private void execute() {
        String halt = "f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,";

        if (currentInstruction.toString().equals(halt)) {
            run = false;
            System.out.println("Program terminated, current clock cycle: " + currentClockCycle);
        } else if (format == 4) {
            if (OPcodeString.equals("fttff")) { //ble
                if (alu.equal.getValue() == Bit.boolValues.TRUE || alu.less.getValue() == Bit.boolValues.TRUE) {
                    counter += toInt(valueCode1);
                    counter--;
                }
            } else if (OPcodeString.equals("fttft")) { //blt
                if (alu.less.getValue() == Bit.boolValues.TRUE) {
                    counter += toInt(valueCode1);
                    counter--;
                }
            } else if (OPcodeString.equals("ftttf")) { //bge
                if (alu.less.getValue() == Bit.boolValues.FALSE ||alu.equal.getValue() == Bit.boolValues.TRUE) {
                    counter += toInt(valueCode1);
                    counter--;
                }
            } else if (OPcodeString.equals("ftttt")) { //bgt
                if (alu.less.getValue() == Bit.boolValues.FALSE) {
                    counter += toInt(valueCode1);
                    counter--;
                }
            } else if (OPcodeString.equals("tffff")) { //beq
                if (alu.equal.getValue() == Bit.boolValues.TRUE) {
                    counter += toInt(valueCode1);
                    counter--;
                }
            } else if (OPcodeString.equals("tffft")) { //bne
                if (alu.equal.getValue() == Bit.boolValues.FALSE) {
                    counter += toInt(valueCode1);
                    counter--;
                }
            }
            flag = false;
        } else if (format == 5) {
            mem.write();
        } else if (format == 6) {
            mem.read();
            mem.value.copy(registerArray[toInt(valueCode2)]);
        } else if (format == 7) {
            mode = toInt(valueCode2);
        } else if (format == 8){
            if (OPcodeString.equals("ftfft")) { //call
                Word32 temp = new Word32();
                fromInt(counter, temp);
                stack.push(temp);
                counter += toInt(valueCode1) -1;
            } else {
                counter = toInt(stack.pop());
                flag = false;
            }
        } else {
            currentInstruction.copy(alu.instruction);
            alu.doInstruction();
        }
    }

    private void printReg() {
        for (int i = 0; i < 32; i++) {
            var line = "r"+ i + ":" + registerArray[i]; // TODO: add the register value here...
            output.add(line);
            System.out.println(line);
        }
    }

    private void printMem() {
        for (int i = 0; i < 1000; i++) {
            Word32 addr = new Word32();
            Word32 value = new Word32();
            fromInt(i, addr);
            addr.copy(mem.address);
            mem.read();
            mem.value.copy(value);
            var line = i + ":" + value;
            output.add(line);
            System.out.println(line);
        }
    }

    private void store() {
        if (format == 1){
            alu.result.copy(registerArray[register2]);
        } else if (format == 2){
            alu.result.copy(registerArray[register2]);
        } else if (format == 3){
            if (currentBit.getValue() == Bit.boolValues.FALSE){
                registerArray[register1].copy(registerArray[register2]);
            } else {
                valueCode1.copy(registerArray[register2]);
            }
        }
        Word32 counterWord32 = new Word32();
        if (format == 4){
            fromInt(counter, counterWord32);
            counterWord32.copy(mem.address);
        } else {
            Word32 zero = new Word32();
            fromInt(counter, counterWord32);
            Adder.add(zero, counterWord32, mem.address);
        }
        if (format == 7) {
            if (mode == 0) {
                printReg();
            } else if (mode == 1) {
                printMem();
            }
        }
    }

    public static Word32 getOPCodes(Word16 word, int start, int end, boolean isItSignedValue) {
        Word32 result = new Word32();
        Bit currentBit = new Bit(false);
        int j = 31;

        if (isItSignedValue){
            word.getBitN(start, currentBit);
            if (currentBit.getValue() == Bit.boolValues.TRUE) {
                Bit temp = new Bit(true);
                for (int i = 0; i < 27; i++) {
                    result.setBitN(i, temp);
                }
            }
        }
        for (int i = 0; i <= end - start; i++) {
            word.getBitN(start, currentBit);
            word.getBitN(end - i, currentBit);
            result.setBitN(j, currentBit);
            j--;
        }
        return result;
    }

    public static String convertToString(Word16 word, int start) {
        String newWord = "";
        for (int i = 0; i < 5; i++){
            Bit currentBit = new Bit(false);
            word.getBitN(start + i, currentBit);
            if (currentBit.getValue() == Bit.boolValues.TRUE){
                newWord += currentBit.toString();
            } else {
                newWord += currentBit.toString();
            }
        }
        return newWord;
    }

    public static int toInt(Word32 value) {
        Bit[] bits;
        bits = value.getBits();
        Bit[] reversed = new Bit[32];
        int decimal = 0;
        boolean flag = false;

        if (bits[0].getValue() == Bit.boolValues.TRUE){
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
        if (flag){
            decimal = (decimal * -1) - 1;
        }
        return decimal;
    }

    public static void initializeArray(Word32[] array, int size) {
        for (int i = 0; i < size; i++) {
            array[i] = new Word32();
        }
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