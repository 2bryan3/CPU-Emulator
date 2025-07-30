# CPU Emulator in Java

A simple CPU emulator simulating a custom instruction set architecture with memory hierarchy, implemented in Java.

## Overview:

This project is a CPU emulator that models a simplified processor with registers, memory, instruction cache, L2 cache, and basic arithmetic and logic operations. It simulates instruction fetch, decode, execution, and store cycles to run assembly-like programs.
## Components:

Processor: Simulates the CPU core with a register file, instruction decoding, execution of instructions, branching, and stack handling.

Memory: Simulates main memory (DRAM) with word-addressable storage.

InstructionCache and L2Cache: Models a two-level cache hierarchy for instruction fetching and memory access optimization.

Word and Bit Classes: Provides bit-level data structures to represent 32-bit words and individual bits, supporting bitwise operations.

ALU, Multiplier, Shifter: Implements arithmetic, logical, multiplication, and bit-shifting operations.

Program Control: Supports branching, subroutine calls, and basic system calls for I/O.

## Features:

Emulates a 32-bit processor with 32 registers.

Supports instruction fetch-decode-execute-store cycle.

Includes a two-level cache system for faster instruction and data access.

Provides bitwise operations at the fundamental level (AND, OR, XOR, NOT).

Supports basic instructions including arithmetic, logic, load/store, branching, and system calls.

Uses a stack for subroutine calls and returns.

Simulates clock cycles with varying costs depending on cache hits or misses.

## How It Works:

Fetch: The processor fetches instructions from the instruction cache or L2 cache; if missed, data is loaded from main memory.

Decode: The instruction bits are decoded into operation codes and operands.

Execute: The ALU and supporting classes perform the requested operations.

Store: Results are stored back to registers or memory as needed.

Control Flow: Branches and subroutine calls update the program counter and stack accordingly.

Caching: Instruction and data accesses leverage multi-level caches to simulate realistic latency.

This CPU emulator provides a foundation for learning low-level computing concepts, instruction pipelining, memory hierarchy, and bitwise manipulation in a Java environment.
