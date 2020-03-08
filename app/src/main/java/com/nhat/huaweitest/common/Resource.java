package com.nhat.huaweitest.common;

public class Resource<T> {
    public ResourceState state;
    public T data;
    public String message;

    private Resource(ResourceState state, T data, String message) {
        this.state = state;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(ResourceState.SUCCESS, data, "");
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(ResourceState.LOADING, null, "");
    }

    public static <T> Resource<T> error(String message, T data) {
        return new Resource<>(ResourceState.ERROR, data, message);
    }
}
