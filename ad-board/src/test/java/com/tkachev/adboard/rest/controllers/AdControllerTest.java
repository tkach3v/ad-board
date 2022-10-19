package com.tkachev.adboard.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkachev.adboard.dto.models.ad.AdResponse;
import com.tkachev.adboard.dto.models.ad.CreateAdRequest;
import com.tkachev.adboard.dto.models.ad.UpdateAdRequest;
import com.tkachev.adboard.dto.models.user.UpdateUserRequest;
import com.tkachev.adboard.dto.models.user.UserResponse;
import com.tkachev.adboard.security.JwtTokenProvider;
import com.tkachev.adboard.services.AdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AdController.class)
class AdControllerTest {

    @MockBean
    private AdService adService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String url = "/api/v1/ads";

    @Test
    void shouldCreateAd() throws Exception {
        CreateAdRequest createAdRequest = new CreateAdRequest();
        createAdRequest.setTitle("Tom Soyer paper book");
        createAdRequest.setDescription("book about Tom Soyer");
        createAdRequest.setPrice(200D);
        createAdRequest.setOwnerId(1L);
        createAdRequest.setCategoryId(1L);
        createAdRequest.setCreationDate(mock(Date.class));
        when(adService.createAd(createAdRequest)).thenReturn(new AdResponse());

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAdRequest)))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(adService).createAd(any(CreateAdRequest.class));
    }

    @Test
    void shouldUpdateAd() throws Exception {
        UpdateAdRequest updateAdRequest = new UpdateAdRequest();
        updateAdRequest.setId(1L);
        updateAdRequest.setTitle("Tom Soyer paper book");
        updateAdRequest.setDescription("book about Tom Soyer");
        updateAdRequest.setPrice(200D);
        updateAdRequest.setOwnerId(1L);
        updateAdRequest.setCategoryId(1L);
        updateAdRequest.setCreationDate(mock(Date.class));
        when(adService.updateAd(updateAdRequest)).thenReturn(new AdResponse());

        mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateAdRequest)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(adService).updateAd(any(UpdateAdRequest.class));
    }

    @Test
    void shouldGetAds() throws Exception {
        when(adService.getAds(new HashMap<>())).thenReturn(new ArrayList<>());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(print());

        verify(adService).getAds(any(Map.class));
    }

    @Test
    void shouldGetAdById() throws Exception {
        Long adId = 1L;
        when(adService.getAdById(adId)).thenReturn(new AdResponse());

        mockMvc.perform(get(url + "/" + adId))
                .andExpect(status().isOk())
                .andDo(print());

        verify(adService).getAdById(adId);
    }

    @Test
    void shouldDeleteAd() throws Exception {
        Long adId = 1L;

        mockMvc.perform(delete(url + "/" + adId))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(adService).deleteAd(adId);
    }
}