import java.util.*;

public class RabinKarp {
    private final String str;
    private HashMap<Integer, Integer> subMap;
    private int mapSublen = 0;
    
    private void generateHashMap () {
        HashMap<Integer, Integer> map = new HashMap<>();
        
        int hash = substrHashCode(str.substring(0, mapSublen));
        map.put(hash, 0);

        final int numCodes = str.length() - mapSublen + 1,
                  maxMult  = (int) Math.pow(256, (mapSublen - 1));
        for (int i = 1; i < numCodes; i++) {
            hash = (hash - ((int) str.charAt(i - 1)) * maxMult) * 256 + ((int) str.charAt(i + mapSublen - 1));
            map.put(hash, i);
        }

        subMap = map;
    }

    private int substrHashCode (String substr) {
        int hash = 0;
        for (int i = 0; i < substr.length(); i++) hash += ((int) substr.charAt(i)) * (Math.pow(256, (substr.length() - i - 1)));
        return hash;
    }

    public RabinKarp (String str) {
        this.str = str;
    }

    public RabinKarp (String str, int sublen) {
        this.str = str;
        setSublen(sublen);
    }

    public int getSublen () {
        return mapSublen;
    }

    public void setSublen (int sublen) {
        if (sublen < 1) throw new IllegalArgumentException("Substring length must be >= 1");
        mapSublen = sublen;
        generateHashMap();
    }

    public boolean contains (String substr) {
        if (substr.length() == mapSublen || mapSublen == 0) return positionAny(substr) != -1 ? true : false;
        throw new IllegalArgumentException(String.format(".contains requires substrings of currently mapped length (%d chars)", mapSublen));
    }

    public boolean containsAny (String substr) {
        return positionAny(substr) != -1 ? true : false;
    }

    public int position (String substr) {
        if (substr.length() == mapSublen || mapSublen == 0) return positionAny(substr);
        throw new IllegalArgumentException(String.format(".position requires substrings of currently mapped length (%d chars)", mapSublen));
    }

    public int positionAny (String substr) {
        if (substr.equals("")) throw new IllegalArgumentException("Empty string passed as argument");

        final int sublen = substr.length();
        if (sublen > str.length()) throw new IllegalArgumentException("Substring longer than search string");
        if (sublen != mapSublen) setSublen(sublen);
        
        final int subCode = substrHashCode(substr);
        if (!subMap.containsKey(substrHashCode(substr))) return -1;

        final int index = subMap.get(subCode);
        return str.substring(index, index + sublen).equals(substr) ? index : -1;
    }

    public static void main (String[] args) {
        RabinKarp rk = new RabinKarp("doe are hearing me");
        System.out.println(rk.contains("ear"));
        System.out.println(rk.contains(" me"));
        System.out.println(rk.positionAny("doe are"));
    }
}