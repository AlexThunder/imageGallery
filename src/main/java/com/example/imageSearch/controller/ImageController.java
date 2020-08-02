package com.example.imageSearch.controller;

import com.example.imageSearch.dto.PhotoDetails;
import com.example.imageSearch.service.PhotoDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class ImageController {
    PhotoDetailsService photoDetailsService;

    @Autowired
    public ImageController(PhotoDetailsService photoDetailsService) {
        this.photoDetailsService = photoDetailsService;
    }

    @GetMapping("/{searchTerm}")
    public List<PhotoDetails> searchPhotos(@PathVariable String searchTerm) {
        return photoDetailsService.searchPhotoDetails(searchTerm);
    }

}
