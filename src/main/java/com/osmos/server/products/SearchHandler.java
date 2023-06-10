package com.osmos.server.products;

import com.osmos.server.products.filters.SearchFilter;
import com.osmos.server.products.filters.SearchParams;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

/**
 * Handles search among products and calculates the most valid search rating.
 * Which include required power of product based on user conditions
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
public class SearchHandler {

    /**
     * Filters that calculate error(похибку) and add it to the result
     */
    private Set<Class<?>> filterList;

    /**
     * Collects all classes that implement {@link com.osmos.server.products.filters.SearchFilter} interface.
     * And sets them in {@link SearchHandler#filterList} field
     */
    @PostConstruct
    public void init() {
        Class<?> filterClass = SearchFilter.class;
        Reflections reflections = new Reflections("com.osmos.server.products.filters");
        filterList = reflections.getSubTypesOf((Class<Object>) filterClass);
    }


    /**
     * @param searchParams All possible params for the request
     * @return Rating value which specifies the quality of required product
     */
    public double search(SearchParams searchParams) {
        try {

            double rating = searchParams.distance();

            for (Class<?> filter : filterList) {
                SearchFilter instance = (SearchFilter) filter.getDeclaredConstructor().newInstance();
                double temp = rating;
                rating = instance.doFilter(temp, searchParams);
            }

            return rating;

        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Error in search");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Error creating an instance of filter");
        }
    }

}
