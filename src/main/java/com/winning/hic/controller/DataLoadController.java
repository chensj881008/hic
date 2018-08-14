package com.winning.hic.controller;

import com.winning.hic.base.Constants;
import com.winning.hic.base.utils.Base64Utils;
import com.winning.hic.base.utils.ReflectUtil;
import com.winning.hic.base.utils.StringUtil;
import com.winning.hic.base.utils.XmlUtil;
import com.winning.hic.model.*;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class DataLoadController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(RyjlJbxxExtractController.class);

    @RequestMapping("/dataLoad/index")
    public String index() {
        //数据抽取
        try {
            //5.入院记录* --陈枫
            List<MbzDataCheck> mbzDataChecks5 = super.getFacade().getHlhtRyjlJbxxService().interfaceHlhtRyjlJbxx();
//
//            //6.首次病程记录表* --陈蒯
            List<MbzDataCheck> mbzDataCheck6 = super.getFacade().getHlhtZybcjlScbcjlService().interfaceHlhtZybcjlScbcjl();
//
//            //1. 24h内入出院记录  --陈世杰
            List<MbzDataCheck> mbzDataCheck1 = super.getFacade().getHlhtRyjlRcyjlService().interfaceHlhtRyjlRcyjl(null);
//            //2. 24h内入院死亡记录*  -- 陈世杰
            List<MbzDataCheck> mbzDataCheck2 = super.getFacade().getHlhtRyjlRyswjlService().interfaceHlhtRyjlRyswjl(null);
//            //3. 病危（重）通知书*  --陈枫
            List<MbzDataCheck> mbzDataChecks3 = super.getFacade().getHlhtZqgzxxBwztzsService().interfaceHlhtZqgzxxBwztzs();
//            //4.出院记录数据集表* --陈枫
            List<MbzDataCheck> mbzDataChecks4 = super.getFacade().getHlhtZybcjlCyjlService().interfaceHlhtZybcjlCyjl();
//
//            //7.日常病程记录数据集表*  --陈枫
//
//            //8.交接班记录数据集表(医院一般不在病历里面写交接班记录，CIS有一个交接班的功能)*    --陈蒯
            List<MbzDataCheck> mbzDataChecks8 = super.getFacade().getHlhtZybcjlJjbjlService().interfaceHlhtZybcjlJjbjl();

            //9.--阶段小结数据集表*   --陈蒯
            List<MbzDataCheck> mbzDataChecks9 = super.getFacade().getHlhtZybcjlJdxjService().interfaceHlhtZybcjlJdxj();

            //10.抢救记录数据集表*  --陈蒯
            List<MbzDataCheck> mbzDataChecks10 = super.getFacade().getHlhtZybcjlQjjlService().interfaceHlhtZybcjlQjjl();

            //11.术后首次病程记录数据集表* --- 陈世杰

            //12.术前小结数据集表* --- 陈世杰

            //13.术前讨论数据集表*   --陈世杰

            //14.死亡病历讨论记录数据集表* --陈世杰

            //15.死亡记录数据集表*  --陈枫

            //16.麻醉术前访视记录表(由手麻系统处理）* --陈枫

            //17.麻醉术后访视记录表(由手麻系统处理)*   --陈枫

            //18.麻醉知情同意书表* --陈枫

            //19.其他知情告知同意书*  --陈蒯
            List<MbzDataCheck> mbzDataChecks19 = super.getFacade().getHlhtZqgzxxQtzqtysService().interfaceHlhtZqgzxxQtzqtys();

            //20.一般手术记录表(待定）*    --陈蒯

            //21.会诊记录数据集表* --陈蒯

            //22.出院小结数据集表*  --陈蒯

            //23.
            //24.
            //25.
            //26.
            //27.
            //28.
            //29.
            //30.
            //31.
            //32.
            //33.
            //34.
            //35.
            //非病历抽取
            //36.
            //37.
            //38.
            //39.





        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/dataLoad/index";
    }


}
