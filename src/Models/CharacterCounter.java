package Models;

import java.util.HashMap;
import java.util.HashSet;

public class CharacterCounter {

    String string;
    HashMap<Character, Integer> countTable;
    HashSet<Node> nodes;

    public CharacterCounter(String string) {
        this.string = string;
        nodes = new HashSet<>();
        countTable = new HashMap<>();
    }

    HashSet<Node> getNodes() {
        for (int i = 0; i < string.length(); i++) {
            if (!countTable.containsKey(string.charAt(i)))
                countTable.put(string.charAt(i), 1);
            else
                countTable.replace(string.charAt(i), countTable.get(string.charAt(i)) + 1);
        }

        countTable.forEach((character, integer) -> {
            Node node = new Node();
            node.setCharacter(character);
            node.setFrequency(integer);
            nodes.add(node);
        });

        return nodes;
    }

}
