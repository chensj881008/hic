package com.winning.hic.service.impl;

import com.winning.hic.base.Constants;
import com.winning.hic.base.utils.*;
import com.winning.hic.dao.cisdb.CommonQueryDao;
import com.winning.hic.dao.cisdb.EmrQtbljlkDao;
import com.winning.hic.dao.data.HlhtZlczjlMzsqfsjlDao;
import com.winning.hic.dao.data.MbzDataListSetDao;
import com.winning.hic.dao.data.MbzDataSetDao;
import com.winning.hic.dao.data.MbzLoadDataInfoDao;
import com.winning.hic.model.*;
import com.winning.hic.service.HlhtZlczjlMzsqfsjlService;
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
 * @title HLHT_ZLCZJL_MZSQFSJL
 * @email Winning Health
 * @package com.winning.hic.service.impl
 * @date 2018-31-31 16:31:37
 */
@Service
public class HlhtZlczjlMzsqfsjlServiceImpl implements HlhtZlczjlMzsqfsjlService {

    private final Logger logger = LoggerFactory.getLogger(HlhtZlczjlMzsqfsjlServiceImpl.class);
    @Autowired
    private CommonQueryDao commonQueryDao;
    @Autowired
    private MbzDataListSetDao mbzDataListSetDao;
    @Autowired
    private MbzDataSetDao mbzDataSetDao;

    @Autowired
    private EmrQtbljlkDao emrQtbljlkDao;
    @Autowired
    private HlhtZlczjlMzsqfsjlDao hlhtZlczjlMzsqfsjlDao;
    @Autowired
    private MbzDataCheckService mbzDataCheckService;
    @Autowired
    private MbzLoadDataInfoDao mbzLoadDataInfoDao;

    public int createHlhtZlczjlMzsqfsjl(HlhtZlczjlMzsqfsjl hlhtZlczjlMzsqfsjl) {
        return this.hlhtZlczjlMzsqfsjlDao.insertHlhtZlczjlMzsqfsjl(hlhtZlczjlMzsqfsjl);
    }

    public int modifyHlhtZlczjlMzsqfsjl(HlhtZlczjlMzsqfsjl hlhtZlczjlMzsqfsjl) {
        return this.hlhtZlczjlMzsqfsjlDao.updateHlhtZlczjlMzsqfsjl(hlhtZlczjlMzsqfsjl);
    }

    public int removeHlhtZlczjlMzsqfsjl(HlhtZlczjlMzsqfsjl hlhtZlczjlMzsqfsjl) {
        return this.hlhtZlczjlMzsqfsjlDao.deleteHlhtZlczjlMzsqfsjl(hlhtZlczjlMzsqfsjl);
    }

    public HlhtZlczjlMzsqfsjl getHlhtZlczjlMzsqfsjl(HlhtZlczjlMzsqfsjl hlhtZlczjlMzsqfsjl) {
        return this.hlhtZlczjlMzsqfsjlDao.selectHlhtZlczjlMzsqfsjl(hlhtZlczjlMzsqfsjl);
    }

    public int getHlhtZlczjlMzsqfsjlCount(HlhtZlczjlMzsqfsjl hlhtZlczjlMzsqfsjl) {
        return (Integer) this.hlhtZlczjlMzsqfsjlDao.selectHlhtZlczjlMzsqfsjlCount(hlhtZlczjlMzsqfsjl);
    }

    public List<HlhtZlczjlMzsqfsjl> getHlhtZlczjlMzsqfsjlList(HlhtZlczjlMzsqfsjl hlhtZlczjlMzsqfsjl) {
        return this.hlhtZlczjlMzsqfsjlDao.selectHlhtZlczjlMzsqfsjlList(hlhtZlczjlMzsqfsjl);
    }

    public List<HlhtZlczjlMzsqfsjl> getHlhtZlczjlMzsqfsjlPageList(HlhtZlczjlMzsqfsjl hlhtZlczjlMzsqfsjl) {
        return this.hlhtZlczjlMzsqfsjlDao.selectHlhtZlczjlMzsqfsjlPageList(hlhtZlczjlMzsqfsjl);
    }

    @Override
    public List<HlhtZlczjlMzsqfsjl> getHlhtZlczjlMzsqfsjlListFromBaseData(EmrQtbljlk emrQtbljlk) throws DataAccessException {
        return this.commonQueryDao.getHlhtZlczjlMzsqfsjlListFromBaseData(emrQtbljlk);
    }

    @Override
    public void deleteHlhtZlczjlMzsqfsjlByYjlxh(HlhtZlczjlMzsqfsjl hlhtZlczjlMzsqfsjl) {
        this.hlhtZlczjlMzsqfsjlDao.deleteHlhtZlczjlMzsqfsjlByYjlxh(hlhtZlczjlMzsqfsjl);
    }

