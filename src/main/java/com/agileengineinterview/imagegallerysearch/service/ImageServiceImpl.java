package com.agileengineinterview.imagegallerysearch.service;

import com.agileengineinterview.imagegallerysearch.converter.ImageDTOToImageConverter;
import com.agileengineinterview.imagegallerysearch.converter.ImageToImageDTOConverter;
import com.agileengineinterview.imagegallerysearch.dto.ImageDTO;
import com.agileengineinterview.imagegallerysearch.matcher.Matcher;
import com.agileengineinterview.imagegallerysearch.model.Image;
import com.agileengineinterview.imagegallerysearch.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageToImageDTOConverter imageToImageDTOConverter;
    private final ImageDTOToImageConverter imageDTOToImageConverter;
    private final Matcher matcher;

    public ImageServiceImpl(ImageRepository imageRepository,
                            ImageToImageDTOConverter imageToImageDTOConverter,
                            ImageDTOToImageConverter imageDTOToImageConverter,
                            Matcher matcher) {
        this.imageRepository = imageRepository;
        this.imageToImageDTOConverter = imageToImageDTOConverter;
        this.imageDTOToImageConverter = imageDTOToImageConverter;
        this.matcher = matcher;
    }

    @Override
    public void save(ImageDTO imageDTO) {
        imageRepository.save(imageDTOToImageConverter.convert(imageDTO));
    }

    @Override
    public void deleteAll() {
        imageRepository.deleteAll();
    }

    /**
     * Iterates over all images in repository and returns all images that match to given term
     * @param term : given search term
     * @return : all images in repository that match to {@param term}
     */
    @Override
    public List<ImageDTO> findMatching(String term) {
        List<Image> images = imageRepository.findAll();
        List<ImageDTO> matchingImages = new ArrayList<>();
        images.forEach(image -> {
            if (matcher.matches(image, term)) {
                matchingImages.add(imageToImageDTOConverter.convert(image));
            }
        });
        return matchingImages;
    }
}
