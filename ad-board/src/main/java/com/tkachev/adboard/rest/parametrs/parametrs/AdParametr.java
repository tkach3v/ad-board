package com.tkachev.adboard.rest.parametrs.parametrs;

public enum AdParametr {
    TEXT("text"),
    STATUS("status"),
    PRICE("price"),
    CATEGORY("category"),
    OWNER("owner"),
    SORT_TYPE("sort-type"),
    SORT_ORDER("sort-order");

    private final String parametr;

    AdParametr(String parametr) {
        this.parametr = parametr;
    }

    public String getParametr() {
        return parametr;
    }
}
