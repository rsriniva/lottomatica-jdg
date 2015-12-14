/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.redhat.demo.model;

import java.io.Serializable;



/**
 *
 * @author francesco
 */
 
public class CacheEdge implements Serializable {

     private static final long         serialVersionUID = 1L;

    private String                    nodeKey;

    
    private Long                      order;

    private Object                    data;

    public CacheEdge(String nodeKey) {

        if (nodeKey == null) {
            throw new RuntimeException("CacheEdge constructor invoked with nodeKey null parameter");
        }

        this.nodeKey = nodeKey;
    }
    public CacheEdge( ) {   }

 
    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

 
    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

 
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CacheEdge{" + "nodeKey=" + nodeKey + ", order=" + order + ", data=" + data + '}';
    }
    
     
    
}
