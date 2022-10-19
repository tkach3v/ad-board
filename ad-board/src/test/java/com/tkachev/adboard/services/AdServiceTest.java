package com.tkachev.adboard.services;

import com.tkachev.adboard.config.TestConfig;
import com.tkachev.adboard.dto.models.ad.AdResponse;
import com.tkachev.adboard.dto.models.ad.CreateAdRequest;
import com.tkachev.adboard.dto.models.ad.UpdateAdRequest;
import com.tkachev.adboard.dto.models.chat.ChatResponse;
import com.tkachev.adboard.dto.models.chat.CreateChatRequest;
import com.tkachev.adboard.dto.models.chat.UpdateChatRequest;
import com.tkachev.adboard.dto.models.user.CreateUserRequest;
import com.tkachev.adboard.dto.models.user.UserResponse;
import com.tkachev.adboard.entity.*;
import com.tkachev.adboard.exception_handling.exceptions.EntityAlreadyExistsException;
import com.tkachev.adboard.exception_handling.exceptions.NoSuchEntityException;
import com.tkachev.adboard.repositories.AdRepository;
import com.tkachev.adboard.repositories.CategoryRepository;
import com.tkachev.adboard.repositories.ChatRepository;
import com.tkachev.adboard.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
class AdServiceTest {

    @Autowired
    private AdService adService;
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    private void beforeEach() {
        reset(adRepository, userRepository, categoryRepository);
    }

    @Test
    void checkOwnerAndCategoryExistenceAndSaveAdWhenCreatingAd() {
        CreateAdRequest dto = new CreateAdRequest();
        dto.setCategoryId(1L);
        dto.setOwnerId(1L);
        when(categoryRepository.findById(dto.getCategoryId())).thenReturn(Optional.of(new Category()));
        when(userRepository.findById(dto.getOwnerId())).thenReturn(Optional.of(new User()));

        adService.createAd(dto);

        verify(userRepository).findById(anyLong());
        verify(categoryRepository).findById(anyLong());
        verify(adRepository).save(any(Ad.class));
    }

    @Test
    void setDefaultParametersIfTheyMissedWhenCreatingAd() {
        CreateAdRequest dto = new CreateAdRequest();
        dto.setCategoryId(1L);
        dto.setOwnerId(1L);
        when(categoryRepository.findById(dto.getCategoryId())).thenReturn(Optional.of(new Category()));
        when(userRepository.findById(dto.getOwnerId())).thenReturn(Optional.of(new User()));

        AdResponse adResponse = adService.createAd(dto);

        assertEquals(AdStatus.ACTIVE, adResponse.getStatus());
        assertEquals(false, adResponse.getPromotion());
    }

    @Test
    void throwExceptionIfOwnerDoesNotExistWhenCreatingAd() {
        CreateAdRequest dto = new CreateAdRequest();
        dto.setCategoryId(1L);
        dto.setOwnerId(1L);
        when(categoryRepository.findById(dto.getCategoryId())).thenReturn(Optional.of(new Category()));
        when(userRepository.findById(dto.getOwnerId())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> adService.createAd(dto));
    }

    @Test
    void throwExceptionIfCategoryDoesNotExistWhenCreatingAd() {
        CreateAdRequest dto = new CreateAdRequest();
        dto.setCategoryId(1L);
        dto.setOwnerId(1L);
        when(categoryRepository.findById(dto.getCategoryId())).thenReturn(Optional.empty());
        when(userRepository.findById(dto.getOwnerId())).thenReturn(Optional.of(new User()));

        assertThrows(NoSuchEntityException.class, () -> adService.createAd(dto));
    }

    @Test
    void checkAdExistenceAndCallDeleteAdWhenDeletingAd() {
        when(adRepository.findById(anyLong())).thenReturn(Optional.of(new Ad()));

        adService.deleteAd(anyLong());

        verify(adRepository).findById(anyLong());
        verify(adRepository).delete(any(Ad.class));
    }

    @Test
    void throwExceptionIfAdDoesNotExistWhenDeletingAd() {
        when(adRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> adService.deleteAd(anyLong()));
    }

    @Test
    void checkAdAndOwnerAndCategoryExistenceAndSaveAdWhenUpdatingAd() {
        UpdateAdRequest dto = new UpdateAdRequest();
        dto.setId(1L);
        dto.setCategoryId(1L);
        dto.setOwnerId(1L);
        when(adRepository.findById(dto.getId())).thenReturn(Optional.of(new Ad()));
        when(categoryRepository.findById(dto.getCategoryId())).thenReturn(Optional.of(new Category()));
        when(userRepository.findById(dto.getOwnerId())).thenReturn(Optional.of(new User()));
        adService.updateAd(dto);

        verify(adRepository).findById(anyLong());
        verify(userRepository).findById(anyLong());
        verify(categoryRepository).findById(anyLong());
        verify(adRepository).save(any(Ad.class));
    }

    @Test
    void throwExceptionIfAdDoesNotExistWhenUpdatingAd() {
        UpdateAdRequest dto = new UpdateAdRequest();
        dto.setId(1L);
        when(adRepository.findById(dto.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> adService.updateAd(dto));
    }

    @Test
    void throwExceptionIfOwnerDoesNotExistWhenUpdatingAd() {
        UpdateAdRequest dto = new UpdateAdRequest();
        dto.setId(1L);
        dto.setCategoryId(1L);
        dto.setOwnerId(1L);
        when(adRepository.findById(dto.getId())).thenReturn(Optional.of(new Ad()));
        when(categoryRepository.findById(dto.getCategoryId())).thenReturn(Optional.empty());
        when(userRepository.findById(dto.getOwnerId())).thenReturn(Optional.of(new User()));

        assertThrows(NoSuchEntityException.class, () -> adService.updateAd(dto));
    }

    @Test
    void throwExceptionIfCategoryDoesNotExistWhenUpdatingAd() {
        UpdateAdRequest dto = new UpdateAdRequest();
        dto.setId(1L);
        dto.setCategoryId(1L);
        dto.setOwnerId(1L);
        when(adRepository.findById(dto.getId())).thenReturn(Optional.of(new Ad()));
        when(categoryRepository.findById(dto.getCategoryId())).thenReturn(Optional.of(new Category()));
        when(userRepository.findById(dto.getOwnerId())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> adService.updateAd(dto));
    }

    @Test
    void callFindAllWhenGettingAds() {
        adService.getAds(new HashMap<>());

        verify(adRepository).findAll(any(Specification.class), any(Sort.class));
    }

    @Test
    void checkAdExistenceWhenGettingAdById() {
        when(adRepository.findById(anyLong())).thenReturn(Optional.of(new Ad()));

        adService.getAdById(anyLong());

        verify(adRepository).findById(anyLong());
    }

    @Test
    void throwExceptionIfChatDoesNotExistWhenGettingChatById() {
        when(adRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> adService.getAdById(anyLong()));
    }
}