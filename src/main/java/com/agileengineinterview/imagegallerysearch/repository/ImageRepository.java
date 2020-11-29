package com.agileengineinterview.imagegallerysearch.repository;

import com.agileengineinterview.imagegallerysearch.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
