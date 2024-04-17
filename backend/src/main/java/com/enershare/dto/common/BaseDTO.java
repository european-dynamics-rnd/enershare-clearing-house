package com.enershare.dto.common;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseDTO implements Serializable {
    private Long id;
}
