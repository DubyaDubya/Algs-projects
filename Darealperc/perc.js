class Percolation {
    constructor(dimension){
        this.grid = [];
        this.top = "top";
        this.openSites = 0;
        this.perc = false;
        this.closedSites = new Set();
        for (let y = 0; y < dimension; y++){
            this.grid.push([]);
            for (let x = 0; x< dimension; x++){
                
                this.grid[y].push(undefined)
                this.closedSites.add(`${x}${y}`);
            }
        }
    }
    
    open(x, y){
        //first delete from the list of closed boxes
        delete this.closedSites(`${x}${y}`);
        //if box is top row, set its root to top
        if(y == 0){
            this.grid[y][x] = this.top;
        }
        // otherwise, check if adjacent boxes are open
        //if so, assign their roots to eachother.
        
        else {
            this.grid[y][x] = [x,y];
            //look to left
            //if x is 0, do nothing
            if(x > 0 && this.isOpen(x-1,y)) {
                if(this.root(x-1,y) == "top"){
                    this.grid[y][x] = "top"
                } else{
                    this.grid[this.root(x-1,y)[1]][
                        this.root(x-1,y)[0]] = this.grid[y][x];
                }
            }
            //look to right
            //if x is grid[y].length -1, do nothing
            if(x < this.grid[y].length -1 && this.isOpen(x+1,y)) {
                if(this.root(x+1,y) == "top"){
                    this.grid[y][x] = "top"
                } else{
                    this.grid[this.root(x+1,y)[1]][
                        this.root(x+1,y)[0]] = this.grid[y][x];
                }
            }
            // look to bottom
            //if y is max, do nothing
            if(y < this.grid.length-1  && this.isOpen(x,y+1)) {
                if(this.root(x,y+1) == "top"){
                    this.grid[y][x] = "top"
                } else{
                    this.grid[this.root(x,y+1)[1]][
                        this.root(x,y+1)[0]] = this.grid[y][x];
                    
                }
            }
            // look to top
            // if y is max, do nothing
            if(y > 0  && this.isOpen(x,y-1)) {
                if(this.root(x,y-1) == "top"){
                    this.grid[y][x] = "top";
                } else{
                    this.grid[this.root(x,y-1)[1]][
                        this.root(x,y-1)[0]] = this.grid[y][x];
                }
            }
        }
        //check to see if it percolates
        if (y == this.grid.length -1 && this.grid[y][x] == "top"){
            this.perc = true;
        }
        this.openSites++;
    }
    isOpen(x, y){
        console.log(this.grid[y]);
        return !(this.grid[y][x] === undefined);
    }
    get numberOfOpenSites(){
        return this.openSites;
    }
    root(x,y){
        if(this.grid[y][x] === undefined){
            console.log("only use root on open sites");
            return undefined;
        }
        while((this.grid[y][x] != "top")&&
        (this.grid[y][x][0] != x &&
            this.grid[y][x][1] !=y)){
                let idX = this.grid[y][x][0];
                let idY = this.grid[y][x][1];
                this.grid[y][x] = this.grid[idY][idX];
                x =idX;
                y = idY;
                
            }
        return this.grid[y][x];
    }

    get percolates(){
        return this.perc;
    }
    // Method below is just for testing, is n2
    /*visi(){
        return this.grid.map(row => row.map(item => {
            if (item ==undefined) return undefined;
            return item.toString();
        }))
    }
    */


}
export {Percolation};
