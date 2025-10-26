package io;

import graph.Edge;
import graph.Graph;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JSONWriter {
    public static void writeResult(String file,
                                   List<Graph> graphs,
                                   List<List<Edge>> primEdges, List<Integer> primCost, List<Long> primOps, List<Double> primMs,
                                   List<List<Edge>> kruskalEdges, List<Integer> kruskalCost, List<Long> kruskalOps, List<Double> kruskalMs) {
        JSONArray results = new JSONArray();
        for (int i=0;i<graphs.size();i++){
            Graph g = graphs.get(i);
            JSONObject obj = new JSONObject();
            obj.put("graph_id", i+1);
            obj.put("input_stats", new JSONObject().put("vertices", g.V()).put("edges", g.E()));

            JSONArray primE = new JSONArray();
            for (Edge e: primEdges.get(i)) primE.put(new JSONObject().put("from", e.u).put("to", e.v).put("weight", e.w));
            JSONArray krusE = new JSONArray();
            for (Edge e: kruskalEdges.get(i)) krusE.put(new JSONObject().put("from", e.u).put("to", e.v).put("weight", e.w));

            obj.put("prim", new JSONObject()
                    .put("mst_edges", primE)
                    .put("total_cost", primCost.get(i))
                    .put("operations_count", primOps.get(i))
                    .put("execution_time_ms", primMs.get(i)));

            obj.put("kruskal", new JSONObject()
                    .put("mst_edges", krusE)
                    .put("total_cost", kruskalCost.get(i))
                    .put("operations_count", kruskalOps.get(i))
                    .put("execution_time_ms", kruskalMs.get(i)));

            results.put(obj);
        }
        JSONObject root = new JSONObject().put("results", results);
        try { Files.writeString(Path.of(file), root.toString(2)); }
        catch (IOException e){ throw new RuntimeException(e); }
    }
}