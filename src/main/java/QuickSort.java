import java.util.concurrent.ThreadLocalRandom;

public class QuickSort {
    public static void sort(int[] a, Metrics m) {
        if (a==null) throw new IllegalArgumentException("Array must not be null");
        if (a.length <2) {
            if (a.length ==1) m.updateDepth(1);
            return;
        }
        sort(a, 0, a.length - 1, 1, m);
    }

    private static void sort(int[] a, int lo, int hi, int depth, Metrics m) {
        while (lo < hi) {
            m.updateDepth(depth);
            int pivot =a[ThreadLocalRandom.current().nextInt(lo, hi +1)];
            int lt=lo, i=lo, gt= hi;
            while (i<=gt) {
                m.addComparison();
                if (a[i]<pivot) {
                    swap(a,lt++, i++);
                } else {
                    m.addComparison();
                    if (a[i] >pivot) swap(a, i, gt--);
                    else i++;
                }
            }
            int leftSize =lt - lo;
            int rightSize =hi- gt;

            if (leftSize <rightSize) {
                if (lo <lt -1) sort(a,lo,lt -1,depth + 1, m);
                lo =gt+1;
            } else {
                if (gt +1 < hi) sort(a,gt +1, hi, depth+1,m);
                hi= lt - 1;
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t=a[i];
        a[i]=a[j];
        a[j]=t;
    }
}

