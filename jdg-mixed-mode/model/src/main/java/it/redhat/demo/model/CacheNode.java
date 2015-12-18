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
import java.util.ArrayList;
import java.util.List;

@ProtoMessage(name = "CacheNode")
@ProtoDoc("@Indexed")
public class CacheNode<T extends TreeCacheDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nodeKey;

    private Long cachedDTO;

    private CacheEdge edgesToParentNodes;

    private CacheEdge edgesToChildNodes;

    private List<CacheNode<T>> zchildNodes;

    public CacheNode() {
    }

    public CacheNode(String nodeKey) {
        this.nodeKey = nodeKey;
        this.edgesToChildNodes = new CacheEdge(nodeKey);
        this.edgesToParentNodes = new CacheEdge(nodeKey);
    }

    @ProtoField(number = 1, required = false)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    @ProtoField(number = 2, required = false)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public Long getCachedDTO() {
        return cachedDTO;
    }

    public void setCachedDTO(Long cachedDTO) {
        this.cachedDTO = cachedDTO;
    }

    @ProtoField(number = 3, required = false)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public CacheEdge getEdgesToParentNodes() {
        return edgesToParentNodes;
    }

    public void setEdgesToParentNodes(CacheEdge edgesToParentNodes) {
        this.edgesToParentNodes = edgesToParentNodes;
    }

    @ProtoField(number = 4, required = false)
    @ProtoDoc("@IndexedField(index = true, store = true)")
    public CacheEdge getEdgesToChildNodes() {
        return edgesToChildNodes;
    }

    public void setEdgesToChildNodes(CacheEdge edgesToChildNodes) {
        this.edgesToChildNodes = edgesToChildNodes;
    }

    @ProtoField(number = 5, collectionImplementation = ArrayList.class, required = false)
    public List<CacheNode<T>> getZchildNodes() {
        return zchildNodes;
    }

    public void setZchildNodes(List<CacheNode<T>> zchildNodes) {
        this.zchildNodes = zchildNodes;
    }


}
