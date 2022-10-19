package com.tkachev.adboard.repositories.specs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Filter {
    private String field;
    private QueryOperator operator;
    private Object value;
}
