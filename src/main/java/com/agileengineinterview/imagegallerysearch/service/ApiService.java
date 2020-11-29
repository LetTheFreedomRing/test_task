package com.agileengineinterview.imagegallerysearch.service;

import com.agileengineinterview.imagegallerysearch.dto.ImageDTO;
import com.agileengineinterview.imagegallerysearch.dto.PageDTO;
import com.agileengineinterview.imagegallerysearch.exceptions.InvalidTokenException;
import com.agileengineinterview.imagegallerysearch.exceptions.NotFoundException;
import com.agileengineinterview.imagegallerysearch.exceptions.TokenNotObtainedException;

public interface ApiService {

    String getAuthenticationToken() throws TokenNotObtainedException;

    PageDTO getPage(String token, Integer pageNum) throws NotFoundException, InvalidTokenException;

    ImageDTO getImage(String token, String id) throws NotFoundException, InvalidTokenException;
}
