package Models;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Decompressor {

    @Test
    public static void main(String[] args) {
        try {
            FileReader fileReader = new FileReader("restoreData.json");
            Gson gson = new Gson();
            Data data = gson.fromJson(fileReader, Data.class);
            FileInputStream fileInputStream = new FileInputStream(new File("op"));
            byte[] bytes = new byte[(int) (data.length / 8 + 1)];
            byte[] temp = new byte[1];
            int read = 0;
            int i = 0;
            while (true) {
                read = fileInputStream.read(temp);
                if (read == -1)
                    break;
                bytes[i++] = temp[0];
            }
            String s = convertBytesToString(bytes);
            System.out.println(s);
            Decompressor decompressor = new Decompressor();
            decompressor.decompress(data, s);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static String convertBytesToString(byte[] bytes) {
        char[] chars = new char[bytes.length * 8];
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < 8 && i + j < bytes.length * 8; j++) {
                byte temp = (byte) (bytes[i] >> j);
                temp &= 0x01;
                chars[i * 8 + 7 - j] = temp == 0 ? '0' : '1';
            }
        }
        return String.valueOf(chars);
    }

    public void decompress(Data data, String str) {
        HashMap<Character, String> lettersTable = data.lettersTable;
        HashMap<String, Character> codeTable = new HashMap<>();

        lettersTable.forEach((character, s) -> {
            codeTable.put(s, character);
        });

        StringBuilder stringBuilder = new StringBuilder();

        long length = data.length;
        long count = 0;
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < length && i + j < str.length() && count < data.decodedLength; j++) {
                if (codeTable.containsKey(str.substring(i, i + j + 1))) {
                    stringBuilder.append(codeTable.get(str.substring(i, i + j + 1)));
                    count++;
                    i += j;
                    break;
                }
            }
        }

        System.out.println(stringBuilder);
    }
}
