package com.ashish.kawaspace.model;

import java.util.List;

public class UserInfoModel {
    public List<Result> results;
    public Info info;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}


