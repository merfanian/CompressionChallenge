package Models;

import java.util.Comparator;

public class Node {

    private int frequency;
    private char character;
    private Node left;
    private Node right;

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    static class MyComparator implements Comparator<Node> {
        public int compare(Node x, Node y) {

            return x.getFrequency() - y.getFrequency();
        }
    }
}
