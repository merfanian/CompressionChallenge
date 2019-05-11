package Models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Compressor {
    Scanner scanner;

    public Compressor(Scanner scanner) {
        this.scanner = scanner;
    }

    public void compress() {
        String s = scanner.next();
        CharacterCounter characterCounter = new CharacterCounter(s);
        HashSet<Node> nodes = characterCounter.getNodes();
        HuffmanCoding huffmanCoding = new HuffmanCoding(nodes);
        huffmanCoding.makeHuffmanTree();
        HashMap<Character, String> lettersTable = huffmanCoding.getLettersTable();

        for (int i = 0; i < s.length(); i++) {
            System.out.print(lettersTable.get(s.charAt(i)));
        }
    }

}


