package io.bitjob.domain;

/**
 *
 */
public class Constants {

/**********************支付通知类型******************/

    /**
     * 特殊的一个常量，仅用于区分主、子WORKER
     */
    public static final String NOTIFY_TYPE_MAIN = "NOTIFY_MAIN";

    /**
     * 特殊的一个常量，仅用于区分系统部署的方式
     */
    public static final String DEPLOY_MODE = "DEPLOY_MODE";

    /**
     * POP
     */
    public static final String NOTIFY_TYPE_POP = "NOTIFY_POP";

    /**
     * 团购
     */
    public static final String NOTIFY_TYPE_TUAN = "NOTIFY_TUAN";

    /**
     * 消息中间件
     */
    public static final String NOTIFY_TYPE_MESSAGE = "NOTIFY_MESSAGE";

    /**
     * 订单中间件
     */
    public static final String NOTIFY_TYPE_ORDER = "NOTIFY_ORDER";

    /**
     * 财务
     */
    public static final String NOTIFY_TYPE_FINANCE = "NOTIFY_FINANCE";

    /**
     * 写支付结果表
     */
    public static final String NOTIFY_TYPE_PAY_RESULT = "NOTIFY_PAY_RESULT";

    /**
     * 订单台账
     */
    public static final String NOTIFY_TYPE_ORDER_BANK = "NOTIFY_ORDER_BANK";

    /**
     * 订单任务
     */
    public static final String NOTIFY_TYPE_ORDER_TASK = "NOTIFY_ORDER_TASK";
    /**
     * 快捷支付
     */
    public static final String NOTIFY_TYPE_QUICKPAY_TASK = "NOTIFY_QUICKPAY_TASK";


    /**********************数据库主键唯一冲突类型******************/

    /**
     * MSSQL 主键或唯一索引冲突
     */
    public static final String SQLSTATE_DUPLICATE_INDEX_MSSQL = "2627";
    /**
     * MSSQL 主键或唯一索引冲突
     */
    public static final String SQLSTATE_uuid_INDEX_MSSQL = "2601";
    /**
     * ORACLE 主键或唯一索引冲突
     */
    public static final String SQLSTATE_DUPLICATE_INDEX_ORACLE = "23000";

    /**
     * MYSQL 主键或唯一索引冲突
     */
    public static final String SQLSTATE_DUPLICATE_INDEX_MYSQL = "23000";

    /**
     * DB2 主键或唯一索引冲突
     */
    public static final String SQLSTATE_DUPLICATE_INDEX_DB2 = "23505";


    /**********************任务开关类型******************/

    /**
     * 开启服务
     */
    public static final String TASK_SWITCH_TYPE_ON = "1";

    /**
     * 关闭服务
     */
    public static final String TASK_SWITCH_TYPE_OFF = "0";


    /**********************系统部署类型******************/

    /**
     * 系统部署模式：集中部署
     */
    public static final String DEPLOY_MODE_ALL = "0";

    /**
     * 系统部署模式：分开部署
     */
    public static final String DEPLOY_MODE_MORE = "1";

    /**********************任务执行状态******************/

    /**
     * 任务未完成
     */
    public static final int TASK_STATUS_UNFINISHED = 0;

    /**
     * 任务锁定
     */
    public static final int TASK_STATUS_LOCK = 1;

    /**
     * 任务完成
     */
    public static final int TASK_STATUS_SUCCESS = 2;

    /**
     * 任务失败
     */
    public static final int TASK_STATUS_FAILED = 3;

    /**
     * 任务成功 订单取消
     */
    public static final int TASK_STATUS_SUCCESS_ORDER_CANCLE = 4;

    /**
     * 任务失败重试后成功
     */
    public static final int TASK_STATUS_FAILED_RESET_SUCCESS = 5;

    /**
     * 订单状态已被更新 任务应该为成功
     */
    public static final int TASK_STATUS_SUCCESS_ORDER_UPDATED = 6;

