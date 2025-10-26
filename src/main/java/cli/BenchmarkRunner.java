package cli;

import algorithms.Kruskal;
import algorithms.Prim;
import graph.Edge;
import graph.Graph;
import io.JSONReader;
import io.JSONWriter;
import metrics.Metrics;

import java.util.*;

public class BenchmarkRunner {
    public static void main(String[] args) {
        String inFile  = (args.length>0)? args[0] : "src/main/resources/assign3_input.json";
        String outFile = (args.length>1)? args[1] : "output.json";
        String csvFile = (args.length>2)? args[2] : "results.csv";

        var graphs = JSONReader.loadGraphs(inFile);
        var primEdges = new ArrayList<List<Edge>>();
        var primCost  = new ArrayList<Integer>();
        var primOps   = new ArrayList<Long>();
        var primMs    = new ArrayList<Double>();

        var kruEdges = new ArrayList<List<Edge>>();
        var kruCost  = new ArrayList<Integer>();
        var kruOps   = new ArrayList<Long>();
        var kruMs    = new ArrayList<Double>();

        Prim prim = new Prim();
        Kruskal kru = new Kruskal();

        Metrics.writeCsvHeader(csvFile);

        for (int i=0;i<graphs.size();i++){
            Graph g = graphs.get(i);
            // Prim
            Metrics mp = new Metrics();
            mp.startTimer();
            var pr = prim.run(g, mp);
            mp.stopTimer();
            primEdges.add(pr.mst); primCost.add(pr.totalCost); primOps.add(mp.ops()); primMs.add(mp.ms());
            mp.appendCsv(csvFile, i+1, g.V(), g.E(), "Prim", pr.totalCost);

            // Kruskal
            Metrics mk = new Metrics();
            mk.startTimer();
            var kr = kru.run(g, mk);
            mk.stopTimer();
            kruEdges.add(kr.mst); kruCost.add(kr.totalCost); kruOps.add(mk.ops()); kruMs.add(mk.ms());
            mk.appendCsv(csvFile, i+1, g.V(), g.E(), "Kruskal", kr.totalCost);

            // sanity: costs must match if graph is connected
            if (g.V()>0 && pr.mst.size()==g.V()-1 && kr.mst.size()==g.V()-1 && pr.totalCost!=kr.totalCost){
                System.err.println("WARNING: cost mismatch at graph "+(i+1));
            }
        }

        JSONWriter.writeResult(outFile, graphs, primEdges, primCost, primOps, primMs, kruEdges, kruCost, kruOps, kruMs);
        System.out.println("Done. Wrote: "+outFile+" and "+csvFile);
    }
}