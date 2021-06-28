/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordNet) {
        this.wordNet = wordNet;
    }

    public String outcast(String[] nouns) {
        int[] distances = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++) {
            for (int j = i; j < nouns.length; j++) {
                int d = wordNet.distance(nouns[i], nouns[j]);
                distances[i] += d;
                distances[j] += d;
            }
        }
        int outcast = 0;
        for (int i = 0; i < nouns.length; i++) {
            if (distances[i] > distances[outcast]) outcast = i;
        }
        return nouns[outcast];
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
