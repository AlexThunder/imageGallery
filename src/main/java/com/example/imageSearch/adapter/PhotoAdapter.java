package com.example.imageSearch.adapter;

import com.example.imageSearch.dto.PhotoDetails;
import com.example.imageSearch.dto.PhotosPageResponse;
import com.example.imageSearch.dto.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class PhotoAdapter {
    @Value("${api.images.link}")
    public String imagesSourceLink;

    AuthAdapter authAdapter;
    String token;
    RestTemplate photoRest = new RestTemplate();
    Logger log = LoggerFactory.getLogger(PhotoAdapter.class);

    @Autowired
    public PhotoAdapter(AuthAdapter authAdapter) {
        this.authAdapter = authAdapter;
        addBearerToken();
    }

    @Cacheable(cacheNames = "all_photos")
    public List<PhotoDetails> getAllPhotoDetails() {
        log.debug("Get all photo details from outer source");
        return getAllIds()
                .stream()
                .map(this::getPhotoDetails)
                .collect(Collectors.toList());
    }

    public PhotoDetails getPhotoDetails(String id) {
        log.debug("Get Photo details for id: " + id);
        URI link = UriComponentsBuilder
                .fromHttpUrl(imagesSourceLink + id)
                .build()
                .encode()
                .toUri();
        return getObject(link, PhotoDetails.class);
    }

    public List<String> getAllIds() {
        log.debug("Get Photo Ids List");
        int pageId = 1;
        boolean next;
        List<String> result = new ArrayList<>();
        do {
            URI link = UriComponentsBuilder
                    .fromHttpUrl(imagesSourceLink)
                    .replaceQueryParam("page", pageId)
                    .build()
                    .encode()
                    .toUri();
            PhotosPageResponse response = getObject(link, PhotosPageResponse.class);
            result.addAll(response.getPictures().stream().map(Picture::getId).collect(Collectors.toList()));
            pageId++;
            next = response.isHasMore();
       } while(next);

        log.debug("List with " + result.size() + " ids taken");
        return result;
    }

    private <T> T getObject(URI url, Class<T> objectType) {
        try {
            return photoRest.getForObject(url, objectType);
        } catch(HttpClientErrorException e) {
            addBearerToken();
            return photoRest.getForObject(url, objectType);
        }
    }

    private void addBearerToken() {
        token = authAdapter.getToken();
        photoRest.getInterceptors().clear();
        photoRest.getInterceptors().add((request, bytes, execution) -> {
            request.getHeaders().setBearerAuth(token);
            return execution.execute(request, bytes);
        });
    }
}
