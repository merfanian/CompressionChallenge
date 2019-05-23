import java.util.Scanner;

public class BWT {
    static {
        System.loadLibrary("BWT");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String encode = new BWT().encode("$" + s);
        System.err.println("encoded string received in java: " + encode);
        String decode = new BWT().decode(encode);
        System.err.println("decoded string received in java: " + decode);
    }

    public native String encode(String s);

    public native String decode(String s);
}

