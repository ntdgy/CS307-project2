package ntdgy.cs307project2.service;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private Map<K, V> map;
    private final int cacheSize;

    public LRUCache(int initialCapacity) {
        LinkedHashMap<K, V> map = new LinkedHashMap<K, V>(initialCapacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > cacheSize;
            }
        };
        this.cacheSize = initialCapacity;
    }
}
