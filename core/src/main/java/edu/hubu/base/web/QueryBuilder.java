package edu.hubu.base.web;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import java.util.List;
import java.util.Map;

/**
 * 根据查询字段、查询参数和查询类型生成查询语句，排除id与string类型重复，总共27个case
 */
public class QueryBuilder {

    public CriteriaDefinition buildNull(String key) {
        return Criteria.where(key).exists(false);
    }

    /**
     * 查询布尔类型
     * 
     * @param key   字段名
     * @param value 查询参数（布尔）
     * @param type  查询类型（相等）
     * @return
     */
    public CriteriaDefinition buildBool(String key, Boolean value, QueryType type) {
        switch (type) {
            case equal:
                return Criteria.where(key).is(value);
            default:
                return null;
        }
    }

    /**
     * 查询整数类型
     * 
     * @param key   字段名
     * @param value 查询参数（整数）
     * @param type  查询类型（相等）
     * @return
     */
    public CriteriaDefinition buildInt(String key, Integer value, QueryType type) {
        switch (type) {
            case equal:
                return Criteria.where(key).is(value);
            default:
                return null;
        }
    }

    /**
     * 查询整数类型
     * 
     * @param key   字段名
     * @param value 查询参数（整数数组）
     * @param type  查询类型（包含，范围）
     * @return
     */
    public CriteriaDefinition buildInt(String key, List<Integer> value, QueryType type) {
        switch (type) {
            case in:
                return Criteria.where(key).in(value);
            case between:
                Criteria criteria = new Criteria();
                if (value.size() > 1) {
                    Integer min = value.get(0);
                    Integer max = value.get(1);
                    if (min != null)
                        criteria.andOperator(Criteria.where(key).gte(min));
                    if (max != null)
                        criteria.andOperator(Criteria.where(key).lte(max));
                }
                return criteria;
            default:
                return null;
        }
    }

    /**
     * 查询整数数组类型
     * 
     * @param key   字段名
     * @param value 查询参数（整数）
     * @param type  查询类型（相等）
     * @return
     */
    public CriteriaDefinition buildIntArray(String key, Integer value, QueryType type) {
        switch (type) {
            case equal:
                return Criteria.where(key).is(value);
            default:
                return null;
        }
    }

    /**
     * 查询整数数组类型
     * 
     * @param key   字段名
     * @param value 查询参数（整数数组）
     * @param type  查询类型（包含、范围）
     * @return
     */
    public CriteriaDefinition buildIntArray(String key, List<Integer> value, QueryType type) {
        switch (type) {
            case in:
                return Criteria.where(key).in(value);
            case between:
                Criteria criteria = new Criteria();
                if (value.size() > 1) {
                    Integer left = value.get(0);
                    Integer right = value.get(1);
                    if (left != null)
                        criteria.andOperator(Criteria.where(key).gte(left));
                    if (right != null)
                        criteria.andOperator(Criteria.where(key).lte(right));
                }
                return criteria;
            default:
                return null;
        }
    }

    /**
     * 查询整数键值对类型
     * 
     * @param key   字段名
     * @param value 查询参数（整数键值对）
     * @param type  查询类型（相等）
     * @return
     */
    public CriteriaDefinition buildIntMap(String key, Map<String, Integer> value, QueryType type) {
        Criteria criteria = new Criteria();
        for (Map.Entry<String, Integer> entry : value.entrySet()) {
            switch (type) {
                case equal:
                    criteria.andOperator(Criteria.where(key + '.' + entry.getKey()).is(entry.getValue()));
                    break;
                default:
                    return null;
            }
        }
        return criteria;
    }

    /**
     * 查询整数数组键值对类型
     * 
     * @param key   字段名
     * @param value 查询参数（整数键值对）
     * @param type  查询类型（相等）
     * @return
     */
    public CriteriaDefinition buildIntArrayMap(String key, Map<String, Integer> value, QueryType type) {
        Criteria criteria = new Criteria();
        for (Map.Entry<String, Integer> entry : value.entrySet()) {
            switch (type) {
                case equal:
                    criteria.andOperator(Criteria.where(key + '.' + entry.getKey()).is(entry.getValue()));
                    break;
                default:
                    return null;
            }
        }
        return criteria;
    }

    /**
     * 查询浮点数类型
     * 
     * @param key   字段名
     * @param value 查询参数（浮点数）
     * @param type  查询类型（相等）
     * @return
     */
    public CriteriaDefinition buildFloat(String key, Float value, QueryType type) {
        switch (type) {
            case equal:
                return Criteria.where(key).is(value);
            default:
                return null;
        }
    }

    /**
     * 查询浮点数类型
     * 
     * @param key   字段名
     * @param value 查询参数（浮点数）
     * @param type  查询类型（包含，范围）
     * @return
     */
    public CriteriaDefinition buildFloat(String key, List<Float> value, QueryType type) {
        switch (type) {
            case in:
                return Criteria.where(key).in(value);
            case between:
                Criteria criteria = new Criteria();
                if (value.size() > 1) {
                    Float min = value.get(0);
                    Float max = value.get(1);
                    if (min != null)
                        criteria.andOperator(Criteria.where(key).gte(min));
                    if (max != null)
                        criteria.andOperator(Criteria.where(key).lte(max));
                }
                return criteria;
            default:
                return null;
        }
    }

