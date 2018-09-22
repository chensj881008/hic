package com.winning.hic.controller;

import com.winning.hic.base.Constants;
import com.winning.hic.base.utils.ModelCheckUtil;
import com.winning.hic.base.utils.StringUtil;
import com.winning.hic.base.utils.XmlUtil;
import com.winning.hic.model.EmrMbk;
import com.winning.hic.model.MbzDataListSet;
import com.winning.hic.model.MbzDictInfo;
import com.winning.hic.model.MbzModelCheck;
import com.winning.hic.service.MbzModelCheckService;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 模板校验Controller
 *
 * @author Evol
 * @date 2018年7月24日13:13:01
 */
@Controller
public class ModelCheckController extends BaseController {
    @Autowired
    private MbzModelCheckService mbzModelCheckService;

    /**
     * 跳转模板校验页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/modelCheck/index")
    public String index(Model model) {
        //清库
        //getFacade().getMbzModelCheckService().removeMbzModelCheck(new MbzModelCheck());
        //数据初始化
        getFacade().getMbzModelCheckService().innitModelCheckData();
        //获取配置模板总数
        Integer emrMbkCount = getFacade().getMbzDataListSetService().getMbzDataListSetCount(new MbzDataListSet());
        //从字典表获取数据集
        MbzDictInfo mbzDictInfo = new MbzDictInfo();
        mbzDictInfo.setDictCode("platformTableName");
        List<MbzDictInfo> mbzDictInfoList = getFacade().getMbzDictInfoService().getMbzDictInfoList(mbzDictInfo);
        List<MbzDataListSet> modelList = getFacade().getMbzDataListSetService().getMbzDataListSetList(new MbzDataListSet());
        resultMap.put("num", emrMbkCount);
        resultMap.put("dataSet", mbzDictInfoList);
        resultMap.put("modelList", modelList);
        model.addAllAttributes(resultMap);
        return "modelCheck/modelCheck";
    }

    /**
     * 对应数据集模板总数
     *
     * @param dataListSet
     * @return
     */
    @RequestMapping("/modelCheck/modelNum")
    @ResponseBody
    public Map modelNum(MbzDataListSet dataListSet) {
        //获取去模板总数
        Integer emrMbkCount = getFacade().getMbzDataListSetService().getMbzDataListSetCount(dataListSet);
        resultMap.put("num", emrMbkCount == null ? 0 : emrMbkCount);
        return resultMap;
    }

    /**
     * 数据集目录
     *
     * @param mbzModelCheck
     * @return
     */
    @RequestMapping("/modelCheck/list")
    @ResponseBody
    public List<MbzModelCheck> list(MbzModelCheck mbzModelCheck) {
        //获取数据集目录
        List<MbzModelCheck> mbzModelCheckList = mbzModelCheckService.selectDataSet(mbzModelCheck);
        return mbzModelCheckList;
    }

    /**
     * 获取批量校验数据集模板集合
     *
     * @param mbzModelCheck
     * @return
     */
    @RequestMapping("/modelCheck/modelList")
    @ResponseBody
    public List<MbzModelCheck> modelList(MbzModelCheck mbzModelCheck) {
        if (StringUtil.isEmptyOrNull(mbzModelCheck.getSourceType())) {
            return null;
        }
        //获取数据集目录
        List<MbzModelCheck> mbzModelCheckList = mbzModelCheckService.selectModelListBySourceType(mbzModelCheck);
        return mbzModelCheckList;
    }

    /**
     * 获取批量校验模板配置集合
     *
     * @param mbzModelCheck
     * @return
     */
    @RequestMapping("/modelCheck/modelCheckList")
    @ResponseBody
    public List<MbzModelCheck> modelCheckList(MbzModelCheck mbzModelCheck) {
        if (StringUtil.isEmptyOrNull(mbzModelCheck.getSourceType())) {
            return null;
        }
        //获取数据集目录 影藏错误数据
        List<MbzModelCheck> mbzModelCheckList = mbzModelCheckService.getMbzModelCheckListNotFalse(mbzModelCheck);
        return mbzModelCheckList;
    }

    /**
     * 单份校验模板配置集合
     *
     * @param mbzModelCheck
     * @return
     */
    @RequestMapping("/modelCheck/singleModelCheckList")
    @ResponseBody
    public List<MbzModelCheck> singleModelCheckList(MbzModelCheck mbzModelCheck) {
        if (StringUtil.isEmptyOrNull(mbzModelCheck.getSourceType())) {
            return null;
        }
        //获取数据集目录 影藏错误数据
        List<MbzModelCheck> mbzModelCheckList = mbzModelCheckService.getMbzModelCheckList(mbzModelCheck);
        return mbzModelCheckList;
    }

    /**
     * 二级联动
     *
     * @param mbzDataListSet
     * @return
     */
    @RequestMapping("/modelCheck/checkList")
    @ResponseBody
    public Map checkList(MbzDataListSet mbzDataListSet) {
        if (StringUtil.isEmptyOrNull(mbzDataListSet.getSourceType())) {
            return null;
        }
        //获取数据集目录 影藏错误数据
        List<MbzDataListSet> modelList = getFacade().getMbzDataListSetService().getMbzDataListSetList(mbzDataListSet);
        resultMap.put("currentModelList", modelList);
        return resultMap;
    }

    /**
     * 模板校验
     *
     * @param mbzModelCheck
     * @return
     */
    @RequestMapping("/modelCheck/doCheck")
    @ResponseBody
    public Map doCheck(MbzModelCheck mbzModelCheck) {
        //获取待校验模板sourceType
        String sourceType = mbzModelCheck.getSourceType();
        String modelCode = mbzModelCheck.getModelCode();
        MbzModelCheck temp = new MbzModelCheck();
        if (!StringUtil.isEmptyOrNull(sourceType)) {
            temp.setSourceType(sourceType);
        }
        if (!StringUtil.isEmptyOrNull(modelCode)) {
            temp.setModelCode(modelCode);
        }

        // 获取待check数据集合
        List<MbzModelCheck> mbzModelChecks = getFacade().getMbzModelCheckService().getMbzModelCheckList(temp);
        for (MbzModelCheck modelCheck : mbzModelChecks) {
            //获取当前校验字段所用模板
            EmrMbk emrMbkTemp = new EmrMbk();
            emrMbkTemp.setMbdm(modelCheck.getModelCode());
            emrMbkTemp = getFacade().getEmrMbkService().getEmrMbk(emrMbkTemp);
            if (emrMbkTemp == null) {
                continue;
            }
            //获取模板xml并装换成document文件
            Document document = XmlUtil.getDocument(emrMbkTemp.getMbnr());
            //获取根节点
            Element rootElement = document.getRootElement();
            //校验
            ModelCheckUtil.checkNode(rootElement, modelCheck);
            //更新校验结果
            getFacade().getMbzModelCheckService().modifyMbzModelCheck(modelCheck);
        }
        resultMap.put("msg", Constants.SUCCESS);
        return resultMap;
    }


}
