package com.agileengineinterview.imagegallerysearch.matcher;

import com.agileengineinterview.imagegallerysearch.model.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleMatcherTest {

    private SimpleMatcher matcher;

    @BeforeEach
    void setUp() {
        matcher = new SimpleMatcher();
    }

    @Test
    void matchesTrue() {
        // given
        Image image = new Image();
        image.setCamera("Camera");

        // then
        assertTrue(matcher.matches(image, "Cam"));

    }

    @Test
    void matchesFalse() {
        // given
        Image image = new Image();
        image.setCamera("Camera");

        // then
        assertFalse(matcher.matches(image, "Cama"));
    }

    @Test
    void matchesLower() {
        // given
        Image image = new Image();
        image.setCamera("Camera");

        // then
        assertTrue(matcher.matches(image, "MER"));
    }

    @Test
    void matchesEmptyTerm() {
        // given
        Image image = new Image();
        image.setCamera("Camera");

        // then
        assertTrue(matcher.matches(image, ""));
    }

    @Test
    void matchesNullTerm() {
        // given
        Image image = new Image();
        image.setCamera("Camera");

        // then
        assertTrue(matcher.matches(image, null));
    }

    @Test
    void matchesNullField() {
        // given
        Image image = new Image();

        // then
        assertFalse(matcher.matches(image, "cam"));
    }
}