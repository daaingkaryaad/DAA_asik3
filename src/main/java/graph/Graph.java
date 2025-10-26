package graph;

import java.util.*;

public class Graph {
    public final List<String> nodes = new ArrayList<>();
    public final List<Edge> edges = new ArrayList<>();
    private final Map<String,List<Edge>> adj = new HashMap<>();

    public Graph(Collection<String> vs, Collection<Edge> es){
        nodes.addAll(vs);
        edges.addAll(es);
        for (String v: nodes) adj.put(v, new ArrayList<>());
        for (Edge e: edges){
            adj.get(e.u).add(e);
            adj.get(e.v).add(e);
        }
    }
    public List<Edge> adj(String v){ return adj.getOrDefault(v, List.of()); }
    public int V(){ return nodes.size(); }
    public int E(){ return edges.size(); }
}