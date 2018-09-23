CREATE PROCEDURE [dbo].[USP_HLHT_ZYBCJL_CYJL_DATA]
@sourceType varchar(64),   --原纪录类型
@startDate  varchar(20),   --开始日期
@endDate    varchar(20),   --结束日期
@syxh       int            --首页序号
as
/*
[创建者] chenfeng
[公司]上海金仕达卫宁软件股份有限公司@2015-2018      
[时间]2018-09-23    
[功能]导出出院记录 ---USP_HLHT_ZYBCJL_CYJL_DATA
[参数]
 @sourceType: 元数据类型
 @startime: 开始时间戳
 @endtime:  结束时间戳
 @syxh：病人首页序号 
[调用实例]    
[调用]:  
      exec USP_HLHT_ZYBCJL_CYJL_DATA '1','2018-01-01','2018-01-03','1' --通过首页序号提取数据
      exec USP_HLHT_ZYBCJL_CYJL_DATA '1','2018-01-01','2018-01-03',NULL --提取当天的数据
[注意事项]
 在CIS_HLHT中创建
*/
begin
--创建临时表
if @syxh  is null or @syxh = ''
	begin
		SELECT * INTO #EMR_QTBLJLK FROM [HLHT_ZY_CIS].[CISDB].[dbo].[EMR_QTBLJLK] T(nolock) 
		WHERE EXISTS (SELECT 1 FROM MBZ_DATA_LIST_SET A(nolock) WHERE A.SOURCE_TYPE=@sourceType and A.MODEL_CODE = T.BLDM)
		 AND T.TJSJ BETWEEN CONVERT(DATE, ltrim(@startDate)) AND CONVERT(DATE, ltrim(@endDate))
		 AND T.TJSJ IS NOT NULL  AND T.TJSJ !=''
		 AND T.yxjl=1x;
		 --在临时表上增加索引
		 CREATE INDEX QUERY_INDEX ON #EMR_QTBLJLK (BLDM, YXJL, TJSJ);
		 --查询表数据
		 SELECT
				ISNULL(q.QTBLJLXH, 'NA') AS yjlxh,
				c.SYXH AS jzlsh,
				c.PATID AS patid,
				ISNULL(b.ZYHM, 'NA') AS zyh,
				ISNULL(c.BQDM, 'NA') AS bqdm,
				ISNULL(c.BQMC, 'NA') AS bqmc,
				ISNULL(c.KSDM, 'NA') AS ksdm,
				ISNULL(c.KSMC, 'NA') AS ksmc,
				ISNULL(m.fjh, 'NA') AS bfh,
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
				DATEDIFF(
					MONTH,
					b.CSRQ,
					SUBSTRING (
						CONVERT (CHAR(8), GETDATE(), 112),
						1,
						8
					)
				) % 12 AS nly,
				ISNULL(b.MZDM, 'NA') AS mz,
				ISNULL(z.NAME, 'NA') AS mzmc,
				ISNULL(b.HYZK, 'NA') AS hyzkdm,
				(
					SELECT
						CASE b.HYZK
					WHEN '0' THEN
						'未婚'
					WHEN '1' THEN
						'已婚'
					WHEN '2' THEN
						'离独'
					WHEN '3' THEN
						'丧偶'
					ELSE
						'未知 '
					END
				) AS hyzkmc,
				ISNULL(
					(
						SELECT
							e.NAME
						FROM
							CISDB.dbo.EMR_SYS_DQDMK e
						WHERE
							b.SSDM = e.DQDM
					),
					'NA'
				) AS dzsf,
				ISNULL(
					(
						SELECT
							e.NAME
						FROM
							[HLHT_ZY_CIS].[CISDB].[dbo].[EMR_SYS_DQDMK] e(nolock)
						WHERE
							b.QXDM = e.DQDM
					),
					'NA'
				) AS dzsq,
				ISNULL(
					(
						SELECT
							e.NAME
						FROM
							[HLHT_ZY_CIS].[CISDB].[dbo].[EMR_SYS_DQDMK] e(nolock)
						WHERE
							b.QXDM = e.DQDM
					),
					'NA'
				) AS dzx,
				ISNULL(
					(
						SELECT
							e.NAME
						FROM
							[HLHT_ZY_CIS].[CISDB].[dbo].[EMR_SYS_DQDMK] e(nolock)
						WHERE
							b.QXDM = e.DQDM
					),
					'NA'
				) AS dzxz,
				'NA' AS dzc,
				'NA' AS dzmphm,
				ISNULL(b.ZYDM, 'NA') AS zylbdm,
				ISNULL(i.NAME, 'NA') AS zylbmc,
				b.RYRQ AS ryrq,
				q.SYXH AS syxh,
				q.TJZT AS tjzt,
				q.YXJL AS yxjl,
				GETDATE() AS gxsj,
			  q.SYXH as syxh,
        q.TJZT as tjzt,
        q.YXJL as yxjl,
        q.BLMC as blmc,
        q.FSSJ as fssj,
        q.BLNR as blnr
				FROM #EMR_QTBLJLK q
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[EMR_BRSYK] b(nolock) ON q.SYXH = b.SYXH
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[EMR_SYS_ZDFLMXK] i(nolock) ON b.ZYDM = i.MXDM AND LBDM = 41
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[CPOE_BRSYK] c(nolock) ON b.SYXH = c.EMRXH
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[EMR_SYS_DQDMK] d(nolock) ON b.SSDM = d.DQDM
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[PUB_ZYDMK] p(nolock) ON b.ZYDM = p.ID
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[PUB_MZDMK] z(nolock) ON z.ID = b.MZDM
				LEFT JOIN [HLHT_ZY_HIS].[THIS4].[dbo].[ZY_BCDMK] m(nolock) ON (m.id = b.RYCW AND m.bqdm = b.RYBQ);
				--删除临时表
		DROP TABLE #EMR_QTBLJLK
	end
