import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;


public class Compressor {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            Scanner scanner = new Scanner(new File("input.txt"));
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                stringBuilder.append(s).append("\n");
            }
            String encode = BurrowsWheeler.encode("$" + stringBuilder.toString());
            String toWrite = new RunLengthEncoder().encode(encode);
            Compressor compressor = new Compressor();
            compressor.compress(toWrite);
            scanner.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("\n\nFile compressed succesfully!\nTime elapsed: " + (System.currentTimeMillis() - start) + " ms\n\n");
    }

    public void compress(String s) {
        CharacterCounter characterCounter = new CharacterCounter(s);
        HashSet<Node> nodes = characterCounter.getNodes();
        HuffmanEncoder huffmanEncoder = new HuffmanEncoder(nodes);
        huffmanEncoder.makeHuffmanTree();
        HashMap<Character, String> lettersTable = huffmanEncoder.getLettersTable();
        write(s, lettersTable);
    }

    private void write(String str, HashMap<Character, String> lettersTable) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            stringBuilder.append(lettersTable.get(str.charAt(i)));
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("op"));
            for (int i = 0; i < stringBuilder.length() - 7; i += 8) {
                int i1 = Integer.parseInt(stringBuilder.substring(i, i + 8), 2);
                fileOutputStream.write((byte) i1);
            }
            int i = stringBuilder.length() - stringBuilder.length() % 8;
            if (stringBuilder.length() % 8 != 0) {
                int i1 = Integer.parseInt(stringBuilder.substring(i, i + stringBuilder.length() % 8), 2);
                fileOutputStream.write((byte) i1 << (8 - stringBuilder.length() % 8));
            }
            fileOutputStream.close();
            writeRestoreData(stringBuilder.toString(), lettersTable, str.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeRestoreData(String outStr, HashMap<Character, String> lettersTable, long length) {
        Gson gson = new Gson();
        Data data = new Data(lettersTable, outStr.length(), length);
        String toJson = gson.toJson(data);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("restoreData.json"));
            fileOutputStream.write(toJson.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}