package edu.hubu.base;

import edu.hubu.base.dao.MongoDao;
import edu.hubu.base.web.QueryRequest;
import edu.hubu.base.web.UpdateRequest;
import edu.hubu.security.Token;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
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
    public Result add(@ApiIgnore Token token, @RequestBody T t,
            @RequestHeader(value = "link", required = false) String link) {
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.add(t, links));
    }

    @PostMapping("/multi")
    @ApiOperation("批量新增")
    public Result add(@ApiIgnore Token token, @RequestBody Collection<T> collection,
            @RequestHeader(value = "link", required = false) String link) {
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.add(collection, links));
    }

    @GetMapping("/id/{id}")
    @ApiOperation("根据ID获取")
    public Result get(@ApiIgnore Token token, @PathVariable String id,
            @RequestHeader(value = "link", required = false) String link) {
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.findOne(id, links));
    }

    @PutMapping("/id/{id}")
    @ApiOperation("根据ID修改")
    public Result update(@ApiIgnore Token token, @PathVariable String id, @RequestBody T t) {
        return Result.ok(mongoDao.updateOne(id, t));
    }

    @PostMapping("/count")
    @ApiOperation("统计")
    public Result count(@ApiIgnore Token token, @RequestBody Q q) {
        return Result.ok(mongoDao.count(q));
    }

    @PutMapping("/trash")
    @ApiOperation("废弃")
    public Result trash(@ApiIgnore Token token, @RequestBody Q q) {
        return Result.ok(mongoDao.trash(q));
    }

    @PutMapping("/restore")
    @ApiOperation("还原")
    public Result restore(@ApiIgnore Token token, @RequestBody Q q) {
        return Result.ok(mongoDao.restore(q));
    }

    @PostMapping("/query")
    @ApiOperation("查询")
    public Result query(@ApiIgnore Token token, @RequestBody Q q,
            @RequestHeader(value = "link", required = false) String link) {
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.find(q, links));
    }

    @PostMapping("query/{page}/{pageSize}")
    @ApiOperation("分页查询")
    public Result query(@ApiIgnore Token token, @RequestBody Q q,
            @RequestHeader(value = "link", required = false) String link, @PathVariable int page,
            @PathVariable int pageSize) {
        page = page < 0 ? 0 : page;
        pageSize = pageSize < 1 || pageSize > 100 ? defaultPageSize : pageSize;
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.find(q, links, page, pageSize));
    }

    @PostMapping("query/{sort}/{direction}/{page}/{pageSize}")
    @ApiOperation("分页排序查询")
    public Result query(@ApiIgnore Token token, @RequestBody Q q,
            @RequestHeader(value = "link", required = false) String link, @PathVariable int page,
            @PathVariable int pageSize, @PathVariable String sort, @PathVariable String direction) {
        page = page < 0 ? 0 : page;
        pageSize = pageSize < 1 || pageSize > 100 ? defaultPageSize : pageSize;
        String[] links = link == null ? new String[] {} : link.split(",");
        return Result.ok(mongoDao.find(q, links, page, pageSize, sort, direction.equals("asc")));
    }

    @DeleteMapping()
    @ApiOperation("删除")
    public Result delete(@ApiIgnore Token token, @RequestBody Q q) {
        return Result.ok(mongoDao.delete(q));
    }

    @PutMapping()
    @ApiOperation("修改")
    public Result update(@ApiIgnore Token token, @RequestBody UpdateRequest<T, Q> r) {
        return Result.ok(mongoDao.update(r.getWhere(), r.getUpdate()));
    }
}
