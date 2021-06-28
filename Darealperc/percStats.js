import {Percolation} from './perc.js';
class PercolationStats{
    constructor(dimension, trials){
        //because there are n trials we want to do n times
        //this.runTimes = [];
        this.siteVacancies = [];
        for (let n = 0; n < trials; n++){
            let percSim = new Percolation(dimension);
            //let t1 =Performance.now();
            
            while(!percSim.percolates){
                console.log(percSim.grid);
                let randIndex = Math.floor(Math.random()*
                percSim.closedSites.length);
                console.log(randIndex);
                console.log(percSim.closedSites[randIndex][0],
                     percSim.closedSites[randIndex][1]);
                percSim.open(percSim.closedSites[randIndex][0],
                    percSim.closedSites[randIndex][1]);
            }
            //let t2 = Performance.now();
            //this.runTimes.push(t2-t1);
            this.siteVacancies.push(percSim.openSites);
        }

    }
    
}




let tester = new PercolationStats(5,2);
console.log(tester.runtTimes);
console.log(tester.siteVacancies);




/*how to perform a percolation:
make a new percolation
start timer

randomly pick a block to open
check if system has percolated
repeat

when system has percolated, stop timer
return time, return # sites*/