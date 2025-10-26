package io;

import graph.Edge;
import graph.Graph;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class JSONReader {
    public static List<Graph> loadGraphs(String path) {
        try {
            String txt = Files.readString(Path.of(path));
            JSONObject obj = new JSONObject(txt);
            JSONArray arr = obj.getJSONArray("graphs");
            List<Graph> out = new ArrayList<>();
            for (int i=0;i<arr.length();i++){
                JSONObject g = arr.getJSONObject(i);
                JSONArray nodes = g.getJSONArray("nodes");
                JSONArray edges = g.getJSONArray("edges");
                List<String> vs = new ArrayList<>();
                for (int k=0;k<nodes.length();k++) vs.add(nodes.getString(k));
                List<Edge> es = new ArrayList<>();
                for (int k=0;k<edges.length();k++){
                    JSONObject e = edges.getJSONObject(k);
                    es.add(new Edge(e.getString("from"), e.getString("to"), e.getInt("weight")));
                }
                out.add(new Graph(vs, es));
            }
            return out;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}