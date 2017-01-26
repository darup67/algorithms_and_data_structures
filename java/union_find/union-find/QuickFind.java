public class QuickFind
{
    private int[] id;
    
    public QuickFind(int N)
    {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }
    
    public boolean connected (int p, int q)
    {  return id[p] == id[q];  }
    
    public void union (int p, int q)
    {
        int pid = id[p];
        for (int i = 0; i < id.length; i++)
        {
            if (id[i] == pid) id[i] = id[q];
        }
    }
    
    public static void main(String[] args)
    {
        int N = StdIn.readInt();
        QuickFind qf = new QuickFind(N);

        while (!StdIn.isEmpty())
        {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            
            if (!qf.connected(p, q))
            {
                qf.union(p, q);
                StdOut.println(p + " " + q);
            }
        }
    }
}
