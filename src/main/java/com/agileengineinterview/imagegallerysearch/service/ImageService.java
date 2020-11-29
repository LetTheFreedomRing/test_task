package com.agileengineinterview.imagegallerysearch.service;

import com.agileengineinterview.imagegallerysearch.dto.ImageDTO;

import java.util.List;

public interface ImageService {

    void save(ImageDTO imageDTO);

    void deleteAll();

    List<ImageDTO> findMatching(String term);
}
