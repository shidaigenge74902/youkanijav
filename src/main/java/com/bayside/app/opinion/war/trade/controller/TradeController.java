package com.bayside.app.opinion.war.trade.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.trade.bo.TradeBo;
import com.bayside.app.opinion.war.trade.model.Trade;
import com.bayside.app.opinion.war.trade.service.TradeService;
import com.bayside.app.opinion.war.weidu.service.WeiDuService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.UuidUtil;
@RestController
public class TradeController {
	@Autowired
    private TradeService tradeServiceImpl;
	@Autowired
	private WeiDuService weiduServiceImpl;
	@RequestMapping(value = "/insertTrade", method = RequestMethod.GET)
	public Response insertTrade(TradeBo record){
		record.setId(UuidUtil.getUUID());
		int num = tradeServiceImpl.insertTrade(record);
		if(num >0){
			return new Response(ResponseStatus.Success,num,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO,false);
		}
	}
	@RequestMapping(value = "/updateTrade", method = RequestMethod.GET)
	public Response updateTrade(TradeBo record){
		int num = tradeServiceImpl.updateTrade(record);
		if(num >0){
			return new Response(ResponseStatus.Success,num,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/deleteTrade", method = RequestMethod.GET)
	public Response deleteTrade(TradeBo record){
		int num = tradeServiceImpl.deleteTrade(record.getId());
		
		int n = weiduServiceImpl.deleteWeiDuByRTradeId(record.getId());
		
		if(num >0){
			return new Response(ResponseStatus.Success,num,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
		}
	}
	@RequestMapping(value = "/selectAllTrade", method = RequestMethod.GET)
	public Response selectAllTrade(){
		List<Trade> list = tradeServiceImpl.selectAllTrade();
		if(list.size()>0){
			return new Response(ResponseStatus.Success,list,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/selectTradeById", method = RequestMethod.GET)
	public Response selectTradeById(String id){
		Trade trade = tradeServiceImpl.selectTradeById(id);
		if(trade!=null){
			return new Response(ResponseStatus.Success,trade,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}

}
