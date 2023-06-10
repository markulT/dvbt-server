package com.osmos.server.products.filters;

import org.springframework.stereotype.Component;


/**
 * Implementation of SearchFilter that calculates error based on terrain type
 */
@Component
public class GeoFilter implements SearchFilter {
    @Override
    public double doFilter(double prevRating, SearchParams searchParams) {
        int geoIndex = 20000;
        return prevRating + (geoIndex * searchParams.geo());
    }
}
