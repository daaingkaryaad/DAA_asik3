package cli;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class GraphGenerator {
    private static List<String> labels(int n){
        List<String> L = new ArrayList<>();
        for (int i=0;i<n;i++){
            if (i<26) L.add(""+(char)('A'+i));
            else L.add(""+(char)('A'+(i/26-1)) + (char)('A'+(i%26)));
        }
        return L;
    }
    private static JSONObject makeGraph(int id, int n, double density, Random rnd){
        List<String> nodes = labels(n);
        List<int[]> edges = new ArrayList<>();

        // random spanning tree (connectivity)
        List<Integer> order = new ArrayList<>();
        for (int i=0;i<n;i++) order.add(i);
        Collections.shuffle(order, rnd);
        for (int i=1;i<n;i++){
            int a = order.get(i), b = order.get(rnd.nextInt(i));
            if (a>b){ int t=a;a=b;b=t; }
            edges.add(new int[]{a,b, 1+rnd.nextInt(20)});
        }
        // extra edges by probability
        for (int i=0;i<n;i++) for (int j=i+1;j<n;j++){
            if (rnd.nextDouble()<density){
                final int ai=i, aj=j;
                boolean dup = edges.stream().anyMatch(e -> e[0]==ai && e[1]==aj);
                if (!dup) edges.add(new int[]{i,j, 1+rnd.nextInt(20)});
            }
        }

        JSONArray earr = new JSONArray();
        for (int[] e: edges)
            earr.put(new JSONObject().put("from", nodes.get(e[0])).put("to", nodes.get(e[1])).put("weight", e[2]));
        return new JSONObject().put("id", id).put("nodes", new JSONArray(nodes)).put("edges", earr);
    }

    public static void main(String[] args){
        String out = (args.length>0)? args[0] : "src/main/resources/assign3_input.json";
        Random rnd = new Random(42);

        JSONArray graphs = new JSONArray();
        int id=1;

        // Small (5): 4–6 vertices
        for (int i=0;i<5;i++) graphs.put(makeGraph(id++, 4+rnd.nextInt(3), 0.5, rnd));
        // Medium (10): 10–15
        for (int i=0;i<10;i++) graphs.put(makeGraph(id++, 10+rnd.nextInt(6), 0.35, rnd));
        // Large (10): 20–30
        for (int i=0;i<10;i++) graphs.put(makeGraph(id++, 20+rnd.nextInt(11), 0.25, rnd));
        // Extra (5): 40–60 (still reasonable for classroom runs)
        for (int i=0;i<5;i++) graphs.put(makeGraph(id++, 40+rnd.nextInt(21), 0.15, rnd));

        JSONObject root = new JSONObject().put("graphs", graphs);
        try {
            Files.createDirectories(Path.of("src/main/resources"));
            Files.writeString(Path.of(out), root.toString(2));
            System.out.println("Generated 30 graphs -> " + out);
        } catch (IOException e){ e.printStackTrace(); }
    }
}