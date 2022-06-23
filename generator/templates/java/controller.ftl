package edu.hubu.auto.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import edu.hubu.auto.model.*;
import edu.hubu.auto.request.query.*;
import edu.hubu.base.Controller;
import edu.hubu.base.Result;
import edu.hubu.base.dao.MongoDao;
import edu.hubu.security.AuthService;
import edu.hubu.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@SuppressWarnings("unused")
@RestController("${model.name}Controller")
@RequestMapping("/api/${u(model.name)}")
@Api(tags = "${model.description}")
public class ${model.name}Controller extends Controller<${model.name}, ${model.name}Query> {

    @Autowired
    private AuthService authService;

    @Autowired
    public void setMongoDao(MongoDao<${model.name}, ${model.name}Query> dao) {
        mongoDao = dao;
    }

    @Autowired
    FileService fileService;
    <#list model.fields as field>
    <#if field.type == "upload">

    @PostMapping(value = "/file/${u(field.name)}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("上传${field.description}")
    public String upload${field.name}(@ApiIgnore User current, @RequestPart("file") MultipartFile file) {
        return fileService.upload(file, "${u(model.name)}/${u(field.name)}/");
    }

    @GetMapping("/file/${u(field.name)}/{fileName}")
    @ApiOperation("下载${field.description}")
    public void download${field.name}(@ApiIgnore User current, @PathVariable String fileName, @ApiIgnore HttpServletResponse response) throws Exception {
        var getObjectResponse = fileService.download("${u(model.name)}/${u(field.name)}/", fileName, response);
        response.getOutputStream().write(getObjectResponse.readAllBytes());
        response.getOutputStream().flush();
    }
    </#if>
    </#list>

    @GetMapping("/distinct/{field}")
    @ApiOperation("分类")
    public Result distinct(@ApiIgnore User current, @PathVariable String field) {
        switch (field) {
        <#list model.fields as field>
            <#if field.type == "string">
            case "${c(field.name)}":
                return Result.ok(mongoDao.distinct(field));
            </#if>
        </#list>
            default:
                return Result.fail("字段不存在");
        }
    }

    @GetMapping()
    @ApiOperation("获取")
    public Result get(
            @ApiIgnore User current,
            @RequestParam(required = false) String id,
            <#list model.fields as field><#assign type = qt(field.type, field.search)><#if type == "String" || type == "Float" || type == "Integer" >
            @RequestParam(required = false) ${type} ${c(field.name)},
            </#if></#list>
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String link
    ) {
        ${model.name}Query query = new ${model.name}Query();
        if (id != null)
            query.setId(List.of(id.split(",")));
        <#list model.fields as field><#assign type = qt(field.type, field.search)><#if type == "String" || type == "Float" || type == "Integer">
        if (${c(field.name)} != null)
            query.set${field.name}(${c(field.name)});
        </#if></#list>
        if (page == null && pageSize == null) {
            authService.restrictQuery(current, "query", query);
            String[] links = link == null ? new String[]{} : link.split(",");
            return Result.ok(mongoDao.find(query, links));
        } else {
            authService.restrictQuery(current, "query", query);
            page = page == null || page < 0 ? 0 : page;
            pageSize = pageSize == null || pageSize < 1 || pageSize > 100 ? defaultPageSize : pageSize;
            String[] links = link == null ? new String[]{} : link.split(",");
            return Result.ok(mongoDao.find(query, links, page, pageSize));
        }
    }

    @GetMapping("/one")
    @ApiOperation("获取单个")
    public Result get(
            @ApiIgnore User current,
            @RequestParam(required = false) String id,
            <#list model.fields as field><#assign type = qt(field.type, field.search)><#if type == "String" || type == "Float" || type == "Integer" >
            @RequestParam(required = false) ${type} ${c(field.name)},
            </#if></#list>
            @RequestParam(required = false) String link
    ) {
        ${model.name}Query query = new ${model.name}Query();
        if (id != null)
            query.setId(List.of(id.split(",")));
        <#list model.fields as field><#assign type = qt(field.type, field.search)><#if type == "String" || type == "Float" || type == "Integer">
        if (${c(field.name)} != null)
            query.set${field.name}(${c(field.name)});
        </#if></#list>
        authService.restrictQuery(current, "query", query);
        String[] links = link == null ? new String[]{} : link.split(",");
        return Result.ok(mongoDao.findOne(query, links));
    }
}