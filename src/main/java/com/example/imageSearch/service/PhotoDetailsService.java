package com.example.imageSearch.service;

import com.example.imageSearch.adapter.PhotoAdapter;
import com.example.imageSearch.dto.PhotoDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoDetailsService {
    PhotoAdapter photoAdapter;
    Logger log = LoggerFactory.getLogger(PhotoDetailsService.class);

    @Autowired
    public PhotoDetailsService(PhotoAdapter photoAdapter) {
        this.photoAdapter = photoAdapter;
    }

    public List<PhotoDetails> searchPhotoDetails(String searchTerm) {
        log.debug("Search photos for " + searchTerm);
        return photoAdapter.getAllPhotoDetails()
                .stream()
                .filter(photoDetails -> containsSearchTerm(photoDetails, searchTerm))
                .collect(Collectors.toList());
    }

    private boolean containsSearchTerm(PhotoDetails photoDetails, String searchTerm) {
        String lowerCaseSearchTerm = searchTerm.toLowerCase();
        return photoDetails.getAuthor().toLowerCase().contains(lowerCaseSearchTerm)
                || photoDetails.getCamera().toLowerCase().contains(lowerCaseSearchTerm)
                || photoDetails.getTags().toLowerCase().contains(lowerCaseSearchTerm)
                || photoDetails.getCroppedPicture().toLowerCase().contains(lowerCaseSearchTerm)
                || photoDetails.getFullPicture().toLowerCase().contains(lowerCaseSearchTerm);
    }

}
