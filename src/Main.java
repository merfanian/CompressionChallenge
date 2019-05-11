import Models.Compressor;
import Models.Decompressor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hi, type your command num:");
        System.out.println("1. compress file");
        System.out.println("2. decompress file");
        int i = scanner.nextInt();
        switch (i) {
            case 1:
                Compressor compressor = new Compressor(scanner);
                compressor.compress();
                break;
            case 2:
                new Decompressor();
                break;
        }
    }
}
