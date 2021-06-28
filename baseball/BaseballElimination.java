/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BaseballElimination {
    private int numTeams;
    private HashMap<String, Integer> teams;
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;
    private String[] namesByInd;
    private int best;
    private Bag<String> inTheCut;

    public BaseballElimination(String filename) {
        In teamsIn = new In(filename);
        String temp = teamsIn.readLine();
        numTeams = Integer.parseInt(temp);
        teams = new HashMap<>();
        w = new int[numTeams];
        l = new int[numTeams];
        r = new int[numTeams];
        g = new int[numTeams][numTeams];
        namesByInd = new String[numTeams];
        int i = 0;
        best = 0;
        while (!teamsIn.isEmpty()) {
            String line = teamsIn.readLine();
            Matcher m = Pattern.compile("\\S+").matcher(line);
            String[] tokens = line.split("\\s+");
            m.find();
            String teamName = m.group();
            teams.put(teamName, i);
            namesByInd[i] = teamName;
            m.find();
            w[i] = Integer.parseInt(m.group());
            if (w[i] > w[best]) best = i;
            m.find();
            l[i] = Integer.parseInt(m.group());
            m.find();
            r[i] = Integer.parseInt(m.group());
            for (int j = 0; m.find(); j++) {
                g[i][j] = Integer.parseInt(m.group());
            }
            i++;
        }
    }

    public int numberOfTeams() {
        return numTeams;
    }

    public Iterable<String> teams() {
        return teams.keySet();
    }

    public int wins(String team) {
        checkTeam(team);
        return w[teams.get(team)];
    }

    public int losses(String team) {
        checkTeam(team);
        return l[teams.get(team)];
    }

    public int remaining(String team) {
        checkTeam(team);
        return r[teams.get(team)];
    }

    public int against(String team1, String team2) {
        checkTeam(team1);
        checkTeam(team2);
        return g[teams.get(team1)][teams.get(team2)];
    }

    public boolean isEliminated(String team) {
        //Trivial elimination
        int thisTeam = teams.get(team);
        if (w[thisTeam] + r[thisTeam] < w[best]) {
            inTheCut = new Bag<>();
            for (int i = 0; i < w.length; i++) {
                if (w[thisTeam] + r[thisTeam] < w[i])
                    inTheCut.add(namesByInd[i]);
            }
            return true;
        }

        buildFF(team);
        return (!inTheCut.isEmpty());

    }

    public Iterable<String> certificateOfElimination(String team) {
        if (!isEliminated(team)) return null;
        return inTheCut;
    }

    private void buildFF(String team) {
        checkTeam(team);
        int thisTeam = teams.get(team);
        inTheCut = new Bag<>();

        /*
        first calculate number of vertices necessary. you need s, t (2)
        also you need one for each team not #team (numTeams -1) and one
        for each matchup not against #team. for each team this is numTeams-2.
        but you divide this by 2 in order to keep from counting each team twice
        you then need another numTeams -1 vertices for every team that
        is not # team. so the total is 2 + (numTeams -1)(numTeams -2)/2 +
        numTeams - 1
         */
        int numV = 2 + (numTeams - 1) * (numTeams - 2) / 2 + numTeams - 1;
        FlowNetwork n = new FlowNetwork(numV);
        int v = 1;

        //build FlowEdges from source to matchups
        for (int i = 0; i < g.length; i++) {
            if (i == thisTeam) continue;
            for (int j = i + 1; j < g.length; j++) {
                if (j == thisTeam) continue;
                n.addEdge(new FlowEdge(0, v, g[i][j]));
                v++;
            }
        }

        //build FlowEdges from matchups to teams
        int u = v;
        v = 1;
        for (int team1 = 0; team1 < numTeams - 1; team1++) {
            for (int team2 = team1 + 1; team2 < numTeams - 1; team2++) {
                n.addEdge(new FlowEdge(v, u + team1, Integer.MAX_VALUE));
                n.addEdge(new FlowEdge(v, u + team2, Integer.MAX_VALUE));
                v++;
            }
        }

        //build FlowEdges from teams to sink


        while (u < numV - 1) {
            //build a capacity based on amount more wins team can get
            if (u - v < thisTeam) {
                n.addEdge(new FlowEdge(u, numV - 1,
                                       w[thisTeam] + r[thisTeam]
                                               - w[u - v]));
            }
            else {
                n.addEdge(new FlowEdge(u, numV - 1,
                                       w[thisTeam] + r[thisTeam]
                                               - w[(u - v) + 1]));
            }
            u++;
        }


        // do ford fulkerson simulation
        FordFulkerson ff = new FordFulkerson(n, 0, numV - 1);
        int i = 0;
        while (v < numV - 1) {
            if (i == thisTeam) i++;
            if (ff.inCut(v)) {
                inTheCut.add(namesByInd[i]);
            }
            v++;
            i++;
        }
    }

    private void checkTeam(String a) {
        if (!teams.containsKey(a)) throw new IllegalArgumentException(
                "invalid team");
    }


    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }

}
