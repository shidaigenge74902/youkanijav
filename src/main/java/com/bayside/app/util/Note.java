package com.bayside.app.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bayside.app.opinion.war.myuser.model.ManagerUser;

public class Note {

	private Set<String> returnList = new HashSet<String>();
	public List<String> getChids(List<ManagerUser> list, String parentid) {
		// TODO Auto-generated method stub
	    List<String> listids = new ArrayList<String>();
	    listids.add(parentid);
		  if(list == null && parentid == null){
			  return null;
			 
		   }
		  if(null!=list){
			  for (Iterator<ManagerUser> iterator = list.iterator(); iterator.hasNext();) {
		        	ManagerUser node = (ManagerUser) iterator.next();
		            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
		            if (node.getParentid()!=null &&parentid.equals(node.getParentid())) {
		                returnList.add(node.getId());
		                //递归遍历子后子
		                getChids(list, node.getId());
		            }
		        }
		  }
	       if(null!=returnList){
	    	   Iterator<String> it1 = returnList.iterator();
		        for (String ss : returnList) {
		        	listids.add(ss);
		        } 
	       }
	      
	        
	        return listids;
	}

}
