package us.crast.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import us.crast.datastructures.Pair;

public final class CollectionUtil {
    public static <T extends Comparable<? super T>> List<T> sorted(Collection<T> input) {
        ArrayList<T> output = new ArrayList<T>(input);
        Collections.sort(output);
        return output;
    }
    
    public static <K extends Comparable<? super K>, V> List<Map.Entry<K, V>> sortedDictEntries(Map<K, V> input) {
        List<K> keys = new ArrayList<K>(input.keySet());
        Collections.sort(keys);
        List<Map.Entry<K, V>> entries = new ArrayList<Map.Entry<K, V>>();
        for (K key : keys) {
            entries.add(new Pair<K, V>(key, input.get(key)));
        }
        return entries;
    }

}
