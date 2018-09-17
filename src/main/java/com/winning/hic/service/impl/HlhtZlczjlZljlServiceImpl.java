package com.winning.hic.service.impl;

import com.winning.hic.base.Constants;
import com.winning.hic.base.utils.*;
import com.winning.hic.dao.cisdb.CommonQueryDao;
import com.winning.hic.dao.cisdb.EmrQtbljlkDao;
import com.winning.hic.dao.data.HlhtZlczjlZljlDao;
import com.winning.hic.dao.data.MbzDataListSetDao;
import com.winning.hic.dao.data.MbzDataSetDao;
import com.winning.hic.dao.data.MbzLoadDataInfoDao;
import com.winning.hic.model.*;
import com.winning.hic.service.HlhtZlczjlZljlService;
import com.winning.hic.service.MbzDataCheckService;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author HLHT
 * @title HLHT_ZLCZJL_ZLJL
 * @email Winning Health
 * @package com.winning.hic.service.impl
 * @date 2018-32-31 16:32:09
 */
@Service
public class HlhtZlczjlZljlServiceImpl implements HlhtZlczjlZljlService {
    private final Logger logger = LoggerFactory.getLogger(HlhtZlczjlZljlServiceImpl.class);
    @Autowired
    private CommonQueryDao commonQueryDao;
    @Autowired
    private MbzDataListSetDao mbzDataListSetDao;
    @Autowired
    private MbzDataSetDao mbzDataSetDao;

    @Autowired
    private EmrQtbljlkDao emrQtbljlkDao;

    @Autowired
    private MbzDataCheckService mbzDataCheckService;


    @Autowired
    private HlhtZlczjlZljlDao hlhtZlczjlZljlDao;

    @Autowired
    private MbzLoadDataInfoDao mbzLoadDataInfoDao;

    public int createHlhtZlczjlZljl(HlhtZlczjlZljl hlhtZlczjlZljl) {
        return this.hlhtZlczjlZljlDao.insertHlhtZlczjlZljl(hlhtZlczjlZljl);
    }

    public int modifyHlhtZlczjlZljl(HlhtZlczjlZljl hlhtZlczjlZljl) {
        return this.hlhtZlczjlZljlDao.updateHlhtZlczjlZljl(hlhtZlczjlZljl);
    }

    public int removeHlhtZlczjlZljl(HlhtZlczjlZljl hlhtZlczjlZljl) {
        return this.hlhtZlczjlZljlDao.deleteHlhtZlczjlZljl(hlhtZlczjlZljl);
    }

    public HlhtZlczjlZljl getHlhtZlczjlZljl(HlhtZlczjlZljl hlhtZlczjlZljl) {
        return this.hlhtZlczjlZljlDao.selectHlhtZlczjlZljl(hlhtZlczjlZljl);
    }

    public int getHlhtZlczjlZljlCount(HlhtZlczjlZljl hlhtZlczjlZljl) {
        return (Integer) this.hlhtZlczjlZljlDao.selectHlhtZlczjlZljlCount(hlhtZlczjlZljl);
    }

    public List<HlhtZlczjlZljl> getHlhtZlczjlZljlList(HlhtZlczjlZljl hlhtZlczjlZljl) {
        return this.hlhtZlczjlZljlDao.selectHlhtZlczjlZljlList(hlhtZlczjlZljl);
    }

    public List<HlhtZlczjlZljl> getHlhtZlczjlZljlPageList(HlhtZlczjlZljl hlhtZlczjlZljl) {
        return this.hlhtZlczjlZljlDao.selectHlhtZlczjlZljlPageList(hlhtZlczjlZljl);
    }

    @Override
    public List<HlhtZlczjlZljl> getHlhtZlczjlZljlListFromBaseData(EmrQtbljlk emrQtbljlk) throws DataAccessException {
        return this.commonQueryDao.getHlhtZlczjlZljlListFromBaseData(emrQtbljlk);
    }

    @Override
    public void deleteHlhtZlczjlZljlByYjlxh(HlhtZlczjlZljl hlhtZlczjlZljl) {
        this.hlhtZlczjlZljlDao.deleteHlhtZlczjlZljlByYjlxh(hlhtZlczjlZljl);
    }

