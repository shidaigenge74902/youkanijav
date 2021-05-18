package com.bayside.app.opinion.manager.hanlp.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.manager.hanlp.bo.HanlpWordBo;
import com.bayside.app.opinion.manager.hanlp.bo.NominalBo;
import com.bayside.app.opinion.manager.hanlp.model.HanlpWord;
import com.bayside.app.opinion.manager.hanlp.service.HanlpWordService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@RestController
@RequestMapping("HanlpWordController")
public class HanlpWordController {
	@Autowired
	private HanlpWordService hanlpWordServiceImpl;
	/**
	 * 
	 * <p>方法名称：getHanlpWord</p>
	 * <p>方法描述：获取分析词库</p>
	 * @param hanlpWordBo
	 * @return
	 * @author Administrator
	 * @since  2017年3月3日
	 * <p> history 2017年3月3日 Administrator  创建   <p>
	 */
	@RequestMapping(value ="/getHanlpWord",method=RequestMethod.GET)
	public Response getHanlpWord(HanlpWordBo hanlpWordBo,PageInfo page){
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<HanlpWord> list = hanlpWordServiceImpl.getHanlpWord(hanlpWordBo);
		PageInfo<HanlpWord> info = new PageInfo<HanlpWord>(list);
		return new Response(ResponseStatus.Success,info,true);
	}
	/**
	 * <p>方法名称：getHanlpWordById</p>
	 * <p>方法描述：通过id查询</p>
	 * @param id
	 * @return
	 * @author Administrator
	 * @since  2017年3月3日
	 * <p> history 2017年3月3日 Administrator  创建   <p>
	 */
	@RequestMapping(value ="/getHanlpWordById",method=RequestMethod.GET)
	public Response  getHanlpWordById(String id){
		HanlpWordBo hanlpWordBo = hanlpWordServiceImpl.getHanlpWordById(id);
		return new Response(ResponseStatus.Success, hanlpWordBo, true);
	}
	/**
	 * <p>方法名称：getHanlpWordById</p>
	 * <p>方法描述：通过id查询</p>
	 * @param id
	 * @return
	 * @author Administrator
	 * @since  2017年3月3日
	 * <p> history 2017年3月3日 Administrator  创建   <p>
	 */
	@RequestMapping(value ="/getAllNominal",method=RequestMethod.GET)
	public Response  getAllNominal(){
		List<NominalBo> list = hanlpWordServiceImpl.getAllNominal();
		return new Response(ResponseStatus.Success, list, true);
	}
	/**
	 * 
	 * <p>方法名称：delHanlpWordById</p>
	 * <p>方法描述：根据ID删除</p>
	 * @param id
	 * @return
	 * @author Administrator
	 * @since  2017年3月3日
	 * <p> history 2017年3月3日 Administrator  创建   <p>
	 */
	@RequestMapping(value ="/delHanlpWordById",method=RequestMethod.GET)
	public Response delHanlpWordById(String id){
		int count = hanlpWordServiceImpl.delHanlpWordById(id);
		if(count>0){
			return new Response(ResponseStatus.Success, AppConstant.responseInfo.DELETESUCCESS, true);
		}else{
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.DELETEERRO, false);
		}
	}
	/**
	 * 
	 * <p>方法名称：updateHanlpWordById</p>
	 * <p>方法描述：修改</p>
	 * @param hanlpWordBo
	 * @return
	 * @author Administrator
	 * @since  2017年3月3日
	 * <p> history 2017年3月3日 Administrator  创建   <p>
	 */
	@RequestMapping(value ="/updateHanlpWordById",method=RequestMethod.POST)
	public Response  updateHanlpWordById(HanlpWordBo hanlpWordBo){
		int count = hanlpWordServiceImpl.updateHanlpWordById(hanlpWordBo);
		if(count>0){
			return new Response(ResponseStatus.Success, AppConstant.responseInfo.UPDATESUCCESS, true);
		}else{
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}

		
	}
	/*
	 * 
	 * <p>方法名称：saveHanlpWord</p>
	 * <p>方法描述：保存新增的词库</p>
	 * @param hanlpWordBo
	 * @return
	 * @author Administrator
	 * @since  2017年3月3日
	 * <p> history 2017年3月3日 Administrator  创建   <p>
	 */
	@RequestMapping(value ="/saveHanlpWord",method=RequestMethod.POST)
	public Response saveHanlpWord(HanlpWordBo hanlpWordBo){
		int count = hanlpWordServiceImpl.saveHanlpWord(hanlpWordBo);
		if(count>0){
			return new Response(ResponseStatus.Success, AppConstant.responseInfo.SAVESUCCESS, true);
		}else{
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
		}
	}
	/*
	 * 
	 * <p>方法名称：saveHanlpWord</p>
	 * <p>方法描述：保存新增的词库</p>
	 * @param hanlpWordBo
	 * @return
	 * @author Administrator
	 * @since  2017年3月3日
	 * <p> history 2017年3月3日 Administrator  创建   <p>
	 */
	@RequestMapping(value ="/downloadTxt",method=RequestMethod.GET)
	public void downloadTxt(HanlpWordBo hanlpWordBo,HttpServletResponse response){
		 hanlpWordServiceImpl.downloadTxt(hanlpWordBo,response);
	}

}
