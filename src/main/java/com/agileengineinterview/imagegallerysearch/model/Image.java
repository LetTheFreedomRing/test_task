package com.agileengineinterview.imagegallerysearch.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String author;
    private String camera;
    private String tags;
    @Column(name = "cropped_picture")
    private String croppedPicture;
    @Column(name = "full_picture")
    private String fullPicture;
}
