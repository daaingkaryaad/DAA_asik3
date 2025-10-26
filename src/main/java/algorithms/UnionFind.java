package algorithms;

public class UnionFind {
    private final int[] p, r;
    public UnionFind(int n){ p=new int[n]; r=new int[n]; for(int i=0;i<n;i++) p[i]=i; }
    public int find(int x){ return p[x]==x?x:(p[x]=find(p[x])); }
    public boolean union(int a,int b){
        a=find(a); b=find(b); if (a==b) return false;
        if (r[a]<r[b]) p[a]=b; else if (r[a]>r[b]) p[b]=a; else { p[b]=a; r[a]++; }
        return true;
    }
}