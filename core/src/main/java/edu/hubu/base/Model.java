package edu.hubu.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("基本模型")
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Model implements Serializable {
    @Id
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 删除时间
     */
    private Date deleteTime;
}
