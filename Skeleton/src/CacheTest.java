import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CacheTest {
    @Test
    public void sum20Integers() {
        String[] program = {
                "copy 10 r0", // memory address -- 0
                "leftshift 5 r0", // memory address
                "copy 10 r1", // counter -- 1
                 "leftshift 1 r1", // make counter 20
                "load r0 r2", // get value from memory[r0] to r2 && start of loop -- 2
                "add r2 r3", // add values into r3
                "add 1 r0", // move up the address value -- 3
                "subtract 1 r1", // decrease the counter
                "compare 0 r1", // --4
                "bne -2", // go back to start of loop
                "syscall 0", // print registers-- 5
                "halt"
        };
        var p = runProgram(program);
    }
    @Test
    public void sum20IntegersBackwards() {
        String[] program = {
                "copy 10 r0", // memory address -- 0
                "leftshift 5 r0", // memory address
                "copy 10 r1", // counter -- 1
                "leftshift 1 r1", // make counter 20
                "load r0 r2", // get value from memory[r0] to r2 && start of loop -- 2
                "add r2 r3", // add values into r3
                "subtract 1 r0", // move down the address value -- 3
                "subtract 1 r1", // decrease the counter
                "compare 0 r1", // --4
                "bne -2", // go back to start of loop
                "syscall 0", // print registers -- 5
        };
        var p = runProgram(program);
    }
    @Test
    public void sum20LinkedListIntegers() {
        String[] program = {
                //setting up linked list
                "copy 0 r0", // make r0 0 -- 0
                "copy 10 r1", // counter
                "leftshift 1 r1", // make counter 20 --1
                "copy 13 r2", // memory address
                "leftshift 4 r2", // memory address is now 208 -- 2
                "copy r2 r3", // copy of memory address
                "copy 0 r4", // current node data -- 3
                "copy 0 r0", // waste space
                "add 1 r4", // add one to current data && beginning of loop -- 4
                "store r4 r3", // store node data
                "copy r3 r5", // set up next node -- 5
                "add 7 r5", // address of the next node
                "add 1 r3", // add one to current node to add next node data -- 6
                "store r5 r3", // store the next node value
                "load r3 r3", // load the next node value to the current node -- 7
                "subtract 1 r1", // decrease counter
                "compare 0 r1", // compare counter -- 8
                "bne -4", // go back to beginning of loop
                "subtract 6 r3", // go back to last "next node" address -- 9
                "copy 0 r0", // waste space
                "store r0 r3", // make next node address null or 0 -- 10
                "copy r2 r8", // copy the beginning address of the linked list
                "load r8 r6", // load data from current node to r6 && beginning of loop -- 11
                "copy 0 r0", // waste space
                "add r6 r7", // add to total -- 12
                "add 1 r8", // move to address of the next node
                "load r8 r9", // save address of next node in r9 -- 13
                "copy r9 r8", // make the next node the current node
                "compare 0 r8", // check if the address of the next node is null -- 14
                "bne -3", // go back to the beginning of the loop
                "syscall 0", // print registers -- 15
                "halt", // end of linked list

        };
        var p = runProgram(program);
        assertEquals("r7:f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t,f,t,f,f,t,f,", p.output.get(7));
    }

    private static Processor runProgram(String[] program) {
        var assembled = Assembler.assemble(program);
        var merged = Assembler.finalOutput(assembled);
        var m = new Memory();
        m.load(merged);
        var p = new Processor(m);
        p.run();
        return p;
    }
}
