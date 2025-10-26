package metrics;

import java.io.FileWriter;
import java.io.IOException;

public class Metrics {
    private long ops=0;
    private long startNs, elapsedNs;

    public void op(){ ops++; }
    public void addOps(long k){ ops+=k; }
    public long ops(){ return ops; }

    public void startTimer(){ startNs=System.nanoTime(); }
    public void stopTimer(){ elapsedNs = System.nanoTime()-startNs; }
    public double ms(){ return elapsedNs/1_000_000.0; }

    public static void writeCsvHeader(String file){
        try(FileWriter fw = new FileWriter(file,false)){
            fw.write("graphId,algo,vertices,edges,totalCost,ops,timeMs\n");
        }catch(IOException e){ e.printStackTrace(); }
    }
    public void appendCsv(String file,int gid,int V,int E,String algo,int cost){
        try(FileWriter fw = new FileWriter(file,true)){
            fw.write(gid+","+algo+","+V+","+E+","+cost+","+ops+","+String.format("%.3f",ms())+"\n");
        }catch(IOException e){ e.printStackTrace(); }
    }
}