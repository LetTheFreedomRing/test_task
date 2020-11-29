package com.agileengineinterview.imagegallerysearch.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ImageShortDTO {

    private String id;
    private String cropped_picture;
}
