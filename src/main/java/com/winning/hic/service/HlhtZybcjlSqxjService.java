package com.winning.hic.service;

import java.util.List;  

import com.winning.hic.model.HlhtZybcjlSqxj;  


/**
* @author HLHT
* @title HLHT_ZYBCJL_SQXJ
* @email Winning Health
* @package com.winning.hic.service
* @date 2018-34-31 16:34:33
*/
public interface HlhtZybcjlSqxjService {

    public int createHlhtZybcjlSqxj(HlhtZybcjlSqxj hlhtZybcjlSqxj);

    public int modifyHlhtZybcjlSqxj(HlhtZybcjlSqxj hlhtZybcjlSqxj);

    public int removeHlhtZybcjlSqxj(HlhtZybcjlSqxj hlhtZybcjlSqxj);

    public HlhtZybcjlSqxj getHlhtZybcjlSqxj(HlhtZybcjlSqxj hlhtZybcjlSqxj);

    public int getHlhtZybcjlSqxjCount(HlhtZybcjlSqxj hlhtZybcjlSqxj);

    public List<HlhtZybcjlSqxj> getHlhtZybcjlSqxjList(HlhtZybcjlSqxj hlhtZybcjlSqxj);

    public List<HlhtZybcjlSqxj> getHlhtZybcjlSqxjPageList(HlhtZybcjlSqxj hlhtZybcjlSqxj);
}