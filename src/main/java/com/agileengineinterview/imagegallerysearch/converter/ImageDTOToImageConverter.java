package com.agileengineinterview.imagegallerysearch.converter;

import com.agileengineinterview.imagegallerysearch.dto.ImageDTO;
import com.agileengineinterview.imagegallerysearch.model.Image;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ImageDTOToImageConverter implements Converter<ImageDTO, Image> {

    @Override
    public Image convert(ImageDTO imageDTO) {
        if (imageDTO == null) return null;
        Image image = new Image();
        image.setId(imageDTO.getId());
        image.setAuthor(imageDTO.getAuthor());
        image.setCamera(imageDTO.getCamera());
        image.setCroppedPicture(imageDTO.getCropped_picture());
        image.setFullPicture(imageDTO.getFull_picture());
        image.setTags(imageDTO.getTags());
        return image;
    }
}
