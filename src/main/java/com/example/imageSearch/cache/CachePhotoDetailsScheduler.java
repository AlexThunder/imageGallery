package com.example.imageSearch.cache;

import com.example.imageSearch.adapter.PhotoAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class CachePhotoDetailsScheduler {
    PhotoAdapter photoAdapter;
    Logger log = LoggerFactory.getLogger(CachePhotoDetailsScheduler.class);

    @Autowired
    public CachePhotoDetailsScheduler(PhotoAdapter photoAdapter) {
        this.photoAdapter = photoAdapter;
    }

    @Scheduled(fixedDelay = 3600 * 1000, initialDelay = 1)
    public void reloadCache() {
        clearCache();
        log.info("Cache photo details are reloading");
        photoAdapter.getAllPhotoDetails();
    }

    @CacheEvict(value = "all_photos", allEntries = true)
    public void clearCache() {}

}