else 
	begin
		SELECT * INTO #EMR_QTBLJLK_TEMP FROM [HLHT_ZY_CIS].[CISDB].[dbo].[EMR_QTBLJLK] T(nolock) 
		WHERE EXISTS (SELECT 1 FROM dbo.MBZ_DATA_LIST_SET A(nolock) WHERE A.SOURCE_TYPE=@sourceType and A.MODEL_CODE = T.BLDM)
		 AND T.TJSJ BETWEEN CONVERT (DATE, @startDate) AND CONVERT (DATE, @endDate)
		 AND T.TJSJ IS NOT NULL  AND T.TJSJ !=''
		 AND T.yxjl=1
		 AND T.SYXH=@syxh;
		 --在临时表上增加索引
		 CREATE INDEX QUERY_INDEX ON #EMR_QTBLJLK_TEMP (BLDM, YXJL, TJSJ);
		 --查询表数据
				SELECT
				ISNULL(q.QTBLJLXH, 'NA') AS yjlxh,
				c.SYXH AS jzlsh,
				c.PATID AS patid,
				ISNULL(b.ZYHM, 'NA') AS zyh,
				ISNULL(c.BQDM, 'NA') AS bqdm,
				ISNULL(c.BQMC, 'NA') AS bqmc,
				ISNULL(c.KSDM, 'NA') AS ksdm,
				ISNULL(c.KSMC, 'NA') AS ksmc,
				ISNULL(m.fjh, 'NA') AS bfh,
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
				DATEDIFF(
					MONTH,
					b.CSRQ,
					SUBSTRING (
						CONVERT (CHAR(8), GETDATE(), 112),
						1,
						8
					)
				) % 12 AS nly,
				ISNULL(b.MZDM, 'NA') AS mz,
				ISNULL(z.NAME, 'NA') AS mzmc,
				ISNULL(b.HYZK, 'NA') AS hyzkdm,
				(
					SELECT
						CASE b.HYZK
					WHEN '0' THEN
						'未婚'
					WHEN '1' THEN
						'已婚'
					WHEN '2' THEN
						'离独'
					WHEN '3' THEN
						'丧偶'
					ELSE
						'未知 '
					END
				) AS hyzkmc,
				ISNULL(
					(
						SELECT
							e.NAME
						FROM
							CISDB.dbo.EMR_SYS_DQDMK e
						WHERE
							b.SSDM = e.DQDM
					),
					'NA'
				) AS dzsf,
				ISNULL(
					(
						SELECT
							e.NAME
						FROM
							[HLHT_ZY_CIS].[CISDB].[dbo].[EMR_SYS_DQDMK] e(nolock)
						WHERE
							b.QXDM = e.DQDM
					),
					'NA'
				) AS dzsq,
				ISNULL(
					(
						SELECT
							e.NAME
						FROM
							[HLHT_ZY_CIS].[CISDB].[dbo].[EMR_SYS_DQDMK] e(nolock)
						WHERE
							b.QXDM = e.DQDM
					),
					'NA'
				) AS dzx,
				ISNULL(
					(
						SELECT
							e.NAME
						FROM
							[HLHT_ZY_CIS].[CISDB].[dbo].[EMR_SYS_DQDMK] e(nolock)
						WHERE
							b.QXDM = e.DQDM
					),
					'NA'
				) AS dzxz,
				'NA' AS dzc,
				'NA' AS dzmphm,
				ISNULL(b.ZYDM, 'NA') AS zylbdm,
				ISNULL(i.NAME, 'NA') AS zylbmc,
				b.RYRQ AS ryrq,
				q.SYXH AS syxh,
				q.TJZT AS tjzt,
				q.YXJL AS yxjl,
				GETDATE() AS gxsj,
				q.SYXH as syxh,
        q.TJZT as tjzt,
        q.YXJL as yxjl,
        q.BLMC as blmc,
        q.FSSJ as fssj,
        q.BLNR as blnr
				FROM #EMR_QTBLJLK_TEMP q
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[EMR_BRSYK] b(nolock) ON q.SYXH = b.SYXH
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[EMR_SYS_ZDFLMXK] i(nolock) ON b.ZYDM = i.MXDM AND LBDM = 41
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[CPOE_BRSYK] c(nolock) ON b.SYXH = c.EMRXH
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[EMR_SYS_DQDMK] d(nolock) ON b.SSDM = d.DQDM
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[PUB_ZYDMK] p(nolock) ON b.ZYDM = p.ID
				LEFT JOIN [HLHT_ZY_CIS].[CISDB].[dbo].[PUB_MZDMK] z(nolock) ON z.ID = b.MZDM
				LEFT JOIN [HLHT_ZY_HIS].[THIS4].[dbo].[ZY_BCDMK] m(nolock) ON (m.id = b.RYCW AND m.bqdm = b.RYBQ);
				--删除临时表
		DROP TABLE #EMR_QTBLJLK_TEMP
	end

end

