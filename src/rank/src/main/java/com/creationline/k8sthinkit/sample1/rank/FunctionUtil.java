package com.creationline.k8sthinkit.sample1.rank;

import java.util.Comparator;
import java.util.function.Function;

public class FunctionUtil {

    public static <T, U, V extends Comparable<V>> Comparator<T> comparing( //
        final Function<T, U> firstExtractor, //
        final Function<U, V> secondExtractor //
    ) {
        return Comparator.comparing(firstExtractor.andThen(secondExtractor));
    }

}
