package edu.hubu.generator.define;

import lombok.Data;

@Data
public class Validation {
    boolean nullable; // 允许null，限定id、select类型
    boolean emptiable; // 允许empty，限定string、array、map类型
    boolean unique; // list唯一
    String defaultValue; // 默认值
    Float minValue; // 最小值
    Float maxValue; // 最大值
    Integer length; // 文本长度
    String regex; // 正则校验
}
