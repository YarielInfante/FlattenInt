import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlattenInteger {

    public static void main(String[] args) {
        // [0, [1, 2, 3, 4], [ [4, 5], [[[6, 7]]] ], [[[6, 7]]], 9 ]
        var array = new Object[]{
                0,
                new Integer[]{1, 2, 3, 4},
                new Object[]{
                        new Integer[]{4, 5},
                        new Object[]{
                                new Object[]{
                                        new Integer[]{6, 7}
                                }
                        }
                },
                new Object[]{
                        new Object[]{
                                new Integer[]{6, 7}
                        }
                },
                9
        };


        // using FlatMap
        var resultFlatMap = Arrays
                .stream(array)
                .flatMap(FlattenInteger::deepFlat)
                .collect(Collectors.toList());


        resultFlatMap.forEach(System.out::println);


        var resultRecursive = deepFlat(array, new ArrayList<>());

        resultRecursive.forEach(System.out::println);
    }

    /**
     * Flattens an array of arbitrarily nested arrays of integers into array of integers using Arrays flatMap
     *
     * @param arrayNested array of arbitrarily nested arrays of integers
     * @return flat array of integers
     * @see java.util.stream.Stream#flatMap(Function)
     */
    public static Stream<Integer> deepFlat(Object arrayNested) {
        if (arrayNested instanceof Object[])
            return Arrays
                    .stream((Object[]) arrayNested)
                    .flatMap(FlattenInteger::deepFlat);
        else
            return Stream.of((Integer) arrayNested);
    }

    /**
     * Flattens an array of arbitrarily nested arrays of integers into array of integers using Arrays flatMap
     *
     * @param arrayOfInts array of arbitrarily nested arrays of integers
     * @param finalArray  List of array to return
     * @return flat array of integers
     * @see java.util.stream.Stream#flatMap(Function)
     */
    public static List<Integer> deepFlat(Object[] arrayOfInts, List<Integer> finalArray) {

        for (Object i : arrayOfInts) {
            if (i instanceof Integer) {
                finalArray.add((int) i);
            } else if (i instanceof Object[]) {
                deepFlat((Object[]) i, finalArray);
            }
        }
        return finalArray;
    }
}
