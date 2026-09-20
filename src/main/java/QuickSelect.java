public class QuickSelect{
    public static int select(int[] a,int k,Metrics m){
        if(a==null||a.length==0)throw new IllegalArgumentException("Array must not be empty");
        if(k<0||k>=a.length)throw new IllegalArgumentException("k is out of range");
        long start=System.nanoTime();
        int left=0,right=a.length-1,depth=1;
        while(left<=right){
            m.checkDepth(depth++);
            int[] equal=Partition.split(a,left,right,m);
            if(k<equal[0])right=equal[0]-1;
            else if(k>equal[1])left=equal[1]+1;
            else{
                m.timeNano=System.nanoTime()-start;
                return a[k];
            }
        }
        throw new IllegalStateException("Element was not found");
    }
}