    @Override
    public List<MbzDataCheck> interfaceHlhtZlczjlMzsqfsjl(MbzDataCheck t) {
        //执行过程信息记录
        List<MbzDataCheck> mbzDataChecks = null;
        int emr_count =0;//病历数量
        int real_count=0;//实际数量

        MbzDataSet mbzDataSet = new MbzDataSet();
        mbzDataSet.setSourceType(Constants.WN_ZLCZJL_MZSQFSJL_SOURCE_TYPE);
        mbzDataSet.setPId(Long.parseLong(Constants.WN_ZLCZJL_MZSQFSJL_SOURCE_TYPE));
        List<MbzDataSet> mbzDataSetList = this.mbzDataSetDao.selectMbzDataSetList(mbzDataSet);
        //1.获取对应的模板ID集合
        MbzDataListSet mbzDataListSet = new MbzDataListSet();
        mbzDataListSet.setSourceType(Constants.WN_ZLCZJL_MZSQFSJL_SOURCE_TYPE);
        List<MbzDataListSet> dataListSets = this.mbzDataListSetDao.selectMbzDataListSetList(mbzDataListSet);
        for (MbzDataListSet dataListSet : dataListSets) {
            EmrQtbljlk qtbljlk = new EmrQtbljlk();
            qtbljlk.setBldm(dataListSet.getModelCode());
            qtbljlk.setYxjl(1);
            qtbljlk.getMap().put("startDate",t.getMap().get("startDate"));
            qtbljlk.getMap().put("endDate",t.getMap().get("endDate"));
            qtbljlk.getMap().put("syxh",t.getMap().get("syxh"));
            qtbljlk.getMap().put("hisName", ConfigUtils.getEnvironment().getZYHISLinkServerFullPathURL());

            //2.根据模板代码去找到对应的病人病历
            List<HlhtZlczjlMzsqfsjl> hlhtZlczjlMzsqfsjlListFromBaseData = this.commonQueryDao.getHlhtZlczjlMzsqfsjlListFromBaseData(qtbljlk);
            emr_count = emr_count+hlhtZlczjlMzsqfsjlListFromBaseData.size();
            if (hlhtZlczjlMzsqfsjlListFromBaseData != null) {
                for (HlhtZlczjlMzsqfsjl hlhtZlczjlMzsqfsjl : hlhtZlczjlMzsqfsjlListFromBaseData) {
                    EmrQtbljlk emrQtbljlk = new EmrQtbljlk();
                    emrQtbljlk.setQtbljlxh(Long.parseLong(hlhtZlczjlMzsqfsjl.getYjlxh()));
                    emrQtbljlk = this.emrQtbljlkDao.selectEmrQtbljlk(emrQtbljlk);
                    //清库
                    HlhtZlczjlMzsqfsjl temp = new HlhtZlczjlMzsqfsjl();
                    temp.setYjlxh(hlhtZlczjlMzsqfsjl.getYjlxh());
                    this.hlhtZlczjlMzsqfsjlDao.deleteHlhtZlczjlMzsqfsjlByYjlxh(temp);
                    //清除日志
                    Map<String,Object> param = new HashMap<>();
                    param.put("SOURCE_ID",emrQtbljlk.getQtbljlxh());
                    param.put("SOURCE_TYPE",Constants.WN_ZLCZJL_MZSQFSJL_SOURCE_TYPE);
                    mbzLoadDataInfoDao.deleteMbzLoadDataInfoBySourceIdAndSourceType(param);
                    //3.xml文件解析 获取病历信息
                    Document document = null;
                    try {
                        document = XmlUtil.getDocument(Base64Utils.unzipEmrXml(emrQtbljlk.getBlnr()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Map<String, String> paramTypeMap = ReflectUtil.getParamTypeMap(HlhtZlczjlMzsqfsjl.class);
                    try {
                        hlhtZlczjlMzsqfsjl = (HlhtZlczjlMzsqfsjl) HicHelper.initModelValue(mbzDataSetList, document, hlhtZlczjlMzsqfsjl, paramTypeMap);
                        logger.info("Model:{}", hlhtZlczjlMzsqfsjl);
                        this.hlhtZlczjlMzsqfsjlDao.insertHlhtZlczjlMzsqfsjl(hlhtZlczjlMzsqfsjl);
                        //插入日志
                        mbzLoadDataInfoDao.insertMbzLoadDataInfo(new MbzLoadDataInfo(
                                Long.parseLong(Constants.WN_ZLCZJL_MZSQFSJL_SOURCE_TYPE),
                                emrQtbljlk.getQtbljlxh(),emrQtbljlk.getBlmc(),emrQtbljlk.getSyxh()+"",
                                new Timestamp(DateUtil.parse(emrQtbljlk.getFssj(),DateUtil.PATTERN_19).getTime()),
                                hlhtZlczjlMzsqfsjl.getPatid(),hlhtZlczjlMzsqfsjl.getZyh(),hlhtZlczjlMzsqfsjl.getHzxm(),hlhtZlczjlMzsqfsjl.getXbmc(),hlhtZlczjlMzsqfsjl.getXbdm(),
                                hlhtZlczjlMzsqfsjl.getKsmc(),hlhtZlczjlMzsqfsjl.getKsdm(), hlhtZlczjlMzsqfsjl.getBqmc(),hlhtZlczjlMzsqfsjl.getBqdm(), hlhtZlczjlMzsqfsjl.getSfzhm()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    real_count++;
                }
            }
        }
        //1.病历总数 2.抽取的病历数量 3.子集类型
        this.mbzDataCheckService.createMbzDataCheckNum(emr_count,real_count,Integer.parseInt(Constants.WN_ZLCZJL_MZSQFSJL_SOURCE_TYPE));
        return mbzDataChecks;
    }
}