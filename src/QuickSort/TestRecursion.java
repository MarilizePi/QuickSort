package QuickSort;

import java.io.*;

/**
 * This class tests quick sort algorithm of various array sizes (total of 20 intervals between 20,000 and 10,000,000)
 * with a range between 2 and 300 recursions, increased by two, as well as getting the amount of time it takes
 * for each array to run. Also getting the average of three runs to filter out any non-significant differences in the run time.
 *
 * @author Pires, Marilize.
 */
public class TestRecursion {

    private static final int MIN_RECURSION_LIMIT = 2;
    private static final int MAX_RECURSION_LIMIT = 300;
    private static final int MIN_ARRAY_SIZE = 20000;
    private static final int MAX_ARRAY_SIZE = 10000000;
    private static final double INCREMENT_ARRAY = 500000; //This increment will result in about 20 runs.
    private static final int NUMBER_OF_COMPARISONS = 3; //Compare the run 3 times to filter out.


    /**
     * This method converts the data into a CSV file, which will be stored in the resources folder.
     *
     * @param arraySize the size of array generated.
     * @param nextRow   for every recursion limit, step to the next row.
     */
    public static void CSVFile(int arraySize, String nextRow) {
        {
            PrintWriter printWriter;
            try {
                printWriter = new PrintWriter(new File("resources/data_array" + arraySize + ".csv"));

                printWriter.print(nextRow);
                printWriter.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        }
    }

    /**
     * This method tests the recursion limits of 2 and 300 of each array and returns the time elapsed.
     *
     * @param count     it counts the recursion limit.
     * @param copyArray it stored randomly generated arrays.
     * @return time elapsed.
     */
    public static String recurseTimeAndAverage(int count, Integer[] copyArray) {
        Integer[] sortArray;

        String topRowTitle = "Recursion Limit, Time Elapsed";
        String addResult = topRowTitle + "\n";

        while (count <= MAX_RECURSION_LIMIT) {
            long elapsedTime = 0;

            for (int i = 0; i < NUMBER_OF_COMPARISONS; i++) {
                sortArray = new Integer[copyArray.length];
                System.arraycopy(copyArray, 0, sortArray, 0, copyArray.length);
                elapsedTime += estimateTime(count, sortArray);
            }

            long averageElapsedTime = (elapsedTime) / NUMBER_OF_COMPARISONS;
            System.out.println("Number of Recursions: " + count + "  |  Elapsed Time: "
                    + TimeConverter.convertTimeToString(averageElapsedTime)
                    + " = " + "(average) " + averageElapsedTime + " ns");

            addResult += count + "," + averageElapsedTime + "\n";
            count += 2;
        }
        return addResult;
    }

    /**
     * This methods returns the amount of time it takes for the algorithm to run.
     *
     * @param count       it counts the recursion limit.
     * @param copiedArray it stored randomly generated arrays.
     * @return the amount of time it takes for the algorithm to run.
     */
    public static long estimateTime(int count, Integer[] copiedArray) {
        long startTime;
        long endTime;
        long totalEstimatedTime;

        startTime = System.nanoTime();
        FHsort.setRecursionLimit(count);
        FHsort.quickSort(copiedArray);
        endTime = System.nanoTime();
        totalEstimatedTime = endTime - startTime;

        return totalEstimatedTime;
    }

    // --------------- Main Class ---------------
    public static void main(String[] args) {
        int count = MIN_RECURSION_LIMIT; //initializing count as 2.
        int arraySize;

        for (arraySize = MIN_ARRAY_SIZE; arraySize < MAX_ARRAY_SIZE; arraySize += INCREMENT_ARRAY) {
            System.out.println("\n\n*************** Quick sorting array size: " + arraySize + " ***************\n");

            Integer[] integersArray = new Integer[arraySize];

            for (int i = 0; i < arraySize; i++) {
                integersArray[i] = (int) (Math.random() * arraySize);
            }

            String result = recurseTimeAndAverage(count, integersArray);

            CSVFile(arraySize, result);
        }
    }
}