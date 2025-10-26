package algorithms;

import graph.Edge;
import graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrimKruskalTest {
    @Test
    void mst_costs_match_on_connected_graph() {
        var nodes = List.of("A","B","C","D","E");
        var edges = List.of(
                new Edge("A","B",4), new Edge("A","C",3), new Edge("B","C",2),
                new Edge("B","D",5), new Edge("C","D",7), new Edge("C","E",8), new Edge("D","E",6)
        );
        Graph g = new Graph(nodes, edges);
        Prim prim = new Prim(); Kruskal kru = new Kruskal();
        var mp = new metrics.Metrics(); var mk = new metrics.Metrics();
        var pr = prim.run(g, mp); var kr = kru.run(g, mk);
        assertEquals(g.V()-1, pr.mst.size());
        assertEquals(g.V()-1, kr.mst.size());
        assertEquals(pr.totalCost, kr.totalCost);
        assertTrue(mp.ms() >= 0 && mk.ms() >= 0);
    }
}