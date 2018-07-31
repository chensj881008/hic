package com.winning.hic.service;

import java.util.List;  

import com.winning.hic.model.HlhtZybcjlSwbltljl;  


/**
* @author HLHT
* @title HLHT_ZYBCJL_SWBLTLJL
* @email Winning Health
* @package com.winning.hic.service
* @date 2018-34-31 16:34:40
*/
public interface HlhtZybcjlSwbltljlService {

    public int createHlhtZybcjlSwbltljl(HlhtZybcjlSwbltljl hlhtZybcjlSwbltljl);

    public int modifyHlhtZybcjlSwbltljl(HlhtZybcjlSwbltljl hlhtZybcjlSwbltljl);

    public int removeHlhtZybcjlSwbltljl(HlhtZybcjlSwbltljl hlhtZybcjlSwbltljl);

    public HlhtZybcjlSwbltljl getHlhtZybcjlSwbltljl(HlhtZybcjlSwbltljl hlhtZybcjlSwbltljl);

    public int getHlhtZybcjlSwbltljlCount(HlhtZybcjlSwbltljl hlhtZybcjlSwbltljl);

    public List<HlhtZybcjlSwbltljl> getHlhtZybcjlSwbltljlList(HlhtZybcjlSwbltljl hlhtZybcjlSwbltljl);

    public List<HlhtZybcjlSwbltljl> getHlhtZybcjlSwbltljlPageList(HlhtZybcjlSwbltljl hlhtZybcjlSwbltljl);
}