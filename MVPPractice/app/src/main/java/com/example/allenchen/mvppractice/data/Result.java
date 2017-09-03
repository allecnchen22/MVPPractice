package com.example.allenchen.mvppractice.data;

import java.util.List;

/**
 * Created by ALLENCHEN on 2017/9/2.
 */

public final class Result {

    private int offset;

    private int limit;

    private int count;

    private int sort;

    private List<Park> results ;


    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<Park> getResults() {
        return results;
    }

    public void setResults(List<Park> results) {
        this.results = results;
    }
}
