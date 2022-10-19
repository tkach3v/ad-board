package com.tkachev.adboard.rest.parametrs.sort_types;

import com.tkachev.adboard.entity.Ad_;

public enum AdSortType {
    BY_DEFAULT("default"),
    BY_ID(Ad_.ID),
    BY_PRICE(Ad_.PRICE),
    BY_DATE(Ad_.CREATION_DATE),
    BY_SALE_DATE(Ad_.SALE_DATE),
    BY_OWNER_RATING("owner.rating"),
    BY_PROMOTION(Ad_.PROMOTION);

    private final String sortType;

    AdSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSortType() {
        return sortType;
    }
}
