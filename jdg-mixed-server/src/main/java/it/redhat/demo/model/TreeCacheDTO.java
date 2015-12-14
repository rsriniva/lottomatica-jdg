/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.redhat.demo.model;

import java.io.Serializable;



public class TreeCacheDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long version;

    public Long getVersion() {

        return version;
    }

    public void setVersion(Long version) {

        this.version = version;
    }
}
