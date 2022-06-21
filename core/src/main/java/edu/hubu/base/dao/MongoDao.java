package edu.hubu.base.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.multipart.MultipartFile;

import edu.hubu.base.Model;
import edu.hubu.base.web.QueryRequest;
import edu.hubu.base.web.RequestBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class MongoDao<T extends Model, Q extends QueryRequest> {

    @SuppressWarnings("unchecked")
    private final Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    /**
     * 数据请求构建器
     */
    protected RequestBuilder<T, Q> requestBuilder;

    /**
     * Mongo数据操作
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    public abstract Collection<T> input(List<List<String>> matrix);

    public Collection<T> input(MultipartFile file) throws Exception {
        var book = new XSSFWorkbook(file.getInputStream());
        var sheet = book.getSheetAt(0);
        var matrix = new ArrayList<List<String>>();
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            var row = sheet.getRow(i);
            var cells = new ArrayList<String>();
            for (int j = 0; j < row.getLastCellNum(); j++) {
                var cell = row.getCell(j);
                if (cell != null) {
                    cells.add(cell.toString());
                } else {
                    cells.add("");
                }
            }
            matrix.add(cells);
        }
        book.close();
        return input(matrix);
    }

    public Collection<T> input(InputStream stream) {
        var matrix = new ArrayList<List<String>>();
        try {
            var reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            if (stream != null && count() == 0) {
                try {
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        matrix.add(List.of(line.split(",")));
                    }
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return input(matrix);
    }

    public void link(String link, List<T> list) {
    }

    public T findOne(String id) {
        return findOne(id, new String[] {});
    }

    public T findOne(String id, String[] links) {
        return findOne(new Query().addCriteria(Criteria.where("id").is(id)), links);
    }

    public T findOne(Query query) {
        return findOne(query, new String[] {});
    }

    public T findOne(Query query, String[] links) {
        var result = find(query, links);
        if (result.size() > 0)
            return result.get(0);
        else
            return null;
    }

    public List<T> find() {
        return find(new Query(), new String[] {});
    }

    public List<T> find(String[] links) {
        return find(new Query(), links);
    }

    public List<T> find(Set<String> ids, String[] links) {
        return find(new Query().addCriteria(Criteria.where("id").in(ids)), links);
    }

    public List<T> find(Q q, String[] links) {
        return find(requestBuilder.buildQuery(q), links);
    }

    public List<T> find(Q q, String[] links, int page, int pageSize) {
        return find(requestBuilder.buildQuery(q).limit(pageSize).skip(pageSize * (page - 1L))
                .with(Sort.by(Sort.Direction.DESC, "id")), links);
    }

    public List<T> find(Q q, String[] links, int page, int pageSize, String sort, boolean direction) {
        return find(requestBuilder.buildQuery(q).limit(pageSize).skip(pageSize * (page - 1L))
                .with(Sort.by(direction ? Sort.Direction.ASC : Sort.Direction.DESC, sort)), links);
    }

    public List<T> find(Query query, String[] links) {
        var result = mongoTemplate.find(query, clazz);
        for (String link : links) {
            link(link, result);
        }
        return result;
    }

    public Long count() {
        return count(new Query());
    }

    public Long count(Q q) {
        return count(requestBuilder.buildQuery(q));
    }

    public Long count(Query query) {
        return mongoTemplate.count(query, clazz);
    }

    public Long updateOne(String id, T t) {
        return updateOne(new Query().addCriteria(Criteria.where("id").is(id)), t);
    }

    public Long updateOne(Q q, T t) {
        return updateOne(requestBuilder.buildQuery(q), t);
    }

    public Long updateOne(Query query, T t) {
        return mongoTemplate.updateFirst(query, requestBuilder.buildUpdate(t), clazz).getModifiedCount();
    }

    public Long update(Q q, T t) {
        return update(requestBuilder.buildQuery(q), t);
    }

    public Long update(Query query, T t) {
        return mongoTemplate.updateMulti(query, requestBuilder.buildUpdate(t), clazz).getModifiedCount();
    }

    public Long trash(Q q) {
        return trash(requestBuilder.buildQuery(q));
    }

    public Long restore(Q q) {
        return restore(requestBuilder.buildQuery(q));
    }

    public Long trash(Query query) {
        var now = new Date();
        Update update = new Update();
        update.set("deleteTime", now);
        update.set("updateTime", now);
        return mongoTemplate.updateMulti(query, update, clazz).getModifiedCount();
    }

    public Long restore(Query query) {
        Update update = new Update();
        update.unset("deleteTime");
        update.set("updateTime", new Date());
        return mongoTemplate.updateMulti(query, update, clazz).getModifiedCount();
    }

    public Long delete(Q q) {
        return delete(requestBuilder.buildQuery(q));
    }

    public Long delete(Query query) {
        return mongoTemplate.remove(query, clazz).getDeletedCount();
    }

    public T add(T t) {
        return add(t, new String[] {});
    }

    public T add(T t, String[] links) {
        requestBuilder.buildInsert(t);
        var result = List.of(mongoTemplate.insert(t));
        for (String link : links) {
            link(link, result);
        }
        return result.get(0);
    }

    public Collection<T> add(Collection<T> collection) {
        return add(collection, new String[] {});
    }

    public Collection<T> add(Collection<T> collection, String[] links) {
        for (T t : collection) {
            requestBuilder.buildInsert(t);
        }
        var result = mongoTemplate.insert(collection, clazz);
        var array = new ArrayList<T>();
        for (T t : result) {
            array.add(t);
        }
        for (String link : links) {
            link(link, array);
        }
        return array;
    }

    public List<String> distinct(String field) {
        return mongoTemplate.findDistinct(field, clazz, String.class);
    }
}
