package com.agileengineinterview.imagegallerysearch.converter;

import com.agileengineinterview.imagegallerysearch.dto.ImageDTO;
import com.agileengineinterview.imagegallerysearch.model.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageToImageDTOConverterTest {

    private ImageToImageDTOConverter converter;

    @BeforeEach
    void setUp() {
        converter = new ImageToImageDTOConverter();
    }

    @Test
    void convert() {
        // given
        Image image = new Image();
        image.setId("dummy_id");
        image.setCamera("dummy_camera");
        image.setAuthor("dummy_author");
        image.setTags("dummy_tags");
        image.setFullPicture("dummy_full_picture");
        image.setCroppedPicture("dummy_cropped_picture");

        // when
        ImageDTO imageDTO = converter.convert(image);

        // then
        assertNotNull(imageDTO);
        assertEquals(image.getId(), imageDTO.getId());
        assertEquals(image.getAuthor(), imageDTO.getAuthor());
        assertEquals(image.getCamera(), imageDTO.getCamera());
        assertEquals(image.getTags(), imageDTO.getTags());
        assertEquals(image.getCroppedPicture(), imageDTO.getCropped_picture());
        assertEquals(image.getFullPicture(), imageDTO.getFull_picture());
    }

    @Test
    void convertNull() {
        // when
        ImageDTO imageDTO = converter.convert(null);

        // then
        assertNull(imageDTO);
    }

}