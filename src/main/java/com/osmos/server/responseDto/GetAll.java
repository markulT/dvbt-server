package com.osmos.server.responseDto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAll<T> {
    private List<T> list;
}
