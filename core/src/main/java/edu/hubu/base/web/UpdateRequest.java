package edu.hubu.base.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.hubu.base.Model;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("修改")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateRequest<T extends Model, Q extends QueryRequest> {
    private T update;
    private Q where;
}
