package io.bitjob.domain;

/**

 * Date: 14-9-9
 * Time: 下午5:14
 */
public class TaskEnum {


    public enum TaskStatus {
        init(0, "子任务初始化"),
        lock(1, "子任务锁定"),
        finish(2, "子任务完成"),
        stop(3, "子任务暂停");
        private final Integer status;
        private final String desc;

        private TaskStatus(Integer status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        public Integer status() {
            return status;
        }

        public String desc() {
            return desc;
        }
    }

    public enum TaskResultStatus {
        init(0, "任务结果初始化"),
        success(1, "任务结果成功"),
        fail(2, "任务结果失败");
        private final Integer status;
        private final String desc;

        private TaskResultStatus(Integer status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        public Integer status() {
            return status;
        }

        public String desc() {
            return desc;
        }
    }


    public enum TaskType {
        init(100, "保存营销申请原始数据任务"),
        appendPolicyOrder(101, "追加保单"),
        createPayRequest(103, "创建支付单"),
        createTransOrder(104, "创建转账"),
        createPayResult(105, "创建支付成功单"),
        grantResult(106, "发放结果mq");
        private final Integer type;
        private final String desc;

        private TaskType(Integer type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public Integer type() {
            return type;
        }

        public String desc() {
            return desc;
        }
    }
}
