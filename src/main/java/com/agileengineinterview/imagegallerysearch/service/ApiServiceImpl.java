package com.agileengineinterview.imagegallerysearch.service;

import com.agileengineinterview.imagegallerysearch.dto.ImageDTO;
import com.agileengineinterview.imagegallerysearch.dto.PageDTO;
import com.agileengineinterview.imagegallerysearch.exceptions.InvalidTokenException;
import com.agileengineinterview.imagegallerysearch.exceptions.NotFoundException;
import com.agileengineinterview.imagegallerysearch.exceptions.TokenNotObtainedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiServiceImpl implements ApiService {

    private static final Logger log = LoggerFactory.getLogger(ApiService.class);

    private final String apiUrl;
    private final String apiKey;

    public ApiServiceImpl(@Value("${api.url}") String apiUrl, @Value("${api.key}") String apiKey) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    /**
     * Makes post request to API and receives valid authentication token
     * @return : authentication token
     * @throws TokenNotObtainedException
     */
    @Override
    public String getAuthenticationToken() throws TokenNotObtainedException {
        String authUrl = apiUrl + "/auth";
        HttpHeaders headers = createHeader();
        JSONObject body = new JSONObject();
        body.put("apiKey", apiKey);
        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(authUrl, request, String.class);
        if (response == null) {
            throw new TokenNotObtainedException();
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(response);
            String token = node.get("token").asText();
            log.debug("Obtained token : " + token);
            return token;
        } catch (JsonProcessingException e) {
            throw new TokenNotObtainedException();
        }
    }

    /**
     * Makes get request to API and receives given page with images
     * @param token : authentication token
     * @param pageNum : desired page number
     * @return : page number {@param pageNum} with images
     * @throws NotFoundException
     * @throws InvalidTokenException
     */
    @Override
    public PageDTO getPage(String token, Integer pageNum) throws NotFoundException, InvalidTokenException {
        log.debug("Loading page number " + pageNum);
        String pageUrl = pageNum == 1 ? apiUrl + "/images" : apiUrl + "/images?page=" + pageNum;
        HttpHeaders headers = createHeaderWithBearerAuth(token);
        HttpEntity request = new HttpEntity(headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<PageDTO> response = restTemplate.exchange(pageUrl, HttpMethod.GET, request, PageDTO.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                if (response.getBody() == null) {
                    throw new NotFoundException();
                }
                return response.getBody();
            } else {
                log.error("response status : " + response.getStatusCode());
                throw new RuntimeException();
            }
        } catch (HttpClientErrorException.Unauthorized unauthorized) {
            throw new InvalidTokenException();
        }
    }

    /**
     * Makes get request to API and receives full image details by given id
     * @param token : authentication token
     * @param id : desired image id
     * @return : detailed image with id equal to {@param id}
     * @throws NotFoundException
     * @throws InvalidTokenException
     */
    @Override
    public ImageDTO getImage(String token, String id) throws NotFoundException, InvalidTokenException {
        log.debug("Loading image " + id);
        String imageUrl = apiUrl + "/images/" + id;
        HttpHeaders headers = createHeaderWithBearerAuth(token);
        HttpEntity request = new HttpEntity(headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<ImageDTO> response = restTemplate.exchange(imageUrl, HttpMethod.GET, request, ImageDTO.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                if (response.getBody() == null) {
                    throw new NotFoundException();
                }
                return response.getBody();
            } else {
                log.error("response status : " + response.getStatusCode());
                throw new RuntimeException();
            }
        } catch (HttpClientErrorException.Unauthorized unauthorized) {
            throw new InvalidTokenException();
        } catch (HttpClientErrorException.NotFound notFound) {
            throw new NotFoundException();
        }
    }

    private HttpHeaders createHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        return header;
    }

    private HttpHeaders createHeaderWithBearerAuth(String token) {
        HttpHeaders header = createHeader();
        header.setBearerAuth(token);
        return header;
    }
}
