package edu.princeton.cs.stdlib;
/*************************************************************************
 *  Compilation:  javac HexDump.java
 *  Execution:    java HexDump < input
 *  Dependencies: BinaryStdIn.java
 *  
 *  Reads in a binary file and writes out the bytes in hex, 16 per line.
 *
 *  % java HexDump < input.txt
 *
 *  % hexdump < input.txt
 *  % od -t x1 < iput.txt
 *
 *************************************************************************/

public class HexDump {

    public static void main(String[] args) {
        int BYTES_PER_LINE = 16;

        for (int i = 0; !BinaryStdIn.isEmpty(); i++) {
            if (i == 0)                       StdOut.printf("%07x ", i);
            else if (i % BYTES_PER_LINE == 0) StdOut.printf("\n%07x ", i);
            else                              StdOut.print(" ");
            char c = BinaryStdIn.readChar();
            StdOut.printf("%02x", c & 0xff);
        }
        StdOut.println();
    }
}
