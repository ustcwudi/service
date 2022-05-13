package edu.hubu.generator.define;

import lombok.Data;

import java.util.Collection;

@Data
public class Model {

    /**
     * 名称
     */
    private String name;

    /**
     * 说明
     */
    private String description;

    /**
     * 字段
     */
    private Collection<Field> fields;
}
