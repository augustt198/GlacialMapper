package me.hfox.glacial.mongo.query;

import me.hfox.glacial.query.GlacialFieldQuery;
import me.hfox.glacial.query.GlacialQuery;

public class MongoFieldQuery<E> implements GlacialFieldQuery<E> {

    private MongoQuery<E> query;

    private String operator;
    private Object object;

    public MongoFieldQuery(MongoQuery<E> query) {
        this.query = query;
    }

    public String getOperator() {
        return operator;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public MongoQuery<E> equal(Object object) {
        set(null, object);
        return query;
    }

    @Override
    public MongoQuery<E> notEqual(Object object) {
        set("ne", object);
        return query;
    }

    @Override
    public MongoQuery<E> greaterThan(Object object) {
        set("gt", object);
        return query;
    }

    @Override
    public MongoQuery<E> greaterThanOrEqual(Object object) {
        set("gte", object);
        return query;
    }

    @Override
    public MongoQuery<E> lessThan(Object object) {
        set("lt", object);
        return query;
    }

    @Override
    public MongoQuery<E> lessThanOrEqual(Object object) {
        set("lte", object);
        return query;
    }

    @Override
    public MongoQuery<E> exists() {
        set("exists", true);
        return query;
    }

    @Override
    public MongoQuery<E> doesNotExist() {
        set("exists", false);
        return query;
    }

    public MongoQuery<E> in(Object object) {
        set("in", object);
        return query;
    }

    public MongoQuery<E> notIn(Object object) {
        set("nin", object);
        return query;
    }

    private void set(String operator, Object value) {
        this.operator = (operator == null ? null : "$" + operator);
        this.object = value;
    }

}
