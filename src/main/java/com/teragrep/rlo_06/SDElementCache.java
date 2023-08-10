package com.teragrep.rlo_06;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Cache object to contain multiple pre-allocated SDElements for use to avoid memory allocations for new ones
 */
final class SDElementCache {

    private final Queue<SDElement> cachedSDElements;

    final int numElements;

    SDElementCache(int numElements) {
        this.numElements = numElements;
        cachedSDElements = new ArrayDeque<>(this.numElements);
    }


    SDElement take() {
        SDElement sdElement;
        try {
            sdElement = cachedSDElements.remove();
        }
        catch (NoSuchElementException nsee) {
            sdElement = new SDElement();
        }
        return sdElement;
    }

    void put(SDElement sdElement) {
        sdElement.clear();
        if (cachedSDElements.size() < numElements) {
            cachedSDElements.add(sdElement);
        }
    }
}
