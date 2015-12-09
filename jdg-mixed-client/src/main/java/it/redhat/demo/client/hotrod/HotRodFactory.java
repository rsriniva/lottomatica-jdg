/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.redhat.demo.client.hotrod;

import org.infinispan.client.hotrod.RemoteCacheManager;

/**
 *
 * @author francesco
 */
interface HotRodFactory {
    	public RemoteCacheManager getCacheContainer(); 
 

}
