package com.m.gis.springboot.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Title: GisAddressTypeValidator
 * @Package: com.m.gis.springboot.validator
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/8/15
 * @Version: V1.0
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GisAddressTypeValidator.class)
@Documented
public @interface GisAddressTypeConstraint {
    String message() default "gis address type is illegal.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
