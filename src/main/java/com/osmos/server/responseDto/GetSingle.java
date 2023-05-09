package com.osmos.server.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetSingle<T> {
    private T item;
}
