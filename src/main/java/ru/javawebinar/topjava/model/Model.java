package ru.javawebinar.topjava.model;

import java.util.List;

public interface Model<T> {
    List<T> list();
    void add(T t);
    void update(T t);
    void delete(int id);
    T getById(int id);
}
