package com.agileengineinterview.imagegallerysearch;

import com.agileengineinterview.imagegallerysearch.dto.ImageDTO;
import com.agileengineinterview.imagegallerysearch.dto.PageDTO;
import com.agileengineinterview.imagegallerysearch.exceptions.InvalidTokenException;
import com.agileengineinterview.imagegallerysearch.service.ApiService;
import com.agileengineinterview.imagegallerysearch.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Class that loads all images from API_ENDPOINT, saves it to repository and reloads after defined period of time
 */
@Component
public class ImageLoader {

    private static final Logger log = LoggerFactory.getLogger(ImageLoader.class);

    private final ApiService apiService;
    private final ImageService imageService;

    /** Holder for Bearer token, used in header for all requests */
    private String token;

    public ImageLoader(ApiService apiService, ImageService imageService) {
        this.apiService = apiService;
        this.imageService = imageService;
    }

    /**
     * Deletes everything from repository and loads back in a defined period of time
     */
    @Scheduled(fixedRateString = "${IMAGES_RELOAD_TIME_MS}")
    public void reloadImages() {
        imageService.deleteAll();
        loadImages();
    }

    /**
     * Loads images from API and saves to repository
     */
    private void loadImages() {
        log.info("Loading data");
        try {
            // obtain valid authentication token
            token = apiService.getAuthenticationToken();

            // get first page with images and save them to repository
            PageDTO firstPage = loadPage(1);
            saveImagesFromPage(firstPage);

            // get number of pages
            int pageCount = firstPage.getPageCount();

            // get all remaining pages with images and save them to repository
            for (int i = 2; i < pageCount; i++) {
                PageDTO page = loadPage(i);
                saveImagesFromPage(page);
            }
            log.info("Data successfully loaded");
        } catch (Exception ex) {
            log.error("Exception occurred during image loading : " + ex.toString());
        }
    }

    /**
     * Iterates over all pictures in page and save full detailed image
     * @param page : page with images to be saved
     */
    private void saveImagesFromPage(PageDTO page) {
        page.getPictures().forEach(imageShort -> {
            ImageDTO image = loadImage(imageShort.getId());
            imageService.save(image);
        });
    }

    /**
     * Tries to load page, if token is invalid, then try to obtain new valid token and retry.
     * If we get InvalidTokenException second time, then throw this exception
     */
    private PageDTO loadPage(Integer pageNum) {
        PageDTO page;
        try {
            page = apiService.getPage(token, pageNum);
        } catch (InvalidTokenException tokenException) {
            // try to obtain new token one more time
            token = apiService.getAuthenticationToken();
            page = apiService.getPage(token, pageNum);
        }
        return page;
    }

    /**
     * Tries to load image, if token is invalid, then try to obtain new valid token and retry.
     * If we get InvalidTokenException second time, then throw this exception
     */
    private ImageDTO loadImage(String id) {
        ImageDTO image;
        try {
            image = apiService.getImage(token, id);
        } catch (InvalidTokenException tokenException) {
            // try to obtain new token one more time
            token = apiService.getAuthenticationToken();
            image = apiService.getImage(token, id);
        }
        return image;
    }
}
