package com.ogunheper.testcontainers.demo.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IdResponse<T> {

    private final T id;
}
