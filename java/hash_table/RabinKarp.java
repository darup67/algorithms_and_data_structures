import java.util.*;

public class RabinKarp {
    private final String str;
    private final int sublen;
    private final HashMap<Integer, Integer> subMap;

    private HashMap<Integer, Integer> substrHashMap () {
        HashMap<Integer, Integer> map = new HashMap<>();
        
        int hash = substrHashCode(str.substring(0, sublen));
        map.put(hash, 0);

        final int numCodes = str.length() - sublen + 1,
                  maxMult  = (int) Math.pow(256, (sublen - 1));
        for (int i = 1; i < numCodes; i++) {
            hash = (hash - ((int) str.charAt(i - 1)) * maxMult) * 256 + ((int) str.charAt(i + sublen - 1));
            map.put(hash, i);
        }

        return map;
    }

    private int substrHashCode (String substr) {
        int hash = 0;
        for (int i = 0; i < substr.length(); i++) hash += ((int) substr.charAt(i)) * (Math.pow(256, (substr.length() - i - 1)));
        return hash;
    }

    public RabinKarp (String str, int sublen) {
        this.str = str;
        this.sublen = sublen;
        this.subMap = substrHashMap();
    }

    public boolean contains (String substr) {
        if (substr.length() != sublen) throw new IllegalArgumentException(String.format("Substring must be of length %d", sublen));
        
        final int subCode = substrHashCode(substr);
        if (!subMap.containsKey(substrHashCode(substr))) return false;

        final int index = subMap.get(subCode);
        if (str.substring(index, index + sublen).equals(substr)) return true;
        return false;
    }

    public static void main (String[] args) {
        RabinKarp rk = new RabinKarp("doe are hearing me", 3);
        System.out.println(rk.contains("ear"));
        System.out.println(rk.contains(" me"));
        System.out.println(rk.contains("heo"));
    }
}