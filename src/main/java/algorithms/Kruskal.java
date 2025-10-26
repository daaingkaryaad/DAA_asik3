package algorithms;

import graph.Edge;
import graph.Graph;
import metrics.Metrics;

import java.util.*;

public class Kruskal {
    public static class Result {
        public final List<Edge> mst = new ArrayList<>();
        public int totalCost;
    }

    public Result run(Graph g, Metrics m){
        Result res = new Result();
        Map<String,Integer> id = new HashMap<>();
        for (int i=0;i<g.nodes.size();i++) id.put(g.nodes.get(i), i);
        UnionFind uf = new UnionFind(g.V());

        List<Edge> sorted = new ArrayList<>(g.edges);
        sorted.sort(Comparator.naturalOrder());

        for (Edge e: sorted){
            m.op(); // compare/consider
            int a = id.get(e.u), b = id.get(e.v);
            if (uf.union(a,b)){
                res.mst.add(e);
                res.totalCost += e.w;
                if (res.mst.size()==g.V()-1) break;
            }
        }
        return res;
    }
}