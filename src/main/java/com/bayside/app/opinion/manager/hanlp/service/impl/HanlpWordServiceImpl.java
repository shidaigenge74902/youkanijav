package com.bayside.app.opinion.manager.hanlp.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.manager.hanlp.bo.HanlpWordBo;
import com.bayside.app.opinion.manager.hanlp.bo.NominalBo;
import com.bayside.app.opinion.manager.hanlp.dao.HanlpWordMapper;
import com.bayside.app.opinion.manager.hanlp.dao.NominalMapper;
import com.bayside.app.opinion.manager.hanlp.model.HanlpWord;
import com.bayside.app.opinion.manager.hanlp.model.Nominal;
import com.bayside.app.opinion.manager.hanlp.service.HanlpWordService;
import com.bayside.app.util.UuidUtil;
@Service("hanlpWordServiceImpl")
public class HanlpWordServiceImpl implements HanlpWordService {
	private static final Logger log = Logger.getLogger(HanlpWordServiceImpl.class);
	@Autowired
	private HanlpWordMapper hanlpWordMapper;
	@Autowired
	private NominalMapper nominalMapper;
	@Override
	public List<HanlpWord> getHanlpWord(HanlpWordBo hanlpWordBo){
		HanlpWord hanlpWord = new HanlpWord();
		if(hanlpWordBo!=null){
			BeanUtils.copyProperties(hanlpWordBo, hanlpWord);
		}
		List<HanlpWord> list = hanlpWordMapper.selectBySelective(hanlpWord);
		return list;
	}
	@Override
	public HanlpWordBo getHanlpWordById(String id){
		HanlpWord hanlpWord = hanlpWordMapper.selectByPrimaryKey(id);
		HanlpWordBo hanlpWordBo = new HanlpWordBo();
		if(hanlpWord!=null){
			BeanUtils.copyProperties(hanlpWord, hanlpWordBo);
		}
		return hanlpWordBo;
	}
	@Override
	public int delHanlpWordById(String id){
		int count = hanlpWordMapper.deleteByPrimaryKey(id);
		return count;
	}
	@Override
	public int updateHanlpWordById(HanlpWordBo hanlpWordBo){
		int count = 0;
		if(hanlpWordBo!=null){
			HanlpWord hanlpWord = new HanlpWord();
			BeanUtils.copyProperties(hanlpWordBo, hanlpWord);
			 count = hanlpWordMapper.updateByPrimaryKeySelective(hanlpWord);
		}
		return count;
	}
	@Override
	public int saveHanlpWord(HanlpWordBo hanlpWordBo){
		int count = 0;
		if(hanlpWordBo!=null){
			HanlpWord hanlpWord = new HanlpWord();
			BeanUtils.copyProperties(hanlpWordBo, hanlpWord);
			hanlpWord.setId(UuidUtil.getUUID());
			count = hanlpWordMapper.insert(hanlpWord);
		}
		return count;
	}
	@Override
	public List<NominalBo> getAllNominal(){
		Nominal record = new Nominal();
		List<NominalBo> bos = new ArrayList<NominalBo>();
		List<Nominal> list = nominalMapper.selectAllNominal(record);
		for (Nominal nominal : list) {
			NominalBo nominalBo = new NominalBo();
			BeanUtils.copyProperties(nominal, nominalBo);
			bos.add(nominalBo);
		}
		return bos;
		
	}
	@Override
	public void downloadTxt(HanlpWordBo hanlpWordBo,HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=MyDictionary.txt");
		HanlpWord hanlpWord = new HanlpWord();
		if(hanlpWordBo!=null){
			BeanUtils.copyProperties(hanlpWordBo, hanlpWord);
		}
		List<HanlpWord> list = hanlpWordMapper.selectBySelective(hanlpWord);
		try {
			OutputStream os = response.getOutputStream();
			for (HanlpWord word : list) {
				os.write((word.getWordName()+"\r\t"+word.getNominal()+"\r\n").getBytes());
			}
			os.flush();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
	}
}
