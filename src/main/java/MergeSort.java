public class MergeSort{
    private static final int CUTOFF=15;

    public static void sort(int[] a,Metrics m){
        long start=System.nanoTime();
        if(a!=null&&a.length>1){
            int[] temp=new int[a.length];
            sort(a,temp,0,a.length-1,m,1);
        }
        m.timeNano=System.nanoTime()-start;
    }
    private static void sort(int[] a,int[] temp,int left,int right,Metrics m,int depth){
        m.checkDepth(depth);
        if(right-left+1<=CUTOFF){
            insertion(a,left,right,m);
            return;
        }
        int mid=(left+right)/2;
        sort(a,temp,left,mid,m,depth+1);
        sort(a,temp,mid+1,right,m,depth+1);
        merge(a,temp,left,mid,right,m);
    }
    private static void insertion(int[] a,int left,int right,Metrics m){
        for(int i=left+1;i<=right;i++){
            int value=a[i];
            int j=i-1;
            while(j>=left){
                m.comparisons++;
                if(a[j]<=value)break;
                a[j+1]=a[j--];
            }
            a[j+1]=value;
        }
    }
    private static void merge(int[] a,int[] temp,int left,int mid,int right,Metrics m){
        int i=left,j=mid+1,k=left;
        while(i<=mid&&j<=right){
            m.comparisons++;
            if(a[i]<=a[j])temp[k++]=a[i++];
            else temp[k++]=a[j++];
        }
        while(i<=mid)temp[k++]=a[i++];
        while(j<=right)temp[k++]=a[j++];
        for(i=left;i<=right;i++)a[i]=temp[i];
    }
}