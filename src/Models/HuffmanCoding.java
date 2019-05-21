package Models;

import Exceptions.TableNotFoundException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

class HuffmanCoding {
    HashMap<Character, String> lettersTable;
    HashSet<Node> nodes;

    public HuffmanCoding(HashSet<Node> nodes) {
        this.nodes = nodes;
        lettersTable = new HashMap<>(nodes.size());
    }

    public HashMap<Character, String> getLettersTable() {
        return lettersTable;
    }

    public void setLettersTable(HashMap<Character, String> lettersTable) {
        this.lettersTable = lettersTable;
    }

    public HashSet<Node> getNodes() {
        return nodes;
    }

    public void setNodes(HashSet<Node> nodes) {
        this.nodes = nodes;
    }

    public void makeHuffmanTree() {
        if (nodes == null)
            throw new TableNotFoundException("Table is not defined");

        PriorityQueue<Node> queue = new PriorityQueue<>(nodes.size(), new Node.MyComparator());
        queue.addAll(nodes);

        Node root = null;
        while (queue.size() > 1) {
            Node x = queue.peek();
            queue.poll();

            Node y = queue.peek();
            queue.poll();

            Node f = new Node();
            f.setFrequency(x.getFrequency() + y.getFrequency());
            f.setCharacter('-');

            f.setLeft(x);
            f.setRight(y);

            root = f;

            queue.add(f);
        }

        makelettersCode(root, "");
    }

    private void makelettersCode(Node root, String s) {
        if (root.getLeft() == null && root.getRight() == null
            //    & Character.isLetter(root.getCharacter())
        ) {
            lettersTable.put(root.getCharacter(), s);
            return;
        }
        makelettersCode(root.getLeft(), s + "0");
        makelettersCode(root.getRight(), s + "1");
    }
}
