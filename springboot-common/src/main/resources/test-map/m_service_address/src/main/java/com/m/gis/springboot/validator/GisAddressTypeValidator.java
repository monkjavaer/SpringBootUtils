package com.m.gis.springboot.validator;

import com.m.gis.springboot.enums.GisAddressTypeEnums;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Title: GisAddressTypeValidator
 * @Package: com.m.gis.springboot.validator
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/8/15
 * @Version: V1.0
 */
public class GisAddressTypeValidator implements ConstraintValidator<GisAddressTypeConstraint, String> {
    @Override
    public void initialize(GisAddressTypeConstraint arg0) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1) {
        return GisAddressTypeEnums.isValid(value);
    }

}
