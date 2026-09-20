import java.util.Random;
public class Partition{
    private static final Random random=new Random(25);
    public static int[] split(int[] a,int left,int right,Metrics m){
        int pivot=a[left+random.nextInt(right-left+1)];
        int low=left,i=left,high=right;
        while(i<=high){
            m.comparisons++;
            if(a[i]<pivot)swap(a,i++,low++);
            else{
                m.comparisons++;
                if(a[i]>pivot)swap(a,i,high--);
                else i++;
            }
        }
        return new int[]{low,high};
    }
    private static void swap(int[] a,int i,int j){
        int temp=a[i];
        a[i]=a[j];
        a[j]=temp;
    }
}
