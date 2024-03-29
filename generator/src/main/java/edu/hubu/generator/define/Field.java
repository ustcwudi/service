package edu.hubu.generator.define;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class Field extends Validation {
    String name; // 名称
    String type; // 类型，bool、id、id[]、upload、upload[]、int、int[]、float、float[]、string、string[]共11种
    String description; // 说明
    boolean show; // 展示
    String link; // 外链，id类型必定有外链，没有外链的请用string代替
    SearchType search; // 查询类型
    Map<String, String> map; // 值映射
}
