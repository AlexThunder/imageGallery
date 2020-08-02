package com.example.imageSearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PhotoDetails implements Serializable {
    String id = "";
    String author = "";
    String camera = "";
    String tags = "";
    @JsonProperty("cropped_picture")
    String croppedPicture = "";
    @JsonProperty("full_picture")
    String fullPicture = "";
}

