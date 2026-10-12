package insath;

import java.util.Arrays;

/** Insath: comparison-counted linear and binary searches. */
public class SearchingOperations {
    public static final class Result {
        public final int index;
        public final int comparisons;
        public final long nanoseconds;
        public Result(int index, int comparisons, long nanoseconds) {
            this.index = index;
            this.comparisons = comparisons;
            this.nanoseconds = nanoseconds;
        }
    }

    public static Result linear(int[] values, int target) {
        long start = System.nanoTime();
        int comparisons = 0;
        for (int i = 0; i < values.length; i++) {
            comparisons++;
            if (values[i] == target) return new Result(i, comparisons, System.nanoTime() - start);
        }
        return new Result(-1, comparisons, System.nanoTime() - start);
    }

    /** The input must be sorted in ascending order. */
    public static Result binary(int[] sorted, int target) {
        long start = System.nanoTime();
        int lo = 0, hi = sorted.length - 1, comparisons = 0;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            comparisons++;
            if (sorted[mid] == target) return new Result(mid, comparisons, System.nanoTime() - start);
            if (sorted[mid] < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return new Result(-1, comparisons, System.nanoTime() - start);
    }

    public static int[] sortedCopy(int[] original) {
        int[] result = Arrays.copyOf(original, original.length);
        Arrays.sort(result);
        return result;
    }
}
