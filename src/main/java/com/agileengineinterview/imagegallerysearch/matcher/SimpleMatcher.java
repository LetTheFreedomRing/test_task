package com.agileengineinterview.imagegallerysearch.matcher;

import com.agileengineinterview.imagegallerysearch.model.Image;
import org.springframework.stereotype.Component;

/**
 * Simple Matcher implementation, that returns true if image fields lower case contain term lower case.
 * If term is empty, also returns true
 */
@Component
public class SimpleMatcher implements Matcher {

    @Override
    public boolean matches(Image image, String term) {
        return contains(image.getAuthor(), term) ||
                contains(image.getCamera(), term) ||
                contains(image.getTags(), term) ||
                contains(image.getCroppedPicture(), term) ||
                contains(image.getFullPicture(), term);
    }

    private boolean contains(String field, String term) {
        if (term == null || term.isEmpty()) return true;
        return field != null && field.toLowerCase().contains(term.toLowerCase());
    }
}
