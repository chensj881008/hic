package com.winning.hic.service.impl;

import com.winning.hic.base.Constants;
import com.winning.hic.dao.cisdb.CommonQueryDao;
import com.winning.hic.dao.data.HlhtMjzcfXycfDao;
import com.winning.hic.dao.data.MbzLoadDataInfoDao;
import com.winning.hic.model.HlhtMjzcfXycf;
import com.winning.hic.model.HlhtMjzcfZycf;
import com.winning.hic.model.MbzDataCheck;
import com.winning.hic.model.MbzLoadDataInfo;
import com.winning.hic.service.HlhtMjzcfXycfService;
import com.winning.hic.service.MbzDataCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* @author HLHT
* @title HLHT_MJZCF_XYCF
* @email Winning Health
* @package com.winning.hic.service.impl
* @date 2018-29-31 16:29:53
*/
@Service
public class HlhtMjzcfXycfServiceImpl implements  HlhtMjzcfXycfService {
    private final Logger logger = LoggerFactory.getLogger(HlhtMjzcfXycfServiceImpl.class);
    @Autowired
    private HlhtMjzcfXycfDao hlhtMjzcfXycfDao;
    @Autowired
    private CommonQueryDao commonQueryDao;
    @Autowired
    private MbzLoadDataInfoDao mbzLoadDataInfoDao;
    @Autowired
    private MbzDataCheckService mbzDataCheckService;

    public int createHlhtMjzcfXycf(HlhtMjzcfXycf hlhtMjzcfXycf){
        return this.hlhtMjzcfXycfDao.insertHlhtMjzcfXycf(hlhtMjzcfXycf);
    }

    public int modifyHlhtMjzcfXycf(HlhtMjzcfXycf hlhtMjzcfXycf){
        return this.hlhtMjzcfXycfDao.updateHlhtMjzcfXycf(hlhtMjzcfXycf);
    }

    public int removeHlhtMjzcfXycf(HlhtMjzcfXycf hlhtMjzcfXycf){
        return this.hlhtMjzcfXycfDao.deleteHlhtMjzcfXycf(hlhtMjzcfXycf);
    }

    public HlhtMjzcfXycf getHlhtMjzcfXycf(HlhtMjzcfXycf hlhtMjzcfXycf){
        return this.hlhtMjzcfXycfDao.selectHlhtMjzcfXycf(hlhtMjzcfXycf);
    }

    public int getHlhtMjzcfXycfCount(HlhtMjzcfXycf hlhtMjzcfXycf){
        return (Integer)this.hlhtMjzcfXycfDao.selectHlhtMjzcfXycfCount(hlhtMjzcfXycf);
    }

    public List<HlhtMjzcfXycf> getHlhtMjzcfXycfList(HlhtMjzcfXycf hlhtMjzcfXycf){
        return this.hlhtMjzcfXycfDao.selectHlhtMjzcfXycfList(hlhtMjzcfXycf);
    }

    public List<HlhtMjzcfXycf> getHlhtMjzcfXycfPageList(HlhtMjzcfXycf hlhtMjzcfXycf){
        return this.hlhtMjzcfXycfDao.selectHlhtMjzcfXycfPageList(hlhtMjzcfXycf);
    }

    @Override
    public List<MbzDataCheck> interfaceHlhtMjzcfXycf(MbzDataCheck entity) {
        int emr_count =0;//病历数量
        int real_count=0;//实际数量
        List<MbzDataCheck> dataChecks = null;
        HlhtMjzcfXycf xycf = new HlhtMjzcfXycf();
        xycf.getMap().put("startDate",entity.getMap().get("startDate"));
        xycf.getMap().put("endDate",entity.getMap().get("endDate"));
        List<HlhtMjzcfXycf> mjzcfXycfList = this.commonQueryDao.selectInitHlhtMjzcfXycf(xycf);
        emr_count = mjzcfXycfList.size();
        for (HlhtMjzcfXycf obj : mjzcfXycfList) {
            HlhtMjzcfXycf tempXycf = new HlhtMjzcfXycf();
            tempXycf.setYjlxh(obj.getYjlxh());
            this.hlhtMjzcfXycfDao.deleteHlhtMjzcfXycf(tempXycf);
            //清除日志
            Map<String,Object> param = new HashMap<>();
            param.put("SOURCE_ID",obj.getYjlxh());
            param.put("SOURCE_TYPE",Constants.WN_MJZCF_XYCF_SOURCE_TYPE);
            mbzLoadDataInfoDao.deleteMbzLoadDataInfoBySourceIdAndSourceType(param);

            this.hlhtMjzcfXycfDao.insertHlhtMjzcfXycf(obj);
            //插入日志
            mbzLoadDataInfoDao.insertMbzLoadDataInfo(new MbzLoadDataInfo(
                    Long.parseLong(Constants.WN_MJZCF_XYCF_SOURCE_TYPE),
                    Long.parseLong(obj.getYjlxh()),"西药处方","NA",obj.getCfklrq(),
                    obj.getPatid(),obj.getMjzh(),obj.getHzxm(),obj.getXbmc(),obj.getXbdm(),
                    "NA","NA", "NA","NA", obj.getSfzhm()));
            real_count++;
        }
        //1.病历总数 2.抽取的病历数量 3.子集类型
        this.mbzDataCheckService.createMbzDataCheckNum(emr_count,real_count,Integer.parseInt(Constants.WN_MJZCF_XYCF_SOURCE_TYPE));
        return dataChecks;
    }
}