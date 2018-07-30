package com.winning.hic.dao.data;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import com.winning.hic.model.MbzDataListSet;



import org.springframework.stereotype.Repository;
/**
* @author MBZ
* @title DAO接口
* @email Winning Health
* @package com.winning.hic.dao
* @date 2018-55-24 13:55:05
*/
@Repository
public interface MbzDataListSetDao {

    public int insertMbzDataListSet(MbzDataListSet mbzDataListSet) throws DataAccessException;

    public int updateMbzDataListSet(MbzDataListSet mbzDataListSet) throws DataAccessException;

    public int deleteMbzDataListSet(MbzDataListSet mbzDataListSet) throws DataAccessException;

    public MbzDataListSet selectMbzDataListSet(MbzDataListSet mbzDataListSet) throws DataAccessException;

    public Object selectMbzDataListSetCount(MbzDataListSet mbzDataListSet) throws DataAccessException;

    public List<MbzDataListSet> selectMbzDataListSetList(MbzDataListSet mbzDataListSet) throws DataAccessException;

    public List<MbzDataListSet> selectMbzDataListSetPageList(MbzDataListSet mbzDataListSet) throws DataAccessException;
}