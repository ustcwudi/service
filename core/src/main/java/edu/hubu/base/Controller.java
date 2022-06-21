package edu.hubu.base;

import edu.hubu.auto.model.User;
import edu.hubu.base.dao.MongoDao;
import edu.hubu.base.web.QueryRequest;
import edu.hubu.base.web.UpdateRequest;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping()
    @ApiOperation("新增")
    public Result add(@ApiIgnore User current, @RequestBody T t,
            @RequestHeader(value = "link", required = false) String link) {
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.add(t, links));
    }

    @PostMapping("/multi")
    @ApiOperation("批量新增")
    public Result add(@ApiIgnore User current, @RequestBody Collection<T> collection,
            @RequestHeader(value = "link", required = false) String link) {
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.add(collection, links));
    }

    @GetMapping("/id/{id}")
    @ApiOperation("根据ID获取")
    public Result get(@ApiIgnore User current, @PathVariable String id,
            @RequestHeader(value = "link", required = false) String link) {
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.findOne(id, links));
    }

    @PutMapping("/id/{id}")
    @ApiOperation("根据ID修改")
    public Result update(@ApiIgnore User current, @PathVariable String id, @RequestBody T t) {
        return Result.ok(mongoDao.updateOne(id, t));
    }

    @PostMapping("/count")
    @ApiOperation("统计")
    public Result count(@ApiIgnore User current, @RequestBody Q q) {
        return Result.ok(mongoDao.count(q));
    }

    @PutMapping("/trash")
    @ApiOperation("废弃")
    public Result trash(@ApiIgnore User current, @RequestBody Q q) {
        return Result.ok(mongoDao.trash(q));
    }

    @PutMapping("/restore")
    @ApiOperation("还原")
    public Result restore(@ApiIgnore User current, @RequestBody Q q) {
        return Result.ok(mongoDao.restore(q));
    }

    @PostMapping("/query")
    @ApiOperation("查询")
    public Result query(@ApiIgnore User current, @RequestBody Q q,
            @RequestHeader(value = "link", required = false) String link) {
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.find(q, links));
    }

    @PostMapping("query/{page}/{pageSize}")
    @ApiOperation("分页查询")
    public Result query(@ApiIgnore User current, @RequestBody Q q,
            @RequestHeader(value = "link", required = false) String link, @PathVariable int page,
            @PathVariable int pageSize) {
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
        page = page < 0 ? 0 : page;
        pageSize = pageSize < 1 || pageSize > 100 ? defaultPageSize : pageSize;
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.find(q, links, page, pageSize, sort, direction.equals("asc")));
    }

    @DeleteMapping()
    @ApiOperation("删除")
    public Result delete(@ApiIgnore User current, @RequestBody Q q) {
        return Result.ok(mongoDao.delete(q));
    }

    @PutMapping()
    @ApiOperation("修改")
    public Result update(@ApiIgnore User current, @RequestBody UpdateRequest<T, Q> r) {
        return Result.ok(mongoDao.update(r.getWhere(), r.getUpdate()));
    }

    @PostMapping(value = "/upload")
    @ApiOperation("导入")
    public Result upload(@ApiIgnore User current, @RequestPart("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return Result.fail("空文件");
        }
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (suffix.equals(".xlsx")) {
            return Result.ok(mongoDao.input(file));
        } else {
            return Result.fail("文件类型错误");
        }
    }
}
