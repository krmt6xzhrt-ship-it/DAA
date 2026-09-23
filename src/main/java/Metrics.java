public class Metrics {
    private long comparisons;
    private int maxDepth;
    private long start;
    private long end;

    public void reset() {
        comparisons=0;
        maxDepth=0;
        start=0;
        end=0;
    }

    public void startTimer() {
        start = System.nanoTime();
    }
    public void stopTimer() {
        end = System.nanoTime();
    }
    public void addComparison() {
        comparisons++;
    }
    public void updateDepth(int depth) {
        if (depth > maxDepth) maxDepth = depth;
    }
    public long getComparisons() {
        return comparisons;
    }
    public int getMaxDepth() {
        return maxDepth;
    }
    public double getTimeMs() {
        return (end - start) / 1_000_000.0;
    }
}
