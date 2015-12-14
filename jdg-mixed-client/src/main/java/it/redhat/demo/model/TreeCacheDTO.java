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

/**
 *
 * @author francesco
 */
@ProtoMessage(name = "TreeCacheDTO")
@ProtoDoc("@Indexed")
public class TreeCacheDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long version;

    @ProtoField(number = 1, required = true)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public Long getVersion() {

        return version;
    }

    public void setVersion(Long version) {

        this.version = version;
    }
}
