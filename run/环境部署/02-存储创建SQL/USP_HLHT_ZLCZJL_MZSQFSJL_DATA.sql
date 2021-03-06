GO
CREATE PROCEDURE [dbo].[USP_HLHT_ZLCZJL_MZSQFSJL_DATA]
@sourceType varchar(64),   --原纪录类型
@startDate  varchar(20),   --开始日期
@endDate    varchar(20),   --结束日期
@syxh       int            --首页序号
as
/*
[创建者] chenfeng
[公司]上海金仕达卫宁软件股份有限公司@2015-2018      
[时间]2018-09-23    
[功能]导出麻醉术前访视记录 ---USP_HLHT_ZLCZJL_MZSQFSJL_DATA
[参数]
 @sourceType: 元数据类型
 @startime: 开始时间戳
 @endtime:  结束时间戳
 @syxh：病人首页序号 
[调用实例]    
[调用]:  
      exec USP_HLHT_ZLCZJL_MZSQFSJL_DATA '1','2018-01-01','2018-01-03','1' --通过首页序号提取数据
      exec USP_HLHT_ZLCZJL_MZSQFSJL_DATA '1','2018-01-01','2018-01-03',NULL --提取当天的数据
[注意事项]
 在CIS_HLHT中创建
*/
begin
--创建临时表
if @syxh  is null or @syxh = ''
	begin
		   SELECT QTBLJLXH,SYXH,TJZT,YXJL,BLMC,FSSJ,BLNR,BLDM INTO #EMR_QTBLJLK_LS FROM [HLHT_ZY_CIS].[CISDB].[dbo].[EMR_QTBLJLK] T(nolock)
		WHERE T.TJSJ BETWEEN CONVERT(DATE, ltrim(@startDate)) AND CONVERT(DATE, ltrim(@endDate))
		 AND T.YXJL=1
         --在临时表上增加索引
		CREATE INDEX QUERY_INDEX_LS ON #EMR_QTBLJLK_LS (BLDM);

		SELECT QTBLJLXH,SYXH,TJZT,YXJL,BLMC,FSSJ,BLNR,BLDM INTO #EMR_QTBLJLK FROM #EMR_QTBLJLK_LS T(nolock)
		LEFT JOIN MBZ_DATA_LIST_SET A(nolock) on T.BLDM=A.MODEL_CODE
		WHERE A.SOURCE_TYPE=@sourceType

		CREATE INDEX QUERY_INDEX ON #EMR_QTBLJLK (SYXH);
		 --查询表数据
		 SELECT
				ISNULL(q.QTBLJLXH,'NA') AS yjlxh,
        c.SYXH AS jzlsh,
        c.PATID AS patid,
        ISNULL(b.ZYHM, 'NA') AS zyh,
        ISNULL(
        CONVERT (VARCHAR,(o.GHXH)),
        'NA'
        ) AS mjzh,
        ISNULL(
        CONVERT (VARCHAR,(o.GHLB)),
        'NA'
        ) AS jzlb,
        -9 AS tz,
        'NA' AS dzsqsbh,
        ISNULL(c.BQDM, 'NA') AS bqdm,
        ISNULL(c.BQMC, 'NA') AS bqmc,
        ISNULL(c.KSDM, 'NA') AS ksdm,
        ISNULL(c.KSMC, 'NA') AS ksmc,
        ISNULL(m.fjh,'NA') AS bfh,
        ISNULL(b.RYCW, 'NA') AS bch,
        ISNULL(b.HZXM, 'NA') AS hzxm,
        ISNULL(b.SFZH, 'NA') AS sfzhm,
        ISNULL(b.BRXB, 'NA') AS xbdm,
        (
        SELECT
        CASE b.BRXB
        WHEN '2' THEN
        '女'
        WHEN '1' THEN
        '男'
        ELSE
        '其它'
        END
        ) AS xbmc,
        ISNULL(
        CONVERT (
        VARCHAR,
        (
        YEAR (GETDATE()) - YEAR (CONVERT(datetime, b.CSRQ))
        )
        ),

        '0'
        ) AS nls,
        DATEDIFF(MONTH,b.CSRQ,SUBSTRING(CONVERT(CHAR(8),GETDATE(),112),1,8)) %12 AS nly,
        q.SYXH AS syxh,
        q.TJZT AS tjzt,
        q.YXJL AS yxjl,
        GETDATE() AS gxsj,
        q.BLMC as blmc,
        q.FSSJ as fssj,
        q.BLNR as blnr
				FROM #EMR_QTBLJLK q
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[EMR_BRSYK] b(nolock) ON q.SYXH = b.SYXH
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[CPOE_BRSYK] c(nolock) ON b.SYXH = c.EMRXH
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[EMR_SYS_DQDMK] d(nolock) ON b.SSDM = d.DQDM
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[PUB_ZYDMK] p(nolock) ON b.ZYDM = p.ID
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[OUTP_JZJLK] o(nolock) ON q.SYXH = o.EMRXH
				LEFT JOIN [HLHT_ZY_HIS].[THIS4].[dbo].[ZY_BCDMK] m(nolock) ON (m.id = b.RYCW AND m.bqdm = b.RYBQ);
				--删除临时表
		DROP TABLE #EMR_QTBLJLK
		DROP TABLE #EMR_QTBLJLK_LS
	end
