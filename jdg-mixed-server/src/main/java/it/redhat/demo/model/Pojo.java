/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.redhat.demo.model;

import java.io.Serializable;

/**
 * @author francesco
 */
public class Pojo<T> implements Serializable {

    private T message;
    private int x;
    private int y;

    public Pojo() {
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Pojo{" + "message=" + message + ", x=" + x + ", y=" + y + '}';
    }

}
