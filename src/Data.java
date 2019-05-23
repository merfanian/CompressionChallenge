import java.util.HashMap;

public class Data {
    HashMap<Character, String> lettersTable;
    long length;
    long decodedLength;

    public Data(HashMap<Character, String> lettersTable, long length, long decodedLength) {
        this.lettersTable = lettersTable;
        this.length = length;
        this.decodedLength = decodedLength;
    }
}
