package com.example.imageSearch.dto;

import lombok.Data;

import java.util.List;

@Data
public class PhotosPageResponse {
    List<Picture> pictures;
    int page;
    int pageCount;
    boolean hasMore;
}
