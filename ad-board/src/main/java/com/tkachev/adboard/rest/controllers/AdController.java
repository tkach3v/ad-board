package com.tkachev.adboard.rest.controllers;

import com.tkachev.adboard.dto.models.ad.AdResponse;
import com.tkachev.adboard.dto.models.ad.CreateAdRequest;
import com.tkachev.adboard.dto.models.ad.UpdateAdRequest;
import com.tkachev.adboard.services.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ads")
public class AdController {

    private final AdService adService;

    @GetMapping
    @PreAuthorize("hasAuthority('ads:read')")
    public ResponseEntity<List<AdResponse>> getAds(@RequestParam Map<String, String> parameters) {
        List<AdResponse> ads = adService.getAds(parameters);

        return new ResponseEntity<>(ads, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ads:write')")
    public ResponseEntity<AdResponse> createAd(@Valid @RequestBody CreateAdRequest dto) {
        AdResponse ad = adService.createAd(dto);

        return new ResponseEntity<>(ad, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ads:read')")
    public ResponseEntity<AdResponse> getAdById(@PathVariable(name = "id") Long id) {
        AdResponse ad = adService.getAdById(id);

        return new ResponseEntity<>(ad, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ads:write')")
    public ResponseEntity<AdResponse> updateAd(@Valid @RequestBody UpdateAdRequest dto) {
        AdResponse ad = adService.updateAd(dto);

        return new ResponseEntity<>(ad, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ads:delete')")
    public ResponseEntity<String> deleteAd(@PathVariable Long id) {
        adService.deleteAd(id);

        return new ResponseEntity<>("Ad with ID = " + id + " has been deleted", HttpStatus.NO_CONTENT);
    }
}
