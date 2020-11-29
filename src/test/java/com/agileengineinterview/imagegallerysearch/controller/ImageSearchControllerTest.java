package com.agileengineinterview.imagegallerysearch.controller;

import com.agileengineinterview.imagegallerysearch.dto.ImageDTO;
import com.agileengineinterview.imagegallerysearch.service.ImageServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

class ImageSearchControllerTest {

    @Mock
    private ImageServiceImpl imageService;

    @InjectMocks
    private ImageSearchController imageSearchController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(imageSearchController).build();
    }

    @Test
    void searchImages() throws Exception {
        ImageDTO imageDTO1 = new ImageDTO();
        imageDTO1.setId("1");
        ImageDTO imageDTO2 = new ImageDTO();
        imageDTO2.setId("2");

        Mockito.when(imageService.findMatching(ArgumentMatchers.anyString())).thenReturn(Arrays.asList(imageDTO1, imageDTO2));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/search/{searchTerm}", "term")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
        Mockito.verify(imageService, Mockito.times(1)).findMatching(ArgumentMatchers.anyString());
    }
}