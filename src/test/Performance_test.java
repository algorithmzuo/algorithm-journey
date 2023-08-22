package test;

import java.lang.management.ManagementFactory;

public class Performance_test {

    public static int methods;
    public static long[] time, memory;
    private static final int BYTES_TO_MEGABYTES_SHIFT = 20;

    public static class info {
        public long[] time;
        public long[] memory;
        public int ac;

        public info(long[] time, long[] memory) {
            this.time = time;
            this.memory = memory;
            ac = 1;
        }

        public void print() {
            System.out.println("passed " + ac + " test cases");
        }
    }

    public static info initializeInfo(int i) {
        methods = i + 1;
        time = new long[i + 1];
        memory = new long[i + 1];
        return new info(time, memory);
    }

    public static long runTime(Runnable task) {
        long start = System.currentTimeMillis();
        task.run();
        long end = System.currentTimeMillis();
        return Math.abs(end - start);
    }

    public static long memoryConsumption(Runnable task) {
        long startMemory = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
        task.run();
        long endMemory = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
        return Math.abs(endMemory - startMemory) >> BYTES_TO_MEGABYTES_SHIFT;
    }

    public static void printInfo() {
        for (int i = 1; i < methods; i++) {
            System.out.println("method" + i + " runs " + time[i] + " ms");
        }
        for (int i = 1; i < methods; i++) {
            System.out.println("method" + i + " uses " + memory[i] + " mb");
        }
    }
}