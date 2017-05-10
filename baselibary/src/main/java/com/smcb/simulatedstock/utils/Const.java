package com.smcb.simulatedstock.utils;

public interface Const {

	short shSetcode = 1; // 上海
	short szSetcode = 0; // 深圳
	short hkSetcode = 2;// 港股
	short sfSetcode = 3;// 股指期货
	short scSetcode = 4;// 上海商品期货
	short dcSetcode = 5;// 大连商品期货
	short zcSetcode = 6;// 郑州商品期货
	short bhSetcode = 7;// 渤海商品
	short sjSetcode = 8;// 上海金
	short ldjSetcode = 9;// 伦敦金
	short tjSetcode = 10;// 天津贵金属
	short dySetcode = 11;// 大圆银泰
	short gdSetcode = 12;// 广东贵金属

	// v3.4新增 港美股
	// setcode
	int SETCODE_NASDAQ = 13;// 纳斯达克
	int SETCODE_NYSE = 14;// 纽交所
	int SETCODE_AMEX = 15;// 美交所
	int SETCODE_HIS = 16;// 港股指数
	int SETCODE_USI = 17;// 美股指数
	int SETCODE_NK225 = 18;// 日经225指数
	int SETCODE_KOSPI = 19;// 韩国综合指数
	int SETCODE_TWII = 20;// 台湾加权指数
	int SETCODE_STI = 21;// 海湾指数
	int SETCODE_KLSE = 22;// 马来综合指数
	int SETCODE_SETI = 23;// 泰国综合指数
	int SETCODE_JKSE = 24;// 印尼综合指数
	int SETCODE_AORD = 25;// 澳大利亚综合指数
	int SETCODE_NZSE = 26;// 新西兰综合指数
	int SETCODE_SENSEX = 27;// 印度孟买SENSEX指数
	int SETCODE_GSPTSE = 28;// 加拿大综合指数
	int SETCODE_USD = 29;// 美元指数
	int SETCODE_CAC = 30;// 法国CAC40指数
	int SETCODE_DAX = 31;// 德国DAX指数
	int SETCODE_AEX = 32;// 荷兰AEX指数
	int SETCODE_OMX20 = 33;// 丹麦KFX指数
	int SETCODE_BFX = 34;// 比利时BFX指数
	int SETCODE_SSMI = 35;// 瑞士SSMI指数
	int SETCODE_IBOVES = 36;// 巴西综合指数
	int SETCODE_RTS = 37;// 俄罗斯RTS指数
	int SETCODE_MIB = 38;// 意大利MIB指数
	int SETCODE_FX = 39;// 其它指数
	int SETCODE_FTSE = 40;// 富时指数
	int SETCODE_COMEX = 41;// 纽约商品交易所
	int SETCODE_LME = 42;// 伦敦金属交易所
	int SETCODE_NYMEX = 43;// 纽约商业交易所
	int SETCODE_CBOT = 44;// 芝加哥交易所
	int SETCODE_IPE = 45;// 国际石油交易所

	int SETCODE_XSB=47;//新三板

	// 证券类型
	int CODE_SZAG = 0; // A股
	int CODE_SZQZ = 1; // 权证
	int CODE_SZGZ = 2; // 国债
	int CODE_SZZQ = 3; // 企债
	int CODE_SZKZHZQ = 4; // 转债
	int CODE_SZGZHG = 5; // 回购
	int CODE_SZJJ = 6; // 基金
	int CODE_SZBG = 7; // B股
	int CODE_SZCY = 8; // 中小企业
	int CODE_SZOTHER = 9; // 其它

	int CODE_SHAG = 10; // A股
	int CODE_SHQZ = 11; // 权证
	int CODE_SHGZ = 12; // 国债
	int CODE_SHZQ = 13; // 企债
	int CODE_SHKZHZQ = 14; // 转债
	int CODE_SHGZHG = 15; // 回购
	int CODE_SHJJ = 16; // 基金
	int CODE_SHBG = 17; // B股
	int CODE_SHOTHER = 18; // 其它

	int CODE_KFJJ = 19;// 开放式基金
	int CODE_SB = 20; // 三板

	int CODE_SZSPEC = 22;
	int CODE_SHSPEC = 23;

	int CODE_SZ300CY = 24;// 300 开头的创业板

	int CODE_HK = 25;
	int CODE_SF = 26;
	int CODE_SC = 27;
	int CODE_DC = 28;
	int CODE_ZC = 29;
	int CODE_BH = 30;
	String CLTP = "CLTP";// 客户端类别 1、场内WIN-PC客户端 2、WIN-PC客户端 3、手机客户端
	// 4、网页委托 5、监控客户端
	String MAC = "MAC";// Mac地址
	String DISK = "DISK";// 硬件信息
	String YYB = "YYB";// 营业部代码
	String KHH = "KHH";// 客户号
	String JYMM = "JYMM";// 交易密码
	String ZHLB = "ZHLB";// 账号类别
	String CVER = "CVER";// 客户端版本
	String GVER = "GVER";// 业务功能版本号
	String SEID = "SEID";// 交易用户校验编号
	String NEWM = "NEWM";// 新交易密码/新资金密码
	String ZJMM = "ZJMM";// 资金密码
	String BZ = "BZ";// 币种类型
	String ZZFX = "ZZFX";// 转账方向
	String YHDM = "YHDM";// 银行代码
	String YHMM = "YHMM";// 银行密码
	String ZZJE = "ZZJE";// 转账金额
	String BEGD = "BEGD";// 起始日期
	String ENDD = "ENDD";// 终止日期
	String BEGN = "BEGN";// 起始序号
	String QLEN = "QLEN";// 请求记录数
	String GDDM = "GDDM";// 股东代码
	String JYSM = "JYSM";// 交易所代码
	String ZQDM = "ZQDM";// 证券代码
	String WTJG = "WTJG";// 委托价格
	String BSFG = "BSFG";// 买卖标志
	String JYDW = "JYDW";// 交易单位
	String WTSL = "WTSL";// 委托数量
	String WTBH = "WTBH";// 委托编号
	String KHMC = "KHMC";// 客户名称
	String KHQX = "KHQX";// 客户权限
	String LDA = "LDA";// 上次登录日期
	String LIP = "LIP";// 上次登录IP
	String LMAC = "LMAC";// 上次登陆的MAC
	String LTI = "LTI";// 上次登录时间
	String ZXSJ = "ZXSJ";// 在线时间
	String KQJE = "KQJE";// 可取金额
	String KYZJ = "KYZJ";// 可用资金
	String KQXJ = "KQXJ";// 可取现金
	String ZCZZ = "ZCZZ";// 资产总值
	String ZSZ = "ZSZ";// 总市值
	String SECK = "SECK";// 密钥串
	String ERMS = "ERMS";// 错误代码
	String ERMT = "ERMT";// 错误信息
	String CZBZ = "CZBZ";// 操作确认，0：查询银行流水 1：查询银证通资金 CHAR 6
	String CZQR = "CZQR";// 操作确认，1，则只查询可以撤单的委托；0及其他，则查询全部委托
	String HDEX = "HDEX";// 扩展字段
	String ZJZH = "ZJZH";// 资金帐号
	String MPS = "MPS";// 定位串
	String YHZH = "YHZH";// 银行帐户
	String LSH = "LSH";// 流水号
	String SGJG = "SGJG";// 申购价格
	String ZQMC = "ZQMC";// 证券名称
	String ZJYE = "ZJYE";// 资金余额
}
