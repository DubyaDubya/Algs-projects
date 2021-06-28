/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
    private final Digraph G;

    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("null digraph");
        this.G = G;

    }

    public int length(int v, int w) {
        BreadthFirstDirectedPaths gv = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths gw = new BreadthFirstDirectedPaths(G, w);
        int vertexMax = G.V();
        int minLength = -1;
        for (int i = 0; i < vertexMax; i++) {
            if (gv.hasPathTo(i) && gw.hasPathTo(i)) {
                int dist = gv.distTo(i) + gw.distTo(i);
                if (dist < minLength || minLength == -1)
                    minLength = dist;
            }
        }
        return minLength;

    }

    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths gv = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths gw = new BreadthFirstDirectedPaths(G, w);
        int vertexMax = G.V();
        int minLength = Integer.MAX_VALUE;
        int anc = -1;
        for (int i = 0; i < vertexMax; i++) {
            if (gv.hasPathTo(i) && gw.hasPathTo(i)) {
                int dist = gv.distTo(i) + gw.distTo(i);
                if (dist < minLength) {
                    minLength = dist;
                    anc = i;
                }
            }
        }
        return anc;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths gv = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths gw = new BreadthFirstDirectedPaths(G, w);
        int vertexMax = G.V();
        int minLength = -1;
        for (int i = 0; i < vertexMax; i++) {
            if (gv.hasPathTo(i) && gw.hasPathTo(i)) {
                int dist = gv.distTo(i) + gw.distTo(i);
                if (dist < minLength || minLength == -1)
                    minLength = dist;
            }
        }
        return minLength;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths gv = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths gw = new BreadthFirstDirectedPaths(G, w);
        int vertexMax = G.V();
        int minLength = Integer.MAX_VALUE;
        int anc = -1;
        for (int i = 0; i < vertexMax; i++) {
            if (gv.hasPathTo(i) && gw.hasPathTo(i)) {
                int dist = gv.distTo(i) + gw.distTo(i);
                if (dist < minLength) {
                    minLength = dist;
                    anc = i;
                }
            }
        }
        return anc;
    }
}
