package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        for(int N = 100000; N <= 12800000; N *= 2){
            Ns.addLast(N);
            times.addLast(speedTest(N));
            opCounts.addLast(N);
        }
        printTimingTable(Ns, times, opCounts);
    }

    private static double speedTest(int N){
        SLList<Integer> list = new SLList<>();
        for(int i = 0; i <= N; i++){
            list.addFirst(0);
        }
        double t1 = System.currentTimeMillis();
        list.getLast();
        double t2 = System.currentTimeMillis();
        return (t2-t1)/1000;
    }

}
