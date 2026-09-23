import java.io.PrintWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;

public class Benchmark {
    public static void main(String[] args) throws Exception {
        int[] sizes={1000,10000,100000,1000000};
        String[] types ={"random","sorted","duplicates"};
        String[] algorithms={"MergeSort","QuickSort","QuickSelect"};
        double[][][] timeData=new double[3][3][4];
        double[][][] depthData=new double[3][3][4];
        double[][][] ratioData=new double[3][3][4];
        try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(Path.of("results.csv")))) {
            out.println("algorithm,input,n,time_ms,comparisons,max_depth");
            for (int n:sizes) {
                for (String type:types) {
                    int[] source=makeArray(n, type);
                    for (String algorithm:algorithms) {
                        double[] times=new double[5];
                        long[] comparisons=new long[5];
                        int[] depths=new int[5];
                        for (int run=0;run<5; run++) {
                            int[] a=source.clone();
                            Metrics m=new Metrics();
                            m.startTimer();
                            if (algorithm.equals("MergeSort")) MergeSort.sort(a, m);
                            else if (algorithm.equals("QuickSort")) QuickSort.sort(a, m);
                            else QuickSelect.select(a, n / 2, m);
                            m.stopTimer();
                            times[run]=m.getTimeMs();
                            comparisons[run] =m.getComparisons();
                            depths[run]=m.getMaxDepth();
                        }
                        Arrays.sort(times);
                        Arrays.sort(comparisons);
                        Arrays.sort(depths);
                        double time=times[2];
                        long count=comparisons[2];
                        int depth=depths[2];
                        out.printf(java.util.Locale.US, "%s,%s,%d,%.4f,%d,%d%n", algorithm, type, n, time, count, depth);
                        int ai=Arrays.asList(algorithms).indexOf(algorithm);
                        int ti=Arrays.asList(types).indexOf(type);
                        int si=0;
                        while (sizes[si] !=n) si++;
                        timeData[ai][ti][si]=time;
                        depthData[ai][ti][si]=depth;
                        ratioData[ai][ti][si]=count/(ai ==2 ?(double)n:n *Math.log(n)/Math.log(2));
                        System.out.println(algorithm + " " + type + " " + n + " done");
                    }
                }
            }
        }
        graph(timeData,"time.png","Time (ms)",sizes,types,algorithms);
        graph(depthData,"depth.png","Max depth",sizes,types,algorithms);
        graph(ratioData,"ratio.png","Comparison ratio",sizes,types,algorithms);
    }
    private static int[] makeArray(int n,String type) {
        int[] a=new int[n];
        Random random=new Random(42+n+type.length());
        for (int i=0;i<n;i++) {
            if (type.equals("sorted")) a[i]=i;
            else if (type.equals("duplicates")) a[i]=random.nextInt(10);
            else a[i]=random.nextInt();
        }
        return a;
    }
    private static void graph(double[][][] data, String file, String title,int[] sizes,String[] types, String[] algorithms) throws Exception {
        int left=75, top=50,width=650, height=500;
        BufferedImage image=new BufferedImage(1050,650,BufferedImage.TYPE_INT_RGB);
        Graphics2D g=image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,1050,650);
        g.setFont(new Font("SansSerif",Font.PLAIN, 14));
        double max=0;
        for (double[][] group:data) for (double[]line:group) for (double value:line) max=Math.max(max, value);
        max =Math.max(1,max*1.1);
        g.setColor(Color.BLACK);
        g.drawString(title,left,25);
        for (int i=0;i<=5; i++) {
            int y=top +height-i*height/5;
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(left,y,left+width,y);
            g.setColor(Color.BLACK);
            g.drawString(String.format(java.util.Locale.US,"%.2f",max*i/5),5,y+5);
        }
        for (int i=0;i< 4; i++) g.drawString(String.valueOf(sizes[i]),left+i*width/3-20,top+height+ 25);
        Color[] colors = {Color.BLUE, Color.RED, new Color(0,140,0)};
        for (int a=0;a<3;a++) for (int t=0;t<3;t++) {
            g.setColor(colors[a]);
            g.setStroke(new BasicStroke(t+1));
            for (int i=0;i<4;i++) {
                int x=left+i*width /3;
                int y=top+height-(int)(data[a][t][i]/max*height);
                if (i>0){
                    int oldX=left+(i-1)*width/3;
                    int oldY=top+height-(int)(data[a][t][i-1]/max* height);
                    g.drawLine(oldX,oldY,x,y);
                }
                if (t==0) g.fillOval(x-4,y-4,8,8);
                else if (t==1) g.fillRect(x-4,y-4,8,8);
                else g.fillPolygon(new int[]{x,x-5,x+5},new int[]{y-5,y+5,y+5},3);
            }
            int legendY =top+25+(a*3+ t) *45;
            g.drawLine(left + width+20, legendY,left+width+45, legendY);
            g.drawString(algorithms[a] + " " + types[t], left + width + 50, legendY + 5);
        }
        g.dispose();
        ImageIO.write(image, "png",new File(file));
    }
}
