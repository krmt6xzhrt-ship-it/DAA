import java.util.concurrent.ThreadLocalRandom;

public class QuickSelect {
    public static int select(int[] a, int k, Metrics m) {
        if (a == null || a.length == 0) throw new IllegalArgumentException("Array must not be empty");
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k is out of range");

        int lo = 0;
        int hi = a.length - 1;
        m.updateDepth(1);

        while (lo <= hi) {
            int pivot = a[ThreadLocalRandom.current().nextInt(lo, hi + 1)];
            int lt = lo, i = lo, gt = hi;
            while (i <= gt) {
                m.addComparison();
                if (a[i] < pivot) {
                    int x = a[i]; a[i] = a[lt]; a[lt] = x;
                    lt++; i++;
                } else {
                    m.addComparison();
                    if (a[i] > pivot) {
                        int x = a[i]; a[i] = a[gt]; a[gt] = x;
                        gt--;
                    } else i++;
                }
            }
            if (k < lt) hi = lt - 1;
            else if (k > gt) lo = gt + 1;
            else return a[k];
        }
        throw new IllegalStateException("Selection failed");
    }
}

