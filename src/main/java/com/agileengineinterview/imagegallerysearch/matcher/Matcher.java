package com.agileengineinterview.imagegallerysearch.matcher;

import com.agileengineinterview.imagegallerysearch.model.Image;

public interface Matcher {

    /** Returns true if image matches to given term */
    boolean matches(Image image, String term);
}
