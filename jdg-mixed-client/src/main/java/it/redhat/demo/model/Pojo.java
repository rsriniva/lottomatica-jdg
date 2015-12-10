/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.redhat.demo.model;

import java.io.Serializable;
import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoMessage;

@ProtoMessage(name = "Pojo")
@ProtoDoc("@Indexed")
public class Pojo implements Serializable {
    private String message;
    int x;
    int y;
    
    @ProtoField(number = 1, required = true)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    @ProtoField(number = 2, required = true)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    @ProtoField(number = 3, required = true)
    @ProtoDoc("@IndexedField(index = true, store = true)")
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
