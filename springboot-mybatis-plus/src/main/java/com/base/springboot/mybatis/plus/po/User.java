package com.base.springboot.mybatis.plus.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

/**
 * @author monkjavaer
 * @version V1.0
 * @date 2019/9/1 0001 21:03
 */
@Data
@Builder
public class User {
    @TableId
    private Long userId;
    private String name;
    private Integer age;
    private String email;
}
