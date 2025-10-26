package algorithms;

import graph.Edge;
import graph.Graph;
import metrics.Metrics;

import java.util.*;

public class Prim {
    public static class Result {
        public final List<Edge> mst = new ArrayList<>();
        public int totalCost;
    }

    public Result run(Graph g, Metrics m){
        Result res = new Result();
        if (g.V()==0) return res;

        Set<String> in = new HashSet<>();
        String start = g.nodes.get(0);
        in.add(start);

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.addAll(g.adj(start));

        while(!pq.isEmpty() && res.mst.size()<g.V()-1){
            Edge e = pq.poll(); m.op();
            String a = in.contains(e.u)? e.u : e.v;
            String b = a.equals(e.u)? e.v : e.u;
            if (in.contains(b)) continue;
            in.add(b);
            res.mst.add(e);
            res.totalCost += e.w;
            for (Edge ne : g.adj(b)) { m.op(); // consider
                String x = ne.u.equals(b) ? ne.v : ne.u;
                if (!in.contains(x)) pq.add(ne);
            }
        }
        return res;
    }
}