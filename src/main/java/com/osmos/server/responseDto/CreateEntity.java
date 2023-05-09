package com.osmos.server.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateEntity<T> {

    private T entity;

}
