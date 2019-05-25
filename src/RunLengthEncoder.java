import java.util.ArrayList;
import java.util.stream.Collectors;

public class RunLengthEncoder {

    public String encode(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {

            // Count occurrences of current character
            int count = 1;
            while (i < string.length() - 1 &&
                    string.charAt(i) == string.charAt(i + 1)) {
                count++;
                i++;
            }
            if (count > 1)
                stringBuilder.append(count).append(string.charAt(i));
            else
                stringBuilder.append(string.charAt(i));
        }
        return stringBuilder.toString();
    }

    public String decode(String string, int length) {
        ArrayList<Character> chars = new ArrayList<Character>(length);
        int count = 0;
        char c = string.charAt(0);
        int index = 0;
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i)))
                c = string.charAt(i);

            if (Character.isDigit(string.charAt(i))) {
                count = count * 10 + string.charAt(i) - '0';
            } else {
                if (count == 0)
                    count = 1;
                while (count > 0) {
                    chars.add(c);
                    count--;

                }
            }
        }
        return chars.stream().map(Object::toString).collect(Collectors.joining());

    }
}
