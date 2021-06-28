public class Board{
    private int[][] tiles;
    private int zeroRow;
    private int zeroCol;
    public Board(int[][] tiles){
        this.tiles = tiles;
        for(int i = 0; i < tiles.length; i++){
            for (int j = 0; j<tiles[i].length; j++){
                if (tiles[i][j] ==0){
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(tiles.length);
        s.append("\n");
        for (int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){
                s.append(tiles[i][j]);
            }
            s.append("\n");
        }
        return s.toString();
    }

    public int hamming(){
        int ham = 0;
        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles[i].length; j++){
                if(i == tiles.length-1 && j == tiles[i].length -1){

                }
                else if (tiles[i][j] != i*tiles[i].length + j + 1) ham++;
            }
        }
        return ham;
    }

    public int manhattan(){
        int m = 0;
        for (int i = 0; i < tiles.length; i++){
            for(int j = 0; j< tiles[i].length; j++){
                int val = tiles[i][j];
                int valy;
                int valx;
                if (val == 0){
                    valy = tiles[i].length;
                    valx = tiles.length;
                }else{
                    valy = (val -1)/tiles[i].length;
                    valx = (val -1) % tiles[i].length;
                }
                m += Math.abs(i - valy) + Math.abs(j -valx);
            }
        }
        return m;
    }

    public boolean isGoal(){
        return this.hamming() == 0;
    }
    
    @Override
    public boolean equals(Object a){
        if (a == null) return false;
        if (a.getClass() != this.getClass()) return false;
        Board y = (Board) a;
        if (this.tiles.length != y.tiles.length) return false;
        else if (this.tiles[0].length != y.tiles[0].length) return false;
        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles.length; j++){
                if (this.tiles[i][j] != y.tiles[i][j]) return false;
            }
        }
        return true;
    }
    
    private static void exch(int[][] a, int i, int j,int i2, int j2){
        int temp = a[i][j];
        a[i][j] = a[i2][j2];
        a[i2][j2] = temp;
    }
    public Board alt(){
        int[][] newT = new int[tiles.length][];
        for (int i = 0; i< tiles.length; i++) newT[i] = tiles[i].clone();
        exch(newT,0,0,1,0);
        return new Board(newT);
    }

    public Bag<Board> neighbors(){
        Bag<Board> neibs = new Bag<>();
        if(zeroRow > 0 ){
            int[][] newT = new int[tiles.length][];
            for (int i = 0; i< tiles.length; i++) newT[i] = tiles[i].clone();
            exch(newT,zeroRow,zeroCol,zeroRow-1,zeroCol);
            neibs.add(new Board(newT));
        }
        if(zeroRow < tiles.length-1 ){
            int[][] newT = new int[tiles.length][];
            for (int i = 0; i< tiles.length; i++) newT[i] = tiles[i].clone();
            exch(newT,zeroRow,zeroCol,zeroRow+1,zeroCol);
            neibs.add(new Board(newT));
        }
        if(zeroCol > 0 ){
            int[][] newT = new int[tiles.length][];
            for (int i = 0; i< tiles.length; i++) newT[i] = tiles[i].clone();
            exch(newT,zeroRow,zeroCol,zeroRow,zeroCol-1);
            neibs.add(new Board(newT));
        }
        if(zeroCol < tiles[0].length -1 ){
            int[][] newT = new int[tiles.length][];
            for (int i = 0; i< tiles.length; i++) newT[i] = tiles[i].clone();
            exch(newT,zeroRow,zeroCol,zeroRow,zeroCol+1);
            neibs.add(new Board(newT));
        }
        return neibs;
    }

    public static void main(String[] args){
        int[][] tiles = {{1,4,3}, {6,5,8},{2,7,0}};
        Board ay = new Board(tiles);
        Bag<Board> n = ay.neighbors();
        for (Board b : n){
            System.out.println(b);
        }
    }
}