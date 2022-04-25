package com.zn.learn.basic.javafature;

public class TypeT<T> {
    private T t;

    public T getData() {

        return t;
    }



    public TypeT(T t) {
        this.t = t;
    }
}
