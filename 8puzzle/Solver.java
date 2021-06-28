public class Solver {
    Node gameTree;

    public Solver(Board initial){
        Node start = new Node(initial, null, 0);
        MinPQ<Node> orig = new MinPQ<>();
        MinPQ<Node> alt = new MinPQ<>();
        orig.add(start);
        alt.add(new Node(initial.alt(), null, 0));
        while(!orig.min().getBoard().isGoal()&& !alt.min().getBoard().isGoal()){
            Node tempOrig = orig.delMin();
            Bag<Board> ob = tempOrig.getBoard().neighbors();
            for(Board a: ob){
                if(tempOrig.prev == null || !a.equals(tempOrig.prev.b)){
                    orig.add(new Node(a, tempOrig, tempOrig.moves+1));
                }
            }
            Node tempAlt = alt.delMin();
            Bag<Board> ab = tempAlt.getBoard().neighbors();
            for(Board a: ab){
                if(tempAlt.prev == null || !a.equals(tempAlt.prev.b)){
                    alt.add(new Node(a, tempAlt, tempAlt.moves+1));
                }
            }
        }
        gameTree = orig.min();
    }

    private class Node implements Comparable<Node>{
        private Node prev;
        private int moves;
        private Board b;
        private int priority;
        private Node(Board b, Node p, int moves){
            this.b = b;
            this.moves = moves;
            this.prev = p;
            priority = b.hamming() + moves;
        }
        public Board getBoard(){
            return b;
        }
        public int compareTo(Node that){
            if(this.priority< that.priority) return -1;
            else if( this.priority> that.priority) return +1;
            else return 0;
        }
    }
    public boolean isSolvable(){
        return gameTree.getBoard().isGoal();
    }


    public int moves(){
        if (isSolvable()) return gameTree.moves;
        else return -1;
    }

    public Iterable<Board> solution(){
        if(!isSolvable()) return null;
        Bag<Board> sol = new Bag<>();
        Node g = gameTree.prev;
        sol.add(gameTree.getBoard());
        while(g != null){
            sol.add(g.getBoard());
            g = g.prev;
        } 
        return sol;
    }

    public static void main(String[] args){
        int[][] t = {{1,6,3},{4,5,7},{2,0,8}};
        Board b = new Board(t);
        Solver a = new Solver(b);
        if (a.isSolvable()){
            for(Board c: a.solution()){
                System.out.print(c);
            }
        } else System.out.println(a.isSolvable());
    }
}
