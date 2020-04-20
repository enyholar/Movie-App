package com.behruz.magmovie.model;

public class GenresModel {
    public Integer id;
    public String name;
    public int flag;

    public GenresModel(Integer id, String name, int flag) {
        this.id = id;
        this.name = name;
        this.flag = flag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
