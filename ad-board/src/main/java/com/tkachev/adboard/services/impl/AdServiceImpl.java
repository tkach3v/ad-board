package com.tkachev.adboard.services.impl;

import com.tkachev.adboard.dto.models.ad.AdResponse;
import com.tkachev.adboard.dto.models.ad.CreateAdRequest;
import com.tkachev.adboard.dto.models.ad.UpdateAdRequest;
import com.tkachev.adboard.entity.Ad;
import com.tkachev.adboard.entity.AdStatus;
import com.tkachev.adboard.entity.Ad_;
import com.tkachev.adboard.entity.Category;
import com.tkachev.adboard.entity.Category_;
import com.tkachev.adboard.entity.User;
import com.tkachev.adboard.entity.User_;
import com.tkachev.adboard.exception_handling.exceptions.ParametrException;
import com.tkachev.adboard.dto.mappers.AdMapper;
import com.tkachev.adboard.repositories.AdRepository;
import com.tkachev.adboard.repositories.CategoryRepository;
import com.tkachev.adboard.repositories.UserRepository;
import com.tkachev.adboard.repositories.specs.AppSpecification;
import com.tkachev.adboard.repositories.specs.Filter;
import com.tkachev.adboard.repositories.specs.QueryOperator;
import com.tkachev.adboard.rest.parametrs.parametrs.AdParametr;
import com.tkachev.adboard.rest.parametrs.sort_types.AdSortType;
import com.tkachev.adboard.services.AbstractService;
import com.tkachev.adboard.services.AdService;
import com.tkachev.adboard.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdServiceImpl extends AbstractService implements AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final AdMapper adMapper;

    @Override
    public AdResponse createAd(CreateAdRequest dto) {
        Ad ad = adMapper.toAd(dto);
        linkOwnerAndCategory(dto.getOwnerId(), dto.getCategoryId(), ad);
        ad.setStatus(Objects.requireNonNullElse(ad.getStatus(), AdStatus.ACTIVE));
        ad.setPromotion(Objects.requireNonNullElse(ad.getPromotion(), false));
        adRepository.save(ad);

        return adMapper.toAdResponse(ad);
    }

    @Override
    public void deleteAd(Long id) {
        Ad ad = adRepository.findById(id).orElse(null);
        ad = isNotNull(ad, "Ad", id);
        adRepository.delete(ad);
    }

    @Override
    public AdResponse updateAd(UpdateAdRequest dto) {
        Ad ad = adRepository.findById(dto.getId()).orElse(null);
        ad = isNotNull(ad, "Ad", dto.getId());
        adMapper.updateAd(dto, ad);
        linkOwnerAndCategory(dto.getOwnerId(), dto.getCategoryId(), ad);
        adRepository.save(ad);

        return adMapper.toAdResponse(ad);
    }

    @Override
    public List<AdResponse> getAds(Map<String, String> parameters) {
        Specification<Ad> specification = parametersToSpecification(parameters);
        Sort sort = parametersToSort(parameters);

        return adRepository.findAll(specification, sort).stream().
                map(adMapper::toAdResponse).
                toList();
    }

    @Override
    public AdResponse getAdById(Long id) {
        Ad ad = adRepository.findById(id).orElse(null);
        ad = isNotNull(ad, "Ad", id);

        return adMapper.toAdResponse(ad);
    }

    private void linkOwnerAndCategory(Long ownerId, Long categoryId, Ad ad) {
        User user = userRepository.findById(ownerId).orElse(null);
        user = isNotNull(user, "User", ownerId);
        Category category = categoryRepository.findById(categoryId).orElse(null);
        category = isNotNull(category, "Category", categoryId);
        ad.setOwner(user);
        ad.setCategory(category);
    }

    private Specification<Ad> parametersToSpecification(Map<String, String> parameters) {
        Specification<Ad> specification = new AppSpecification<>();
        //filtering
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (entry.getKey().equals(AdParametr.TEXT.getParametr())) {
                //by text in title or description
                AppSpecification<Ad> spTitle = new AppSpecification<>();
                spTitle.add(new Filter(Ad_.TITLE, QueryOperator.MATCH, entry.getValue()));
                AppSpecification<Ad> spDescription = new AppSpecification<>();
                spDescription.add(new Filter(Ad_.DESCRIPTION, QueryOperator.MATCH, entry.getValue()));
                specification = specification.and(spTitle.or(spDescription));
            } else if (entry.getKey().equals(AdParametr.PRICE.getParametr())) {
                //by starting and final price
                String[] prices = entry.getValue().split("-");
                if (prices == null || prices.length != 2 ||
                        Util.parseDoubleOrNull(prices[0]) == null || Util.parseDoubleOrNull(prices[1]) == null) {
                    throw new ParametrException("You must enter the price range as startingPrice-finalPrice!");
                }
                AppSpecification<Ad> spPrice = new AppSpecification<>();
                spPrice.add(new Filter(Ad_.PRICE, QueryOperator.GREATER_THAN_EQUAL, prices[0]));
                spPrice.add(new Filter(Ad_.PRICE, QueryOperator.LESS_THAN_EQUAL, prices[1]));
                specification = specification.and(spPrice);
            } else if (entry.getKey().equals(AdParametr.STATUS.getParametr())) {
                //by ad status
                try {
                    AdStatus.valueOf(entry.getValue());
                } catch (Exception ex) {
                    throw new ParametrException("You must enter the AdStatus enum part!");
                }
                AppSpecification<Ad> spStatus = new AppSpecification<>();
                spStatus.add(new Filter(Ad_.STATUS, QueryOperator.EQUAL, AdStatus.valueOf(entry.getValue())));
                specification = specification.and(spStatus);
            } else if (entry.getKey().equals(AdParametr.CATEGORY.getParametr())) {
                //by category id
                if (Util.parseLongOrNull(entry.getValue()) == null) {
                    throw new ParametrException("You must enter a valid category ID!");
                }
                Specification<Ad> spCategory = (root, query, builder) -> {
                    Join<Object, Object> category = root.join(Ad_.CATEGORY);
                    return builder.equal(category.get(Category_.ID), entry.getValue());
                };
                specification = specification.and(spCategory);
            } else if (entry.getKey().equals(AdParametr.OWNER.getParametr())) {
                //by owner id
                if (Util.parseLongOrNull(entry.getValue()) == null) {
                    throw new ParametrException("You must enter a valid category ID!");
                }
                Specification<Ad> spOwner = (root, query, builder) -> {
                    Join<Object, Object> owner = root.join(Ad_.OWNER);
                    return builder.equal(owner.get(User_.ID), entry.getValue());
                };
                specification = specification.and(spOwner);
            }
        }

        return specification;
    }

    private Sort parametersToSort(Map<String, String> parameters) {
        //sorting
        Sort sort = Sort.unsorted();
        String sortType = parameters.get(AdParametr.SORT_TYPE.getParametr());
        boolean isDefaultSort = (sortType == null) || (sortType.equals(AdSortType.BY_DEFAULT.toString()));
        if (isDefaultSort) {
            sort = sort.and(Sort.by(AdSortType.BY_PROMOTION.getSortType())).
                    and(Sort.by(AdSortType.BY_OWNER_RATING.getSortType()));
        } else if (sortType.equals(AdSortType.BY_ID.toString())) {
            sort = sort.and(Sort.by(AdSortType.BY_ID.getSortType()));
        } else if (sortType.equals(AdSortType.BY_PRICE.toString())) {
            sort = sort.and(Sort.by(AdSortType.BY_PRICE.getSortType()));
        } else if (sortType.equals(AdSortType.BY_DATE.toString())) {
            sort = sort.and(Sort.by(AdSortType.BY_DATE.getSortType()));
        } else if (sortType.equals(AdSortType.BY_SALE_DATE.toString())) {
            sort = sort.and(Sort.by(AdSortType.BY_SALE_DATE.getSortType()));
        } else if (sortType.equals(AdSortType.BY_OWNER_RATING.toString())) {
            sort = sort.and(Sort.by(AdSortType.BY_OWNER_RATING.getSortType()));
        } else if (sortType.equals(AdSortType.BY_PROMOTION.toString())) {
            sort = sort.and(Sort.by(AdSortType.BY_PROMOTION.getSortType()));
        } else {
            throw new ParametrException("Enter a valid sort type!");
        }

        //order
        String sortOrder = parameters.get(AdParametr.SORT_ORDER.getParametr());
        if (sortOrder == null) {
            sort = sort.descending();
        } else if (sortOrder.equals(Sort.Direction.ASC.toString())) {
            sort = sort.ascending();
        } else if (sortOrder.equals(Sort.Direction.DESC.toString()) || isDefaultSort) {
            sort = sort.descending();
        } else {
            throw new ParametrException("Enter a valid order type!");
        }

        return sort;
    }
}
