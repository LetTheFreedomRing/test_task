package com.agileengineinterview.imagegallerysearch.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class PageDTO {
    private List<ImageShortDTO> pictures;
    private Integer page;
    private Integer pageCount;
    private boolean hasMore;
}
