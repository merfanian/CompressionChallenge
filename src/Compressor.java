import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;


public class Compressor {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            System.out.println("Compression starts, please wait, process may take while.\n");
            Scanner scanner = new Scanner(new File("input.txt"));
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                stringBuilder.append(s).append("\n");
            }
            System.out.println("\t1- Scan completed.");
            Compressor compressor = new Compressor();
            HashMap<Character, Character> characterHashMap = compressor.mapToAscii(stringBuilder.toString());
            char[] chars = new char[stringBuilder.toString().length()];
            for (int i = 0; i < stringBuilder.length(); i++) {
                chars[i] = characterHashMap.get(stringBuilder.charAt(i));
            }
            String asciid = String.valueOf(chars);
            System.out.println("\t2- Do BWT on partitions:");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < asciid.length() / 900000 + 1; i++) {
                sb.append(BurrowsWheelerEncoder.encode("$" + asciid.substring(
                        i * 900000, (i + 1) * 900000 < asciid.length() ? (i + 1) * 900000 : asciid.length())));
                System.out.println("\t\t Partition " + (i + 1) + " BWT done!");
            }
            String encode = sb.toString();
            System.out.println("\t3- BWT Completed!");
            String toWrite = new RunLengthEncoder().encode(encode);
            System.out.println("\t4- RLE Completed!");
            compressor.compress(toWrite, characterHashMap);
            System.out.println("\t5- HuffmanEncoding Completed!");
            scanner.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("\n\nFile compressed successfully!\nTime elapsed: " + (System.currentTimeMillis() - start) + " ms\n\n");
    }

    public void compress(String s, HashMap<Character, Character> charMap) {
        CharacterCounter characterCounter = new CharacterCounter(s);
        HashSet<Node> nodes = characterCounter.getNodes();
        HuffmanEncoder huffmanEncoder = new HuffmanEncoder(nodes);
        huffmanEncoder.makeHuffmanTree();
        HashMap<Character, String> lettersTable = huffmanEncoder.getLettersTable();
        write(s, lettersTable, charMap);
    }

    private HashMap<Character, Character> mapToAscii(String s) {
        HashSet<Character> digits = new HashSet<Character>();
        HashSet<Character> nonDigits = new HashSet<Character>();
        HashMap<Character, Character> characterHashMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i)))
                digits.add(s.charAt(i));
            else
                nonDigits.add(s.charAt(i));
        }
        Random random = new Random();
        nonDigits.forEach(character -> characterHashMap.put(character, character));
        digits.forEach(character -> {
            while (true) {
                int i = random.nextInt(58) + 68;
                if (!characterHashMap.containsValue((char) i)) {
                    characterHashMap.put(character, (char) i);
                    break;
                }
            }

        });
        return characterHashMap;
    }


    private void write(String str, HashMap<Character, String> lettersTable, HashMap<Character, Character> charMap) {

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
            writeRestoreData(stringBuilder.toString(), lettersTable, str.length(), charMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeRestoreData(String outStr, HashMap<Character, String> lettersTable, long length, HashMap<Character, Character> charMap) {
        Gson gson = new Gson();
        Data data = new Data(lettersTable, charMap, outStr.length(), length);
        String toJson = gson.toJson(data);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("restoreData.json"));
            fileOutputStream.write(toJson.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}