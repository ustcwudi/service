package edu.hubu.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("结果")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    /**
     * 数据
     */
    private Object data;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 消息
     */
    private String message;

    /**
     * 错误代码
     */
    private Integer code;

    /**
     * 关联数据
     */
    private Map<String, List<Model>> map;

    public static <T> Result ok(T data) {
        return Result.builder().success(true).code(0).message(null).data(data).build();
    }

    public static Result fail(String message) {
        return Result.builder().success(false).code(-1).message(message).build();
    }
}
