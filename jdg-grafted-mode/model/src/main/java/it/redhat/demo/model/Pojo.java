/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.redhat.demo.model;

import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoMessage;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pojo)) return false;

        Pojo pojo = (Pojo) o;

        if (!key.equals(pojo.key)) return false;
        return value.equals(pojo.value);

    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
