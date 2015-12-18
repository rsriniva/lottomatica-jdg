/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.redhat.demo.model;

import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoMessage;

import java.io.Serializable;

/**
 * @author francesco
 */
@ProtoMessage(name = "CacheEdge")
@ProtoDoc("@Indexed")
public class CacheEdge implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nodeKey;

    private Long order;

//    private Object data;

    public CacheEdge() {
    }

    public CacheEdge(String nodeKey) {

        if (nodeKey == null) {
            throw new RuntimeException("CacheEdge constructor invoked with nodeKey null parameter");
        }

        this.nodeKey = nodeKey;
    }

    @ProtoField(number = 1, required = true)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    @ProtoField(number = 2, required = true)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

/*
    @ProtoField(number = 3, required = true)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
*/

}
