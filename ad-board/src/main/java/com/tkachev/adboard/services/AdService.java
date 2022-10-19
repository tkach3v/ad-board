package com.tkachev.adboard.services;

import com.tkachev.adboard.dto.models.ad.AdResponse;
import com.tkachev.adboard.dto.models.ad.CreateAdRequest;
import com.tkachev.adboard.dto.models.ad.UpdateAdRequest;

import java.util.List;
import java.util.Map;

public interface AdService {

    AdResponse createAd(CreateAdRequest dto);

    void deleteAd(Long id);

    AdResponse updateAd(UpdateAdRequest dto);

    List<AdResponse> getAds(Map<String, String> parameters);

    AdResponse getAdById(Long id);
}
