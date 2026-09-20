public class main {
    static void mergeSort(int[] a, int l, int r){
        if (l >= r) return;
        int m=(l+r)/2;
        mergeSort(a,l,m); mergeSort(a,m+1,r);;

        int[] b=new int[r-l+1];;
        
    }
}
