package com.agileengineinterview.imagegallerysearch.converter;

import com.agileengineinterview.imagegallerysearch.dto.ImageDTO;
import com.agileengineinterview.imagegallerysearch.model.Image;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ImageToImageDTOConverter implements Converter<Image, ImageDTO> {

    @Override
    public ImageDTO convert(Image image) {
        if (image == null) return null;
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(image.getId());
        imageDTO.setAuthor(image.getAuthor());
        imageDTO.setCamera(image.getCamera());
        imageDTO.setCropped_picture(image.getCroppedPicture());
        imageDTO.setFull_picture(image.getFullPicture());
        imageDTO.setTags(image.getTags());
        return imageDTO;
    }
}
