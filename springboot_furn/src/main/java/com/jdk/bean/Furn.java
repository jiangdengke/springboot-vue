package com.jdk.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 如果类名和表名不一致，需要在类名上加@Table(name = "表名")
 */
@Data
@TableName(value = "furn")
public class Furn {
    //使用@TableId注解，如果主键是自增的，那么需要加上@TableId(type = IdType.AUTO)
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotEmpty(message = "家居名称不能为空")
    private String name;
    @NotEmpty(message = "家居品牌不能为空")
    private String maker;
    @NotNull(message = "请输入数字")
    @Range(min = 0,message = "不能小于0")
    private double price;
    @NotNull(message = "请输入数字")
    @Range(min = 0,message = "不能小于0")
    private Integer sales;
    @NotNull(message = "请输入数字")
    @Range(min = 0,message = "不能小于0")
    private Integer stock;
}