else 
	begin
		     SELECT QTBLJLXH,SYXH,TJZT,YXJL,BLMC,FSSJ,BLNR,BLDM INTO #EMR_QTBLJLK_TEMP_LS FROM [HLHT_ZY_CIS].[CISDB].[dbo].[EMR_QTBLJLK] T(nolock)
		WHERE T.TJSJ BETWEEN CONVERT(DATE, ltrim(@startDate)) AND CONVERT(DATE, ltrim(@endDate))
		 AND T.YXJL=1 AND T.SYXH=@syxh;
         --在临时表上增加索引
		CREATE INDEX QUERY_INDEX_LS ON #EMR_QTBLJLK_TEMP_LS (BLDM);

		SELECT QTBLJLXH,SYXH,TJZT,YXJL,BLMC,FSSJ,BLNR,BLDM INTO #EMR_QTBLJLK_TEMP FROM #EMR_QTBLJLK_TEMP_LS T(nolock)
		LEFT JOIN MBZ_DATA_LIST_SET A(nolock) on T.BLDM=A.MODEL_CODE
		WHERE A.SOURCE_TYPE=@sourceType;

		CREATE INDEX QUERY_INDEX ON #EMR_QTBLJLK_TEMP (SYXH);
		 --查询表数据
				SELECT
				ISNULL(q.QTBLJLXH,'NA') AS yjlxh,
        c.SYXH AS jzlsh,
        c.PATID AS patid,
        ISNULL(b.ZYHM, 'NA') AS zyh,
        ISNULL(
        CONVERT (VARCHAR,(o.GHXH)),
        'NA'
        ) AS mjzh,
        ISNULL(
        CONVERT (VARCHAR,(o.GHLB)),
        'NA'
        ) AS jzlb,
        -9 AS tz,
        'NA' AS dzsqsbh,
        ISNULL(c.BQDM, 'NA') AS bqdm,
        ISNULL(c.BQMC, 'NA') AS bqmc,
        ISNULL(c.KSDM, 'NA') AS ksdm,
        ISNULL(c.KSMC, 'NA') AS ksmc,
        ISNULL(m.fjh,'NA') AS bfh,
        ISNULL(b.RYCW, 'NA') AS bch,
        ISNULL(b.HZXM, 'NA') AS hzxm,
        ISNULL(b.SFZH, 'NA') AS sfzhm,
        ISNULL(b.BRXB, 'NA') AS xbdm,
        (
        SELECT
        CASE b.BRXB
        WHEN '2' THEN
        '女'
        WHEN '1' THEN
        '男'
        ELSE
        '其它'
        END
        ) AS xbmc,
        ISNULL(
        CONVERT (
        VARCHAR,
        (
        YEAR (GETDATE()) - YEAR (CONVERT(datetime, b.CSRQ))
        )
        ),

        '0'
        ) AS nls,
        DATEDIFF(MONTH,b.CSRQ,SUBSTRING(CONVERT(CHAR(8),GETDATE(),112),1,8)) %12 AS nly,
        q.SYXH AS syxh,
        q.TJZT AS tjzt,
        q.YXJL AS yxjl,
        GETDATE() AS gxsj,
        q.BLMC as blmc,
        q.FSSJ as fssj,
        q.BLNR as blnr
				FROM #EMR_QTBLJLK_TEMP q
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[EMR_BRSYK] b(nolock) ON q.SYXH = b.SYXH
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[CPOE_BRSYK] c(nolock) ON b.SYXH = c.EMRXH
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[EMR_SYS_DQDMK] d(nolock) ON b.SSDM = d.DQDM
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[PUB_ZYDMK] p(nolock) ON b.ZYDM = p.ID
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[OUTP_JZJLK] o(nolock) ON q.SYXH = o.EMRXH
				LEFT JOIN [HLHT_ZY_HIS].[THIS4].[dbo].[ZY_BCDMK] m(nolock) ON (m.id = b.RYCW AND m.bqdm = b.RYBQ);
				--删除临时表
		DROP TABLE #EMR_QTBLJLK_TEMP
		DROP TABLE #EMR_QTBLJLK_TEMP_LS
	end

end

