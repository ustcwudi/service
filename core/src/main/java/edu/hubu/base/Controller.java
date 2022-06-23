package edu.hubu.base;

import edu.hubu.auto.model.User;
import edu.hubu.base.dao.MongoDao;
import edu.hubu.base.web.QueryRequest;
import edu.hubu.base.web.UpdateRequest;
import edu.hubu.security.AuthService;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;

public class Controller<T extends Model, Q extends QueryRequest> {

    /**
     * 默认分页大小
     */
    protected final int defaultPageSize = 10;

    protected MongoDao<T, Q> mongoDao;

    @Autowired
    private AuthService authService;

    @PostMapping()
    @ApiOperation("新增")
    public Result add(@ApiIgnore User current, @RequestBody T t,
            @RequestHeader(value = "link", required = false) String link) {
        authService.restrictModel(current, "add", t);
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.add(t, links));
    }

    @PostMapping("/multi")
    @ApiOperation("批量新增")
    public Result add(@ApiIgnore User current, @RequestBody Collection<T> collection,
            @RequestHeader(value = "link", required = false) String link) {
        for (var item : collection) {
            authService.restrictModel(current, "add", item);
        }
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.add(collection, links));
    }

    @PostMapping("/one")
    @ApiOperation("查询单个")
    public Result queryOne(@ApiIgnore User current, @RequestBody Q q,
            @RequestHeader(value = "link", required = false) String link) {
        authService.restrictQuery(current, "query", q);
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.findOne(q, links));
    }

    @PutMapping("/one")
    @ApiOperation("修改单个")
    public Result updateOne(@ApiIgnore User current, @RequestBody UpdateRequest<T, Q> r) {
        authService.restrictQuery(current, "query", r.getWhere());
        authService.restrictModel(current, "update", r.getUpdate());
        return Result.ok(mongoDao.updateOne(r.getWhere(), r.getUpdate()));
    }

    @PostMapping("/count")
    @ApiOperation("统计")
    public Result count(@ApiIgnore User current, @RequestBody Q q) {
        authService.restrictQuery(current, "count", q);
        return Result.ok(mongoDao.count(q));
    }

    @PutMapping("/trash")
    @ApiOperation("废弃")
    public Result trash(@ApiIgnore User current, @RequestBody Q q) {
        authService.restrictQuery(current, "trash", q);
        return Result.ok(mongoDao.trash(q));
    }

    @PutMapping("/restore")
    @ApiOperation("还原")
    public Result restore(@ApiIgnore User current, @RequestBody Q q) {
        authService.restrictQuery(current, "restore", q);
        return Result.ok(mongoDao.restore(q));
    }

    @PostMapping("/query")
    @ApiOperation("查询")
    public Result query(@ApiIgnore User current, @RequestBody Q q,
            @RequestHeader(value = "link", required = false) String link) {
        authService.restrictQuery(current, "query", q);
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.find(q, links));
    }

    @PostMapping("query/{page}/{pageSize}")
    @ApiOperation("分页查询")
    public Result query(@ApiIgnore User current, @RequestBody Q q,
            @RequestHeader(value = "link", required = false) String link, @PathVariable int page,
            @PathVariable int pageSize) {
        authService.restrictQuery(current, "query", q);
        page = page < 0 ? 0 : page;
        pageSize = pageSize < 1 || pageSize > 100 ? defaultPageSize : pageSize;
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.find(q, links, page, pageSize));
    }

    @PostMapping("query/{sort}/{direction}/{page}/{pageSize}")
    @ApiOperation("分页排序查询")
    public Result query(@ApiIgnore User current, @RequestBody Q q,
            @RequestHeader(value = "link", required = false) String link, @PathVariable int page,
            @PathVariable int pageSize, @PathVariable String sort, @PathVariable String direction) {
        authService.restrictQuery(current, "query", q);
        page = page < 0 ? 0 : page;
        pageSize = pageSize < 1 || pageSize > 100 ? defaultPageSize : pageSize;
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.find(q, links, page, pageSize, sort, direction.equals("asc")));
    }

    @DeleteMapping()
    @ApiOperation("删除")
    public Result delete(@ApiIgnore User current, @RequestBody Q q) {
        authService.restrictQuery(current, "delete", q);
        return Result.ok(mongoDao.delete(q));
    }

    @PutMapping()
    @ApiOperation("修改")
    public Result update(@ApiIgnore User current, @RequestBody UpdateRequest<T, Q> r) {
        authService.restrictModel(current, "update", r.getUpdate());
        authService.restrictQuery(current, "update", r.getWhere());
        return Result.ok(mongoDao.update(r.getWhere(), r.getUpdate()));
    }

    @PostMapping(value = "/import")
    @ApiOperation("导入")
    public Result in(@ApiIgnore User current, @RequestPart("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return Result.fail("空文件");
        }
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (suffix.equals(".xlsx")) {
            var list = mongoDao.input(file);
            for (var item : list) {
                authService.restrictModel(current, "add", item);
            }
            return Result.ok(mongoDao.add(list));
        } else {
            return Result.fail("文件类型错误");
        }
    }
}
