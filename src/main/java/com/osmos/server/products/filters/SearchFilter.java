package com.osmos.server.products.filters;

/**
 * Represent general bare filter. SearchHandler searches all filters by this interface
 */
public interface SearchFilter {
    /**
     * @param prevRating Currently calculated rating from previous filter.
     * @param searchParams All possible params for the request
     * @return New rating value after calculations.
     */
    public double doFilter(double prevRating, SearchParams searchParams);

}
