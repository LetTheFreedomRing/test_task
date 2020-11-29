package com.agileengineinterview.imagegallerysearch.service;

import com.agileengineinterview.imagegallerysearch.dto.ImageDTO;
import com.agileengineinterview.imagegallerysearch.dto.ImageShortDTO;
import com.agileengineinterview.imagegallerysearch.dto.PageDTO;
import com.agileengineinterview.imagegallerysearch.exceptions.InvalidTokenException;
import com.agileengineinterview.imagegallerysearch.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ApiServiceImplTest {

    @Autowired
    private ApiService apiService;

    @Test
    void getAuthenticationToken() {
        assertNotNull(apiService.getAuthenticationToken());
    }

    @Test
    void getFirstPage() {
        String validToken = apiService.getAuthenticationToken();
        PageDTO pageDTO = apiService.getPage(validToken, 1);
        assertEquals(1, pageDTO.getPage());
    }

    @Test
    void getLastPage() {
        String validToken = apiService.getAuthenticationToken();
        PageDTO firstPage = apiService.getPage(validToken, 1);
        int pageCount = firstPage.getPageCount();
        PageDTO lastPage = apiService.getPage(validToken, pageCount);
        assertEquals(pageCount, lastPage.getPage());
    }

    @Test
    void getPageInvalidToken() {
        assertThrows(InvalidTokenException.class, () -> {
            apiService.getPage("invalid token", 1);
        });
    }

    @Test
    void getPageNotFound() {
        String validToken = apiService.getAuthenticationToken();
        PageDTO pageDTO = apiService.getPage(validToken, 5000);
        assertEquals(5000, pageDTO.getPage());
        assertFalse(pageDTO.isHasMore());
        assertTrue(pageDTO.getPictures().isEmpty());
    }

    @Test
    void getImage() {
        String validToken = apiService.getAuthenticationToken();
        PageDTO pageDTO = apiService.getPage(validToken, 1);
        if (!pageDTO.getPictures().isEmpty()) {
            ImageShortDTO imageShortDTO = pageDTO.getPictures().get(0);
            ImageDTO imageDTO = apiService.getImage(validToken, imageShortDTO.getId());
            assertNotNull(imageDTO);
            assertEquals(imageShortDTO.getId(), imageDTO.getId());
            assertEquals(imageShortDTO.getCropped_picture(), imageDTO.getCropped_picture());
        }
    }

    @Test
    void getImageNotFound() {
        String validToken = apiService.getAuthenticationToken();
        assertThrows(NotFoundException.class, () -> {
            apiService.getImage(validToken, "invalid image id");
        });
    }

    @Test
    void getImageInvalidToken() {
        assertThrows(InvalidTokenException.class, () -> {
            apiService.getImage("invalid token", "1");
        });
    }
}
