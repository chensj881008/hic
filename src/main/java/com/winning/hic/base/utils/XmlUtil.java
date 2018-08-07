package com.winning.hic.base.utils;

import com.winning.hic.model.MbzDataSet;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlUtil {
    private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    /**
     * 根据xnl内容获取dom
     *
     * @param xmlStr
     * @return
     */
    public static Document getDocument(String xmlStr) {
        Document document = null;
        try {
            document = DocumentHelper.parseText(xmlStr);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 根据文件地址，获取dom
     *
     * @param path
     * @return
     */
    public static Document getDocumentByPath(String path) {
        Document document = null;
        try {
            SAXReader reader = new SAXReader();
            document = reader.read(new File(path));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }


    public static List<Element> getNodes(Element node) {
        System.out.println("--------------------");

        //当前节点的名称、文本内容和属性
        System.out.println("当前节点名称：" + node.getName());//当前节点名称
        System.out.println("当前节点的内容：" + node.getTextTrim());//当前节点名称
        List<Attribute> listAttr = node.attributes();//当前节点的所有属性的list
//        for (Attribute attr : listAttr) {//遍历当前节点的所有属性
//            String name = attr.getName();//属性名称
//            String value = attr.getValue();//属性的值
//            System.out.println("属性名称：" + name + "属性值：" + value);
//        }

        //递归遍历当前节点所有的子节点
        List<Element> listElement = node.elements();//所有一级子节点的list
        List<Element> childs = new ArrayList<>();
        for (Element e : listElement) {//遍历所有一级子节点a
            childs.addAll(getNodes(e));//递归
        }
        childs.add(node);
        return childs;
    }

    /***
     * 获取指定属性值的元素的属性值
     * @param elements 元素集合
     * @param attrName 属性名称
     * @param attrValue 属性值
     * @param targetAttr 目标属性
     * @return 目标属性值
     */
    public static String getAttrValue(List<Element> elements, String attrName, String attrValue, String targetAttr) {
        String targetValue = null;
        if (!StringUtil.isEmptyOrNull(attrName) && !StringUtil.isEmptyOrNull(attrValue)) {
            for (Element element : elements) {
                String value = element.attribute(attrName).getValue();
                if (!StringUtil.isEmptyOrNull(value) && attrValue.equals(value)) {
                    targetValue = element.attribute(targetAttr).getValue();
                    break;
                }
            }
        } else {
            logger.error("It does not define the attrbute name !");
        }
        return targetValue;
    }


    public static Element getElementById(Element param, String elementID) {
        int i = 0;
        for (int size = param.nodeCount(); i < size; ++i) {
            Node node = param.node(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                String id = element.attribute("id") == null ? null : element.attribute("id").getValue();
                if (id != null && elementID.equals(id)) {
                    return element;
                }
                element = getElementById(element, elementID);
                if (element != null) {
                    return element;
                }
            }
        }
        return null;
    }


    /**
     * 根据数据配置抽取数
     *
     * @param document
     * @param mbzDataSet
     * @return
     */
    public static String getAttrValueByDataSet(Document document, MbzDataSet mbzDataSet) {
        if (document == null || mbzDataSet == null) {
            logger.warn("The parameter is null!");
            return null;
        }
        //文件结构id
        String dtjddm = mbzDataSet.getDtjddm();
        //基础模板id
        String qrmbdm = mbzDataSet.getQrmbdm();
        //元数据id
        String qrdxdm = mbzDataSet.getQrdxdm();
        //原子节点id
        String yzjddm = mbzDataSet.getYzjddm();
        Element rootElement = document.getRootElement();
        if (StringUtil.isEmptyOrNull(dtjddm)) {
            return null;
        }
        Element dtjddmElement = XmlUtil.getElementById(rootElement, dtjddm);
        if (dtjddmElement == null) {
            return null;
        }
        if (!StringUtil.isEmptyOrNull(yzjddm)) {
            Element qrmbdmElement = XmlUtil.getElementById(dtjddmElement, qrmbdm);
            Element qrdxdmElement = XmlUtil.getElementById(qrmbdmElement, qrdxdm);
            Element yzjddmElement = XmlUtil.getElementById(qrdxdmElement, yzjddm);
            return yzjddmElement.attribute("value").getValue();
        } else if (!StringUtil.isEmptyOrNull(qrdxdm)) {
            Element qrmbdmElement = XmlUtil.getElementById(dtjddmElement, qrmbdm);
            Element qrdxdmElement = XmlUtil.getElementById(qrmbdmElement, qrdxdm);
            return qrdxdmElement.attribute("value").getValue();
        } else if (!StringUtil.isEmptyOrNull(qrmbdm)) {
            Element qrmbdmElement = XmlUtil.getElementById(dtjddmElement, qrmbdm);
            return qrmbdmElement.attribute("value").getValue();
        }
        return dtjddmElement.attribute("value").getValue();
    }


    public static void main(String[] args) {

        Document document = XmlUtil.getDocumentByPath("E:\\jackMa\\hic\\src\\main\\java\\com\\winning\\hic\\base\\utils\\mima.xml");
        MbzDataSet mbzDataSet = new MbzDataSet();
//        mbzDataSet.setDtjddm("665");
//        mbzDataSet.setQrmbdm("d09dd403-ecbd-4be3-a832-139736c5643d");
//        mbzDataSet.setQrdxdm("a20f7a00-2da1-4b6f-a7f5-78275c4433d1");
//        mbzDataSet.setYzjddm("6fa963d2-ab46-493e-9cf6-ff87addccf52");
        mbzDataSet.setDtjddm("628");
        mbzDataSet.setQrmbdm("34e00ca9-f630-4a0a-bb9a-2400a3d7392f");
        mbzDataSet.setQrdxdm("fe028248-7533-49c0-8c47-11132c39fbfe");
        mbzDataSet.setYzjddm("c8499f95-5517-4342-bcc4-6822448c911e");
        String attrValue = XmlUtil.getAttrValueByDataSet(document, mbzDataSet);


//        String id = rootElement.attribute("id").getValue();
//        System.out.println(elements.size());
        System.out.println(attrValue);


    }


}
