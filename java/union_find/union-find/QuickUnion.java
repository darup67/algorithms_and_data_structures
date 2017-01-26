public class QuickUnion
{
    private int[] id;
    
    private int root(int i)
    {
        while (i != id[i]) i = id[i];
        return i;
    }
    
    public QuickUnion(int N)
    {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }
    
    public boolean connected (int p, int q)
    {  return root(p) == root(q);  }
    
    public void union (int p, int q)
    {
        id[root(p)] = root(q);
    }
    
    public static void main(String[] args)
    {
        int N = StdIn.readInt();
        QuickUnion qu = new QuickUnion(N);

        while (!StdIn.isEmpty())
        {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            
            if (!qu.connected(p, q))
            {
                qu.union(p, q);
                StdOut.println(p + " " + q);
            }
        }
    }
}
