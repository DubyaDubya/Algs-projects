/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Topological;

import java.util.HashMap;

public class WordNet {
    private final Digraph wordDAG;
    private final HashMap<Integer, SET<String>> synMap = new HashMap<>();
    private final HashMap<String, SET<Integer>> defMap = new HashMap<>();
    private final SAP sap;


    public WordNet(String synsets, String hypernyms) {

        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("an argument is null");
        // synsets will be used to create hash map of Key(int) to Value (Set<String>)

        In synsIn = new In(synsets);
        while (!synsIn.isEmpty()) {
            String line = synsIn.readLine();
            String[] tokens = line.split(",");
            int synId = Integer.parseInt(tokens[0]);
            String[] words = tokens[1].split(" ");
            SET<String> synonyms = new SET<>();
            for (int i = 0; i < words.length; i++) {
                if (this.defMap.containsKey(words[i]))
                    defMap.get(words[i]).add(synId);
                else {
                    SET<Integer> defs = new SET<>();
                    defs.add(synId);
                    this.defMap.put(words[i], defs);
                }
                synonyms.add(words[i]);
            }
            this.synMap.put(synId, synonyms);

        }

        In hypsIn1 = new In(hypernyms);
        int lineCounter = 0;
        Bag<Integer[]> edges = new Bag<>();
        while (!hypsIn1.isEmpty()) {
            lineCounter++;
            String line = hypsIn1.readLine();
            String[] tokens = line.split(",");
            Integer[] eyges = new Integer[tokens.length];
            for (int i = 0; i < tokens.length; i++)
                eyges[i] = Integer.parseInt(tokens[i]);
            edges.add(eyges);
        }
        wordDAG = new Digraph(lineCounter);
        for (Integer[] arr : edges) {
            for (int i = 1; i < arr.length; i++)
                wordDAG.addEdge(arr[0], arr[i]);
        }
        hypsIn1.close();

        Topological top = new Topological(wordDAG.reverse());
        if (!top.hasOrder()) throw new IllegalArgumentException("Not a DAG");
        int rootIndex = top.order().iterator().next();
        if (!rootChecker(wordDAG.reverse(), rootIndex))
            throw new IllegalArgumentException("DAG Not Rooted");
        sap = new SAP(wordDAG);
    }

    private boolean rootChecker(Digraph d, int rootIndex) {
        boolean[] marked = new boolean[d.V()];
        Stack<Integer> dfsStack = new Stack<>();
        dfsStack.push(rootIndex);
        marked[rootIndex] = true;
        int counter = 1;
        while (!dfsStack.isEmpty()) {
            for (int w : d.adj(dfsStack.pop())) {
                if (!marked[w]) {
                    marked[w] = true;
                    dfsStack.push(w);
                    counter++;
                }
            }
        }
        return (counter == d.V());


    }


    public Iterable<String> nouns() {
        return defMap.keySet();
    }

    public boolean isNoun(String noun) {
        if (noun == null) throw new IllegalArgumentException("Noun is null");
        return defMap.containsKey(noun);
    }

    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        Iterable<Integer> b = defMap.get(nounA);
        Iterable<Integer> c = defMap.get(nounB);

        return sap.length(b, c);
    }

    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        int ancestor = sap.ancestor(defMap.get(nounA), defMap.get(nounB));
        StringBuilder sippy = new StringBuilder();
        for (String l : synMap.get(ancestor)) {
            sippy.append(l);
            sippy.append(" ");
        }
        sippy.deleteCharAt(sippy.length() - 1);
        return sippy.toString();
    }

    public static void main(String[] args) {
    }
}
