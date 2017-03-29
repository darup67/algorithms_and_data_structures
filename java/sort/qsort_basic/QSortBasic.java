import java.lang.*;

public class QSortBasic {
  private static void swap (int[] arr, final int i, final int j) {
    final int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  private static void qsort (int[] arr, final int lo, final int hi) {
    if (lo >= hi) return;

    final int pIndex = ((int) (Math.random() * (hi - lo))) + lo,
              pVal   = arr[pIndex];
    int       swapPt = lo;

    swap(arr, lo, pIndex);
    for (int i = lo + 1; i <= hi; i++)
      if (arr[i] < pVal) swap(arr, i, ++swapPt);
    swap(arr, lo, swapPt);
    
    qsort(arr, lo, swapPt - 1);
    qsort(arr, swapPt + 1, hi);
  }

  public static void main(String[] args) {
    int[] testArr = {5,2,78,5,0,13,6};
    for (int i : testArr) System.out.printf("%d%c", i, ' ');
    System.out.println();

    qsort(testArr, 0, testArr.length - 1);
    for (int i : testArr) System.out.printf("%d%c", i, ' ');
  }
}