    /**********************是否使用支付单号开关******************/

    /**
     * 不使用支付单号
     */
    public static final String PAYID_ISOPEN_NO = "0";

    /**
     * 使用支付单号
     */
    public static final String PAYID_ISOPEN_YES = "3";


    /**********************系统部署类型******************/

    /**
     * 新版支付平台号：手机支付010
     */
    public static final String NEW_PAYMENT_PLAT_CMPAY = "010";

    /**
     * 新版支付平台号：银联支付020
     */
    public static final String NEW_PAYMENT_PLAT_UNIONPAY = "020";

    /**
     * 新版支付平台号：嗖付支付030
     */
    public static final String NEW_PAYMENT_PLAT_UMPAY = "030";

    /**
     * 新版支付平台号：财付通040
     */
    public static final String NEW_PAYMENT_PLAT_TENPAY = "040";

    /**
     * 新版支付平台号：快钱支付050
     */
    public static final String NEW_PAYMENT_PLAT_QUICKMONEY = "050";

    /**
     * 新版支付平台号：汇付支付060
     */
    public static final String NEW_PAYMENT_PLAT_CHINAPNR = "060";

    public static final String METRICS_ERROR_KEY = "Web.payworker.";


    /***************任务处理默认配置************************/
    /**
     * 默认失败重试次数
     */
    public static final int TASK_CONFIG_DEFAULT_RETRYCOUNT = 100;

    /**
     * 开启两次解锁时第二次解锁失败重试次数开始
     */
    public static final int TASK_CONFIG_RETRYCOUNT_2_START = 6;

    /**
     * 开启两次解锁时第二次解锁失败重试次数结束
     */
    public static final int TASK_CONFIG_RETRYCOUNT_2_END = 12;

    /**
     * 默认清除多少小时前的成功完成的任务数据(小时)
     */
    public static final int TASK_CONFIG_CLEAR_DEFAULT_HOURAGO = 24;
    /**
     * 默认任务锁定最大时长（秒）
     */
    public static final int TASK_CONFIG_TASKLOCK_DEFAULT_MAXSECONDS = 5 * 60;
    /**
     * 默认任务累积数量限制
     */
    public static final int TASK_CONFIG_ACCUMULATE_DEFAULT_ALLOWNUMBER = 100;
    /**
     * 默认任务累积数量限制
     */
    public static final int TASK_CONFIG_LOCK_DEFAULT_ALLOWSECONDS = 10 * 60;
    /**
     * 默认任务累积数量限制
     */
    public static final int TASK_CONFIG_UNPROCCESS_DEFAULT_ALLOWSECONDS = 10 * 60;
    /**
     * 默认每次批量处理的大小
     */
    public static final int TASK_BATCH_DEFAULT_SIZE = 10;

    /*********************主任务类型********************/
    /**
     * 默认为B28服务器子任务
     */
    public static final String MAIN_TYPE_DEFAULT = "0";

    /**
     * 亦庄服务器主任务
     */
    public static final String MAIN_TYPE_YIZHUANG = "1";
    /**
     * worker使用新旧方式的标识
     * 0标识旧方式
     */
    public static final String WORKER_FLAG_OLD = "0";
    /**
     * worker使用新旧方式的标识
     * 1标识新方式
     */
    public static final String WORKER_FLAG_NEW = "1";

    /********************使用配置文件的方式 新旧方式********************/

    /**
     * 配置文件使用新旧方式的标识
     * old标识旧方式
     */
    public static final String CONFIG_FLAG_OLD = "old";
    /**
     * 配置文件使用新旧方式的标识
     * new标识新方式
     */
    public static final String CONFIG_FLAG_NEW = "new";

    public static final String CONSOLE_KEY = "local.jd.baina.job";


    public static final String ALERT_KEY = "local.jd.p2p2.job.alarm";


}
