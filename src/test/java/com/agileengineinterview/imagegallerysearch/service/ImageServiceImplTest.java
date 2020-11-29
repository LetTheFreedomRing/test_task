package com.agileengineinterview.imagegallerysearch.service;

import com.agileengineinterview.imagegallerysearch.converter.ImageDTOToImageConverter;
import com.agileengineinterview.imagegallerysearch.converter.ImageToImageDTOConverter;
import com.agileengineinterview.imagegallerysearch.dto.ImageDTO;
import com.agileengineinterview.imagegallerysearch.matcher.Matcher;
import com.agileengineinterview.imagegallerysearch.matcher.SimpleMatcher;
import com.agileengineinterview.imagegallerysearch.model.Image;
import com.agileengineinterview.imagegallerysearch.repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageServiceImpl imageService;

    private ImageToImageDTOConverter imageToImageDTOConverter;
    private ImageDTOToImageConverter imageDTOToImageConverter;

    private List<Image> allImages;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageToImageDTOConverter = new ImageToImageDTOConverter();
        imageDTOToImageConverter = new ImageDTOToImageConverter();
        Matcher matcher = new SimpleMatcher();
        imageService = new ImageServiceImpl(imageRepository, imageToImageDTOConverter, imageDTOToImageConverter, matcher);

        Image image1= new Image();
        image1.setCamera("camera1");
        Image image2= new Image();
        image2.setCamera("camera2");
        allImages = new ArrayList<>(Arrays.asList(image1, image2));
    }

    @Test
    void findMatchingFound() {
        // given

        Mockito.when(imageRepository.findAll()).thenReturn(allImages);

        // when
        List<ImageDTO> dtos = imageService.findMatching("camera1");
        assertEquals(1, dtos.size());
        assertEquals(imageToImageDTOConverter.convert(allImages.get(0)), dtos.get(0));
    }

    @Test
    void findMatchingEmptyTag() {
        // given
        Mockito.when(imageRepository.findAll()).thenReturn(allImages);

        // when
        List<ImageDTO> dtos = imageService.findMatching("");

        // then
        assertEquals(2, dtos.size());
    }

    @Test
    void findMatchingNotFound() {
        // given
        Mockito.when(imageRepository.findAll()).thenReturn(allImages);

        // when
        List<ImageDTO> dtos = imageService.findMatching("incorrect_term");

        // then
        assertTrue(dtos.isEmpty());
    }
}