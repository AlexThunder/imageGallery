package com.example.imageSearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Picture {
    String id;
    @JsonProperty("cropped_picture")
    String croppedPicture;
}