import java.util.HashMap;

public class Assembler {
    public static String[] assemble(String[] input) {
        HashMap<String, String> instructions = new HashMap<>();
        HashMap<String, String> registers = new HashMap<>();
        //instructions
        instructions.put("halt", "fffff");
        instructions.put("add", "fffft");
        instructions.put("and", "ffftf");
        instructions.put("multiply", "ffftt");
        instructions.put("leftshift", "fftff");
        instructions.put("subtract", "fftft");
        instructions.put("or", "ffttf");
        instructions.put("rightshift", "ffttt");
        instructions.put("syscall", "ftfff");
        instructions.put("call", "ftfft");
        instructions.put("return", "ftftf");
        instructions.put("compare", "ftftt");
        instructions.put("ble", "fttff");
        instructions.put("blt", "fttft");
        instructions.put("bge", "ftttf");
        instructions.put("bgt", "ftttt");
        instructions.put("beq", "tffff");
        instructions.put("bne", "tffft");
        instructions.put("load", "tfftf");
        instructions.put("store", "tfftt");
        instructions.put("copy", "tftff");

        //registers
        registers.put("r0", "fffff");
        registers.put("r1", "fffft");
        registers.put("r2", "ffftf");
        registers.put("r3", "ffftt");
        registers.put("r4", "fftff");
        registers.put("r5", "fftft");
        registers.put("r6", "ffttf");
        registers.put("r7", "ffttt");
        registers.put("r8", "ftfff");
        registers.put("r9", "ftfft");
        registers.put("r10", "ftftf");
        registers.put("r11", "ftftt");
        registers.put("r12", "fttff");
        registers.put("r13", "fttft");
        registers.put("r14", "ftttf");
        registers.put("r15", "ftttt");
        registers.put("r16", "tffff");
        registers.put("r17", "tffft");
        registers.put("r18", "tfftf");
        registers.put("r19", "tfftt");
        registers.put("r20", "tftff");
        registers.put("r21", "tftft");
        registers.put("r22", "tfttf");
        registers.put("r23", "tfttt");
        registers.put("r24", "ttfff");
        registers.put("r25", "ttfft");
        registers.put("r26", "ttftf");
        registers.put("r27", "ttftt");
        registers.put("r28", "tttff");
        registers.put("r29", "tttft");
        registers.put("r30", "ttttf");
        registers.put("r31", "ttttt");

        String[] assembledInstruction = new String[input.length];

        for (int i = 0; i < input.length; i++) {
            String[] splitInstruction = input[i].split(" ");
            boolean flag = false;
            for (int j = 0; j < splitInstruction.length; j++) {
                if (instructions.containsKey(splitInstruction[j])) {
                    assembledInstruction[i] = (instructions.get(splitInstruction[j]));
                    if(splitInstruction[j].equals("syscall") || splitInstruction[j].equals("call")
                    || splitInstruction[j].equals("ble") || splitInstruction[j].equals("blt")
                    || splitInstruction[j].equals("bge") || splitInstruction[j].equals("bgt")
                    || splitInstruction[j].equals("beq") || splitInstruction[j].equals("bne")){
                        assembledInstruction[i] += convertFromInt(Integer.parseInt(splitInstruction[j+1]), 11);
                        j++;
                    } else if(splitInstruction[j].equals("return") || splitInstruction[j].equals("halt")) {
                        assembledInstruction[i] += "fffffffffff";
                    }
                } else if (registers.containsKey(splitInstruction[j])) {
                    if (!flag) {
                        assembledInstruction[i] += "f";
                        flag = true;
                    }
                    assembledInstruction[i] += (registers.get(splitInstruction[j]));
                } else {
                    if (!flag) {
                        assembledInstruction[i] += "t";
                        flag = true;
                    }
                    assembledInstruction[i] += convertFromInt(Integer.parseInt(splitInstruction[j]), 5);
                }
            }
        }
        return assembledInstruction;
    }

    public static String[] finalOutput(String[] input) {
        int len = input.length;
        String[] assembledInstruction = new String[(len + 1) / 2];
        int j = 0;

        for (int i = 0; i + 1 < len; i += 2) {
            assembledInstruction[j] = input[i] + input[i + 1];
            j++;
        }

        if (len % 2 == 1) {
            assembledInstruction[j] = input[len - 1] + "ffffffffffffffff";
        }

        return assembledInstruction;
    }


    public static String convertFromInt(int value, int size) {
        int temp = value;
        int i = 0;
        Word16 string = new Word16();
        StringBuilder convertedNumber = new StringBuilder();

        if(value < 0) {
            value = (value * -1) - 1;
        }

        for (int j = 0; j < 16; j++) {
            string.setBitN(j, new Bit(false));
        }

        while (value > 0){
            int remainder = value % 2;
            string.setBitN(15-i, new Bit(remainder == 1));
            value = value / 2;
            i++;
        }

        if (temp < 0) {
            string.not(string);
        }

        for (int j = 0; j < size; j++) {
            Bit bit = new Bit(false);
            string.getBitN(16-size+j, bit);
            convertedNumber.append(bit.toString());
        }

        return convertedNumber.toString();
    }
}