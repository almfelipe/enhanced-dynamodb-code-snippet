package com.almfelipe.dynamodb.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListUtils {

    public static <T> List<List<T>> splitList(List<T> collection, int splitSize) {
        return IntStream.iterate(0, i -> i < collection.size(), i -> i + splitSize)
                .mapToObj(i -> collection.subList(i, Math.min(i + splitSize, collection.size())))
                .collect(Collectors.toList());
    }
}
