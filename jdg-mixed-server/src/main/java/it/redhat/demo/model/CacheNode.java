/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.redhat.demo.model;

import java.io.Serializable;
import java.util.List;


 
public class CacheNode<T extends TreeCacheDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    public CacheNode(String nodeKey) {

        this.nodeKey = nodeKey;
        this.edgesToChildNodes = new CacheEdge();
        this.edgesToParentNodes = new CacheEdge();
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
 
    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }
 
    public T getCachedDTO() {
        return cachedDTO;
    }

    public void setCachedDTO(T cachedDTO) {
        this.cachedDTO = cachedDTO;
    }
 
    public CacheEdge getEdgesToParentNodes() {
        return edgesToParentNodes;
    }

    public void setEdgesToParentNodes(CacheEdge edgesToParentNodes) {
        this.edgesToParentNodes = edgesToParentNodes;
    }
 
    public CacheEdge getEdgesToChildNodes() {
        return edgesToChildNodes;
    }

    public void setEdgesToChildNodes(CacheEdge edgesToChildNodes) {
        this.edgesToChildNodes = edgesToChildNodes;
    }
 
    public List<CacheNode<T>> getZchildNodes() {
        return zchildNodes;
    }

    public void setZchildNodes(List<CacheNode<T>> zchildNodes) {
        this.zchildNodes = zchildNodes;
    }

    @Override
    public String toString() {
        return "CacheNode{" + "nodeKey=" + nodeKey + ", cachedDTO=" + cachedDTO + ", edgesToParentNodes=" + edgesToParentNodes + ", edgesToChildNodes=" + edgesToChildNodes + ", zchildNodes=" + zchildNodes + '}';
    }

 
    
    
}
