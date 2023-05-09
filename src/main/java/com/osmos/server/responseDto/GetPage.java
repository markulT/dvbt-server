package com.osmos.server.responseDto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetPage<T> {
    private long length;
    private List<T> page;
}
