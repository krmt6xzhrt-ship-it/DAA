public class Metrics{
    public long comparisons;
    public int maxDepth;
    public long timeNano;

    public void checkDepth(int depth){
        if(depth>maxDepth)maxDepth=depth;
    }
    public double getTimeMs(){
        return timeNano/1000000.0;
    }
}
