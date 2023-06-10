package com.osmos.server.products.filters;

import org.springframework.stereotype.Component;

/**
 * Implementation of SearchFilter that calculates error based on amount of obstacles
 */
@Component
public class ObstacleFilter implements SearchFilter {

    @Override
    public double doFilter(double prevRating, SearchParams searchParams) {
        int obstacleIndex = 20000;
        return prevRating + (obstacleIndex * searchParams.obstacles());
    }

}
