public class QuickSort{
    public static void sort(int[] a,Metrics m){
        long start=System.nanoTime();
        if(a!=null&&a.length>1)sort(a,0,a.length-1,m,1);
        m.timeNano=System.nanoTime()-start;
    }

    private static void sort(int[] a,int left,int right,Metrics m,int depth){
        while(left<right){
            m.checkDepth(depth);
            int[] equal=Partition.split(a,left,right,m);
            if(equal[0]-left<right-equal[1]){
                if(left<equal[0]-1)sort(a,left,equal[0]-1,m,depth+1);
                left=equal[1]+1;
            }else{
                if(equal[1]+1<right)sort(a,equal[1]+1,right,m,depth+1);
                right=equal[0]-1;
            }
        }
    }
}
