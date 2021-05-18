package com.bayside.app.opinion.manager.solrmatch.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
@RestController
public class SolrMatchController {
	@RequestMapping(value ="/solrMatch",method=RequestMethod.GET)
	public Response solrMatch(String subjectid){
		if(subjectid!=null&&!"".equals(subjectid)){
			Thread t = new SolrMatch(subjectid);
			t.start();
		}
		return new Response(ResponseStatus.Success, "启动成功", true);
	}
}
