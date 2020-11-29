package com.agileengineinterview.imagegallerysearch.converter;

import com.agileengineinterview.imagegallerysearch.dto.ImageDTO;
import com.agileengineinterview.imagegallerysearch.model.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageDTOToImageConverterTest {

    private ImageDTOToImageConverter converter;

    @BeforeEach
    void setUp() {
        converter = new ImageDTOToImageConverter();
    }

    @Test
    void convert() {
        // given
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId("dummy_id");
        imageDTO.setCamera("dummy_camera");
        imageDTO.setAuthor("dummy_author");
        imageDTO.setTags("dummy_tags");
        imageDTO.setFull_picture("dummy_full_picture");
        imageDTO.setCropped_picture("dummy_cropped_picture");

        // when
        Image image = converter.convert(imageDTO);

        // then
        assertNotNull(image);
        assertEquals(imageDTO.getId(), image.getId());
        assertEquals(imageDTO.getAuthor(), image.getAuthor());
        assertEquals(imageDTO.getCamera(), image.getCamera());
        assertEquals(imageDTO.getTags(), image.getTags());
        assertEquals(imageDTO.getCropped_picture(), image.getCroppedPicture());
        assertEquals(imageDTO.getFull_picture(), image.getFullPicture());
    }

    @Test
    void convertNull() {
        // when
        Image image = converter.convert(null);

        // then
        assertNull(image);
    }
}