    @Override
    public List<MbzDataCheck> interfaceHlhtZlczjlZljl(MbzDataCheck t) {
        //执行过程信息记录
        List<MbzDataCheck> mbzDataChecks = null;
        int emr_count =0;//病历数量
        int real_count=0;//实际数量

        MbzDataSet mbzDataSet = new MbzDataSet();
        mbzDataSet.setSourceType(Constants.WN_ZLCZJL_ZLJL_SOURCE_TYPE);
        mbzDataSet.setPId(Long.parseLong(Constants.WN_ZLCZJL_ZLJL_SOURCE_TYPE));
        List<MbzDataSet> mbzDataSetList = this.mbzDataSetDao.selectMbzDataSetList(mbzDataSet);
        //1.获取对应的模板ID集合
        MbzDataListSet mbzDataListSet = new MbzDataListSet();
        mbzDataListSet.setSourceType(Constants.WN_ZLCZJL_ZLJL_SOURCE_TYPE);
        List<MbzDataListSet> dataListSets = this.mbzDataListSetDao.selectMbzDataListSetList(mbzDataListSet);
        for (MbzDataListSet dataListSet : dataListSets) {
            EmrQtbljlk qtbljlk = new EmrQtbljlk();
            qtbljlk.setBldm(dataListSet.getModelCode());
            qtbljlk.setYxjl(1);
            qtbljlk.getMap().put("startDate",t.getMap().get("startDate"));
            qtbljlk.getMap().put("endDate",t.getMap().get("endDate"));
            qtbljlk.getMap().put("hisName", ConfigUtils.getEnvironment().getZYHISLinkServerFullPathURL());

            //2.根据模板代码去找到对应的病人病历
            List<HlhtZlczjlZljl> hlhtZlczjlZljlListFromBaseData = this.commonQueryDao.getHlhtZlczjlZljlListFromBaseData(qtbljlk);
            emr_count = emr_count+hlhtZlczjlZljlListFromBaseData.size();

            if (hlhtZlczjlZljlListFromBaseData != null) {
                for (HlhtZlczjlZljl hlhtZlczjlZljl : hlhtZlczjlZljlListFromBaseData) {
                    EmrQtbljlk emrQtbljlk = new EmrQtbljlk();
                    emrQtbljlk.setQtbljlxh(Long.parseLong(hlhtZlczjlZljl.getYjlxh()));
                    emrQtbljlk = this.emrQtbljlkDao.selectEmrQtbljlk(emrQtbljlk);
                    //清库
                    HlhtZlczjlZljl temp = new HlhtZlczjlZljl();
                    temp.setYjlxh(hlhtZlczjlZljl.getYjlxh());
                    this.hlhtZlczjlZljlDao.deleteHlhtZlczjlZljlByYjlxh(temp);
                    //清除日志
                    Map<String,Object> param = new HashMap<>();
                    param.put("SOURCE_ID",emrQtbljlk.getQtbljlxh());
                    param.put("SOURCE_TYPE",Constants.WN_ZLCZJL_ZLJL_SOURCE_TYPE);
                    mbzLoadDataInfoDao.deleteMbzLoadDataInfoBySourceIdAndSourceType(param);
                    //3.xml文件解析 获取病历信息
                    Document document = null;
                    try {
                        document = XmlUtil.getDocument(Base64Utils.unzipEmrXml(emrQtbljlk.getBlnr()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Map<String, String> paramTypeMap = ReflectUtil.getParamTypeMap(HlhtZlczjlZljl.class);
                    try {
                        hlhtZlczjlZljl = (HlhtZlczjlZljl) HicHelper.initModelValue(mbzDataSetList, document, hlhtZlczjlZljl, paramTypeMap);
                        logger.info("Model:{}", hlhtZlczjlZljl);
                        this.hlhtZlczjlZljlDao.insertHlhtZlczjlZljl(hlhtZlczjlZljl);
                        mbzLoadDataInfoDao.insertMbzLoadDataInfo(new MbzLoadDataInfo(
                                Long.parseLong(Constants.WN_ZLCZJL_ZLJL_SOURCE_TYPE),
                                emrQtbljlk.getQtbljlxh(), emrQtbljlk.getBlmc(),emrQtbljlk.getSyxh()+"",
                                new Timestamp(DateUtil.parse(emrQtbljlk.getFssj(),DateUtil.PATTERN_19).getTime()),
                                hlhtZlczjlZljl.getPatid(),hlhtZlczjlZljl.getZyh(),hlhtZlczjlZljl.getHzxm(),hlhtZlczjlZljl.getXbmc(),hlhtZlczjlZljl.getXbdm(),
                                hlhtZlczjlZljl.getKsmc(), hlhtZlczjlZljl.getKsdm(),   hlhtZlczjlZljl.getBqmc(),hlhtZlczjlZljl.getBqdm(), hlhtZlczjlZljl.getSfzhm()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    real_count++;

                }
            }
        }

        //1.病历总数 2.抽取的病历数量 3.子集类型
        this.mbzDataCheckService.createMbzDataCheckNum(emr_count,real_count,Integer.parseInt(Constants.WN_ZLCZJL_ZLJL_SOURCE_TYPE));

        return mbzDataChecks;
    }
}