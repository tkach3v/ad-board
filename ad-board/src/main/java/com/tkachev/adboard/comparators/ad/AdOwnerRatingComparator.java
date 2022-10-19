package com.tkachev.adboard.comparators.ad;

import com.tkachev.adboard.entity.Ad;

import java.util.Comparator;

public class AdOwnerRatingComparator implements Comparator<Ad> {
    @Override
    public int compare(Ad o1, Ad o2) {
        return o1.getOwner().getRating().compareTo(o2.getOwner().getRating());
    }
}
