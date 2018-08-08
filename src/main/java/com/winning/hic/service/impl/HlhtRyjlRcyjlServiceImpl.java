package com.winning.hic.service.impl;

import com.winning.hic.base.Constants;
import com.winning.hic.base.utils.*;
import com.winning.hic.dao.data.HlhtRyjlRcyjlDao;
import com.winning.hic.model.EmrQtbljlk;
import com.winning.hic.model.HlhtRyjlRcyjl;
import com.winning.hic.model.MbzDataListSet;
import com.winning.hic.model.MbzDataSet;
import com.winning.hic.service.EmrQtbljlkService;
import com.winning.hic.service.HlhtRyjlRcyjlService;
import com.winning.hic.service.MbzDataListSetService;
import com.winning.hic.service.MbzDataSetService;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
* @author HLHT
* @title HLHT_RYJL_RCYJL
* @email Winning Health
* @package com.winning.hic.service.impl
* @date 2018-30-31 16:30:31
*/
@Service
public class HlhtRyjlRcyjlServiceImpl implements  HlhtRyjlRcyjlService {

    private static final Logger logger = LoggerFactory.getLogger(HlhtRyjlRcyjlServiceImpl.class);

    @Autowired
    private HlhtRyjlRcyjlDao hlhtRyjlRcyjlDao;
    @Autowired
    private MbzDataListSetService mbzDataListSetService;
    @Autowired
    private MbzDataSetService mbzDataSetService;
    @Autowired
    private EmrQtbljlkService emrQtbljlkService;

    public int createHlhtRyjlRcyjl(HlhtRyjlRcyjl hlhtRyjlRcyjl){
        return this.hlhtRyjlRcyjlDao.insertHlhtRyjlRcyjl(hlhtRyjlRcyjl);
    }

    public int modifyHlhtRyjlRcyjl(HlhtRyjlRcyjl hlhtRyjlRcyjl){
        return this.hlhtRyjlRcyjlDao.updateHlhtRyjlRcyjl(hlhtRyjlRcyjl);
    }

    public int removeHlhtRyjlRcyjl(HlhtRyjlRcyjl hlhtRyjlRcyjl){
        return this.hlhtRyjlRcyjlDao.deleteHlhtRyjlRcyjl(hlhtRyjlRcyjl);
    }

    public HlhtRyjlRcyjl getHlhtRyjlRcyjl(HlhtRyjlRcyjl hlhtRyjlRcyjl){
        return this.hlhtRyjlRcyjlDao.selectHlhtRyjlRcyjl(hlhtRyjlRcyjl);
    }

    public int getHlhtRyjlRcyjlCount(HlhtRyjlRcyjl hlhtRyjlRcyjl){
        return (Integer)this.hlhtRyjlRcyjlDao.selectHlhtRyjlRcyjlCount(hlhtRyjlRcyjl);
    }

    public List<HlhtRyjlRcyjl> getHlhtRyjlRcyjlList(HlhtRyjlRcyjl hlhtRyjlRcyjl){
        return this.hlhtRyjlRcyjlDao.selectHlhtRyjlRcyjlList(hlhtRyjlRcyjl);
    }

    public List<HlhtRyjlRcyjl> getHlhtRyjlRcyjlPageList(HlhtRyjlRcyjl hlhtRyjlRcyjl){
        return this.hlhtRyjlRcyjlDao.selectHlhtRyjlRcyjlPageList(hlhtRyjlRcyjl);
    }

    /**
     *  生成24小时入出院记录
     * @param rcyjl
     */
    @Override
    public HlhtRyjlRcyjl getInitHlhtRyjlRcyjlData(HlhtRyjlRcyjl rcyjl) {
        return  this.hlhtRyjlRcyjlDao.selectInitHlhtRyjlRcyjlData(rcyjl);
    }

    @Override
    public List<HlhtRyjlRcyjl> interfaceHlhtRyjlRcyjl(HlhtRyjlRcyjl hlhtRyjlRcyjl) throws IOException, ParseException {
        //配置接口表字段配置信息
        MbzDataSet mbzDataSet = new MbzDataSet();
        mbzDataSet.setSourceType(Constants.WN_RYJL_RCYJL_SOURCE_TYPE);
        mbzDataSet.setPId(Long.parseLong(Constants.WN_RYJL_RCYJL_SOURCE_TYPE));
        List<MbzDataSet> mbzDataSetList = mbzDataSetService.getMbzDataSetList(mbzDataSet);

        //配置并加载对应的出入院模板集合
        MbzDataListSet mbzDataListSet = new MbzDataListSet();
        mbzDataListSet.setSourceType(Constants.WN_RYJL_RCYJL_SOURCE_TYPE);
        List<MbzDataListSet> dataListSets = mbzDataListSetService.getMbzDataListSetList(mbzDataListSet);

        //获取接口对象字段集合信息
        Map<String, String> paramTypeMap = ReflectUtil.getParamTypeMap(HlhtRyjlRcyjl.class);

        //获取模板集合，遍历
        for(MbzDataListSet dataListSet :dataListSets){
            //查询病历数据 数据来源
            EmrQtbljlk qtbljlk = new EmrQtbljlk();
            qtbljlk.setBldm(dataListSet.getModelCode());
            List<EmrQtbljlk> qtbljlkList = emrQtbljlkService.getEmrQtbljlkList(qtbljlk);
            if(qtbljlkList != null){
                //循环病历
                for(EmrQtbljlk emrQtbljlk:qtbljlkList){
                    //获取接口数据
                    HlhtRyjlRcyjl rcyjl = new HlhtRyjlRcyjl();
                    rcyjl.setYjlxh(String.valueOf(emrQtbljlk.getQtbljlxh()));
                    rcyjl = getHlhtRyjlRcyjl(rcyjl);
                    //解析病历xml
                    Document document = XmlUtil.getDocument(Base64Utils.unzipEmrXml(emrQtbljlk.getBlnr()));

                    //判断是否存在重复,存在则删除，重新新增
                    if(rcyjl != null ){
                        //初始化数据
                        HlhtRyjlRcyjl oldRcyjl  = new HlhtRyjlRcyjl();
                        oldRcyjl.setYjlxh(String.valueOf(emrQtbljlk.getQtbljlxh()));
                        this.removeHlhtRyjlRcyjl(rcyjl);
                    }
                    rcyjl  = new HlhtRyjlRcyjl();
                    rcyjl.setYjlxh(String.valueOf(emrQtbljlk.getQtbljlxh()));
                    rcyjl = this.getInitHlhtRyjlRcyjlData(rcyjl);
                    rcyjl = (HlhtRyjlRcyjl) HicHelper.initModelValue(mbzDataSetList,document,rcyjl,paramTypeMap);
                    this.createHlhtRyjlRcyjl(rcyjl);
                }
            }
        }
        return null;
    }

}