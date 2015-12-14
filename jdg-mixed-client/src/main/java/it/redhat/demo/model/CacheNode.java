/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.redhat.demo.model;

import java.io.Serializable;
import java.util.List;
import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoMessage;

@ProtoMessage(name = "CacheNode")
@ProtoDoc("@Indexed")
public class CacheNode<T extends TreeCacheDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    public CacheNode(String nodeKey) {

        this.nodeKey = nodeKey;
        this.edgesToChildNodes = new CacheEdge(nodeKey);
        this.edgesToParentNodes = new CacheEdge(nodeKey);
       // this.nodeNotification = new CacheNodeNotification();
       // this.zchildNodes = new ArrayList<CacheNode<T>>();
    }

    private String                nodeKey;

    private T                     cachedDTO;

    private CacheEdge            edgesToParentNodes;

    private CacheEdge            edgesToChildNodes;

  

 
    private List<CacheNode<T>>    zchildNodes;

    @Override
    public CacheNode<T> clone() {

        CacheNode<T> clone = null;

        try {
            clone = (CacheNode<T>) super.clone();
        } catch (CloneNotSupportedException e) {
           e.printStackTrace();
        }

       

        return clone;
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
    public T getCachedDTO() {
        return cachedDTO;
    }

    public void setCachedDTO(T cachedDTO) {
        this.cachedDTO = cachedDTO;
    }
    @ProtoField(number = 3, required = true)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public CacheEdge getEdgesToParentNodes() {
        return edgesToParentNodes;
    }

    public void setEdgesToParentNodes(CacheEdge edgesToParentNodes) {
        this.edgesToParentNodes = edgesToParentNodes;
    }
    @ProtoField(number = 4, required = true)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public CacheEdge getEdgesToChildNodes() {
        return edgesToChildNodes;
    }

    public void setEdgesToChildNodes(CacheEdge edgesToChildNodes) {
        this.edgesToChildNodes = edgesToChildNodes;
    }
    @ProtoField(number = 5, required = true)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public List<CacheNode<T>> getZchildNodes() {
        return zchildNodes;
    }

    public void setZchildNodes(List<CacheNode<T>> zchildNodes) {
        this.zchildNodes = zchildNodes;
    }

 
    
    
}
