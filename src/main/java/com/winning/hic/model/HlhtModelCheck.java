package com.winning.hic.model;

import java.io.Serializable; 

import org.apache.ibatis.type.Alias; 

import com.winning.hic.model.BaseDomain;



/**
 * @author HLHT
 * @title 
 * @email Winning Health
 * @package com.winning.hic.model
 * @date 2018-23-25 12:23:55
 */
@Alias("hlhtModelCheck")
public class HlhtModelCheck extends BaseDomain implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 字段名：ID
     * 备注: 
     * 默认值：无
     */
    private String id;
    /**
     * 字段名：P_ID
     * 备注: 
     * 默认值：无
     */
    private String pId;
    /**
     * 字段名：LEVEL
     * 备注: 
     * 默认值：无
     */
    private Integer level;
    /**
     * 字段名：QRMBDM
     * 备注: 
     * 默认值：无
     */
    private String qrmbdm;
    /**
     * 字段名：COLUMM_NAME
     * 备注: 
     * 默认值：无
     */
    private String colummName;
    /**
     * 字段名：TYPE
     * 备注: 元数据   文件结构
     * 默认值：无
     */
    private Integer type;
    /**
     * 字段名：DTJDDM
     * 备注: 
     * 默认值：无
     */
    private String dtjddm;
    /**
     * 字段名：QRDXDM
     * 备注: 
     * 默认值：无
     */
    private String qrdxdm;
    /**
     * 字段名：YZJDDM
     * 备注: 
     * 默认值：无
     */
    private String yzjddm;
    /**
     * 字段名：ERROR_DESC
     * 备注: 
     * 默认值：无
     */
    private String errorDesc;
    /**
     * 字段名：STATUS
     * 备注: 
     * 默认值：无
     */
    private Integer status;

    public HlhtModelCheck (){

    }

   /**
   * 字段名：ID
   * get方法
   * 备注: 
   */
   public String getId(){

        return id;
   }

   /**
   * 字段名：ID
   * set方法
   * 备注: 
   */
   public void setId(String id){
        this.id = id;
   }
   /**
   * 字段名：P_ID
   * get方法
   * 备注: 
   */
   public String getPId(){

        return pId;
   }

   /**
   * 字段名：P_ID
   * set方法
   * 备注: 
   */
   public void setPId(String pId){
        this.pId = pId;
   }
   /**
   * 字段名：LEVEL
   * get方法
   * 备注: 
   */
   public Integer getLevel(){

        return level;
   }

   /**
   * 字段名：LEVEL
   * set方法
   * 备注: 
   */
   public void setLevel(Integer level){
        this.level = level;
   }
   /**
   * 字段名：QRMBDM
   * get方法
   * 备注: 
   */
   public String getQrmbdm(){

        return qrmbdm;
   }

   /**
   * 字段名：QRMBDM
   * set方法
   * 备注: 
   */
   public void setQrmbdm(String qrmbdm){
        this.qrmbdm = qrmbdm;
   }
   /**
   * 字段名：COLUMM_NAME
   * get方法
   * 备注: 
   */
   public String getColummName(){

        return colummName;
   }

   /**
   * 字段名：COLUMM_NAME
   * set方法
   * 备注: 
   */
   public void setColummName(String colummName){
        this.colummName = colummName;
   }
   /**
   * 字段名：TYPE
   * get方法
   * 备注: 元数据   文件结构
   */
   public Integer getType(){

        return type;
   }

   /**
   * 字段名：TYPE
   * set方法
   * 备注: 元数据   文件结构
   */
   public void setType(Integer type){
        this.type = type;
   }
   /**
   * 字段名：DTJDDM
   * get方法
   * 备注: 
   */
   public String getDtjddm(){

        return dtjddm;
   }

   /**
   * 字段名：DTJDDM
   * set方法
   * 备注: 
   */
   public void setDtjddm(String dtjddm){
        this.dtjddm = dtjddm;
   }
   /**
   * 字段名：QRDXDM
   * get方法
   * 备注: 
   */
   public String getQrdxdm(){

        return qrdxdm;
   }

   /**
   * 字段名：QRDXDM
   * set方法
   * 备注: 
   */
   public void setQrdxdm(String qrdxdm){
        this.qrdxdm = qrdxdm;
   }
   /**
   * 字段名：YZJDDM
   * get方法
   * 备注: 
   */
   public String getYzjddm(){

        return yzjddm;
   }

   /**
   * 字段名：YZJDDM
   * set方法
   * 备注: 
   */
   public void setYzjddm(String yzjddm){
        this.yzjddm = yzjddm;
   }
   /**
   * 字段名：ERROR_DESC
   * get方法
   * 备注: 
   */
   public String getErrorDesc(){

        return errorDesc;
   }

   /**
   * 字段名：ERROR_DESC
   * set方法
   * 备注: 
   */
   public void setErrorDesc(String errorDesc){
        this.errorDesc = errorDesc;
   }
   /**
   * 字段名：STATUS
   * get方法
   * 备注: 
   */
   public Integer getStatus(){

        return status;
   }

   /**
   * 字段名：STATUS
   * set方法
   * 备注: 
   */
   public void setStatus(Integer status){
        this.status = status;
   }

}