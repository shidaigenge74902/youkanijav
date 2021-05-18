package com.bayside.app.opinion.manager.hanlp.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bayside.app.opinion.manager.hanlp.bo.HanlpWordBo;
import com.bayside.app.opinion.manager.hanlp.bo.NominalBo;
import com.bayside.app.opinion.manager.hanlp.model.HanlpWord;

public interface HanlpWordService {
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
	List<HanlpWord> getHanlpWord(HanlpWordBo hanlpWordBo);
	/**
	 * 
	 * <p>方法名称：getHanlpWordById</p>
	 * <p>方法描述：通过id查询</p>
	 * @param id
	 * @return
	 * @author Administrator
	 * @since  2017年3月3日
	 * <p> history 2017年3月3日 Administrator  创建   <p>
	 */
	HanlpWordBo getHanlpWordById(String id);
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
	int delHanlpWordById(String id);
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
	int updateHanlpWordById(HanlpWordBo hanlpWordBo);
	/**
	 * 
	 * <p>方法名称：saveHanlpWord</p>
	 * <p>方法描述：保存新增的词库</p>
	 * @param hanlpWordBo
	 * @return
	 * @author Administrator
	 * @since  2017年3月3日
	 * <p> history 2017年3月3日 Administrator  创建   <p>
	 */
	int saveHanlpWord(HanlpWordBo hanlpWordBo);
	/**
	 * 
	 * <p>方法名称：downloadTxt</p>
	 * <p>方法描述：下载txt</p>
	 * @param hanlpWordBo
	 * @author Administrator
	 * @since  2017年3月3日
	 * <p> history 2017年3月3日 Administrator  创建   <p>
	 */
	void downloadTxt(HanlpWordBo hanlpWordBo,HttpServletResponse response);
	/**
	 * 
	 * <p>方法名称：getAllNominal</p>
	 * <p>方法描述：获取词性</p>
	 * @return
	 * @author Administrator
	 * @since  2017年3月3日
	 * <p> history 2017年3月3日 Administrator  创建   <p>
	 */
	List<NominalBo> getAllNominal();

}
