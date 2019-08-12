package com.base.springboot.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author monkjavaer
 * @version V1.0
 * @date 2019/8/12 0012 23:40
 */
@Data
@Builder
public class Student {
    private Integer id;
    private String name;
    private Integer age;
}
