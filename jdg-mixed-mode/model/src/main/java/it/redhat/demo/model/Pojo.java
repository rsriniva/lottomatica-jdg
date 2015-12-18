/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.redhat.demo.model;

import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoMessage;

import java.io.Serializable;

@ProtoMessage(name = "Pojo")
public class Pojo implements Serializable {

    @ProtoField(number = 1, required = true)
    public String key;

    @ProtoField(number = 2, required = true)
    public Long value;

    public Pojo() {
    }

    public Pojo(String key, Long value) {
        this.key = key;
        this.value = value;
    }

}
