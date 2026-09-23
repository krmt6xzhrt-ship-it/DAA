import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
public class AlgorithmsTest{
    private final Random random=new Random(25);
    @Test
    void sortsMatchArraysSort(){
        for(int test=0;test<100;test++){
            int[] a=randomArray(random.nextInt(500));
            int[] expected=a.clone();
            Arrays.sort(expected);
            int[] merge=a.clone(),quick=a.clone();
            MergeSort.sort(merge,new Metrics());
            QuickSort.sort(quick,new Metrics());
            assertArrayEquals(expected,merge);
            assertArrayEquals(expected,quick);
        }
    }
    @Test
    void edgeCases(){
        check(new int[]{});
        check(new int[]{5});
        check(new int[]{4,4,4,4,4});
        check(new int[]{1,2,3,4,5});
    }
    @Test
    void quickSortDepth(){
        int n=100000;
        int[] a=new int[n];
        for(int i=0;i<n;i++)a[i]=i;
        Metrics m=new Metrics();
        QuickSort.sort(a,m);
        assertTrue(m.maxDepth<=2*Math.log(n)/Math.log(2));
    }

    @Test
    void quickSelectMatchesSortedArray(){
        for(int test=0;test<100;test++){
            int[] a=randomArray(random.nextInt(500)+1);
            int[] sorted=a.clone();
            Arrays.sort(sorted);
            int k=random.nextInt(a.length);
            assertEquals(sorted[k],QuickSelect.select(a,k,new Metrics()));
        }
    }

    @Test
    void invalidSelect(){
        assertThrows(IllegalArgumentException.class,()->QuickSelect.select(new int[]{},0,new Metrics()));
        assertThrows(IllegalArgumentException.class,()->QuickSelect.select(new int[]{1,2},3,new Metrics()));
    }

    private void check(int[] a){
        int[] expected=a.clone();Arrays.sort(expected);
        int[] m=a.clone(),q=a.clone();
        MergeSort.sort(m,new Metrics());QuickSort.sort(q,new Metrics());
        assertArrayEquals(expected,m);assertArrayEquals(expected,q);
    }

    private int[] randomArray(int n){
        int[] a=new int[n];
        for(int i=0;i<n;i++)a[i]=random.nextInt(10000)-5000;
        return a;
    }
}