    /**
     * 查询浮点数数组类型
     * 
     * @param key   字段名
     * @param value 查询参数（浮点数数组）
     * @param type  查询类型（包含、范围）
     * @return
     */
    public CriteriaDefinition buildFloatArray(String key, List<Float> value, QueryType type) {
        switch (type) {
            case in:
                return Criteria.where(key).in(value);
            case between:
                Criteria criteria = new Criteria();
                if (value.size() > 1) {
                    Float min = value.get(0);
                    Float max = value.get(1);
                    if (min != null)
                        criteria.andOperator(Criteria.where(key).gte(min));
                    if (max != null)
                        criteria.andOperator(Criteria.where(key).lte(max));
                }
                return criteria;
            default:
                return null;
        }
    }

    /**
     * 查询浮点数数组类型
     * 
     * @param key   字段名
     * @param value 查询参数（浮点数）
     * @param type  查询类型（相等）
     * @return
     */
    public CriteriaDefinition buildFloatArray(String key, Float value, QueryType type) {
        switch (type) {
            case equal:
                return Criteria.where(key).is(value);
            default:
                return null;
        }
    }

    /**
     * 查询浮点数键值对类型
     * 
     * @param key   字段名
     * @param value 查询参数（浮点数键值对）
     * @param type  查询类型（相等）
     * @return
     */
    public CriteriaDefinition buildFloatMap(String key, Map<String, Float> value, QueryType type) {
        Criteria criteria = new Criteria();
        for (Map.Entry<String, Float> entry : value.entrySet()) {
            switch (type) {
                case equal:
                    criteria.andOperator(Criteria.where(key + '.' + entry.getKey()).is(entry.getValue()));
                    break;
                default:
                    return null;
            }
        }
        return criteria;
    }

    /**
     * 查询浮点数数组键值对类型
     * 
     * @param key   字段名
     * @param value 查询参数（浮点数键值对）
     * @param type  查询类型（相等）
     * @return
     */
    public CriteriaDefinition buildFloatArrayMap(String key, Map<String, Float> value, QueryType type) {
        Criteria criteria = new Criteria();
        for (Map.Entry<String, Float> entry : value.entrySet()) {
            switch (type) {
                case equal:
                    criteria.andOperator(Criteria.where(key + '.' + entry.getKey()).is(entry.getValue()));
                    break;
                default:
                    return null;
            }
        }
        return criteria;
    }

    /**
     * 查询字符串类型
     * 
     * @param key   字段名
     * @param value 查询参数（字符串）
     * @param type  查询类型（相等，相似）
     * @return
     */
    public CriteriaDefinition buildString(String key, String value, QueryType type) {
        switch (type) {
            case like:
                return Criteria.where(key).regex(value);
            case equal:
                return Criteria.where(key).is(value);
            default:
                return null;
        }
    }

    /**
     * 查询字符串类型
     * 
     * @param key   字段名
     * @param value 查询参数（字符串数组）
     * @param type  查询类型（包含）
     * @return
     */
    public CriteriaDefinition buildString(String key, List<String> value, QueryType type) {
        switch (type) {
            case in:
                return Criteria.where(key).in(value);
            default:
                return null;
        }
    }

    /**
     * 查询字符串类型
     * 
     * @param key   字段名
     * @param value 查询参数（字符串）
     * @param type  查询类型（相等，相似）
     * @return
     */
    public CriteriaDefinition buildStringArray(String key, String value, QueryType type) {
        switch (type) {
            case equal:
                return Criteria.where(key).is(value);
            case like:
                return Criteria.where(key).regex(value);
            default:
                return null;
        }
    }

    /**
     * 查询字符串类型
     * 
     * @param key   字段名
     * @param value 查询参数（字符串数组）
     * @param type  查询类型（包含）
     * @return
     */
    public CriteriaDefinition buildStringArray(String key, List<String> value, QueryType type) {
        switch (type) {
            case in:
                return Criteria.where(key).in(value);
            default:
                return null;
        }
    }

    /**
     * 查询字符串键值对类型
     * 
     * @param key   字段名
     * @param value 查询参数（字符串键值对）
     * @param type  查询类型（相等，相似）
     * @return
     */
    public CriteriaDefinition buildStringMap(String key, Map<String, String> value, QueryType type) {
        Criteria criteria = new Criteria();
        for (Map.Entry<String, String> entry : value.entrySet()) {
            switch (type) {
                case like:
                    criteria.andOperator(Criteria.where(key + '.' + entry.getKey()).regex(entry.getValue()));
                    break;
                case equal:
                    criteria.andOperator(Criteria.where(key + '.' + entry.getKey()).is(entry.getValue()));
                    break;
                default:
                    return null;
            }
        }
        return criteria;
    }

    /**
     * 查询字符串数组键值对类型
     * 
     * @param key   字段名
     * @param value 查询参数（字符串键值对）
     * @param type  查询类型（相等，相似）
     * @return
     */
    public CriteriaDefinition buildStringArrayMap(String key, Map<String, String> value, QueryType type) {
        Criteria criteria = new Criteria();
        for (Map.Entry<String, String> entry : value.entrySet()) {
            switch (type) {
                case equal:
                    criteria.andOperator(Criteria.where(key + '.' + entry.getKey()).is(entry.getValue()));
                    break;
                case like:
                    criteria.andOperator(Criteria.where(key + '.' + entry.getKey()).regex(entry.getValue()));
                    break;
                default:
                    return null;
            }
        }
        return criteria;
    }
}
