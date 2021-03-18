package com.lasmch.util;

import java.util.List;
import java.util.Map;

public interface IDaoProvider<T> {

    int _insert(T t);

    int _insert(Map<String, Object> map);

    int _update(T t);

    int _update(Map<String, Object> map);

    int _delete(T t);

    int _delete(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int selectCount(T t);

    int _checkIfExists(Map<String, Object> map);

    int _checkIfExists(T t);

    List<T> _select(Map<String, Object> map);

    List<T> _select(T t);

    T _view(T t);

    T _view(Map<String, Object> map);

}
