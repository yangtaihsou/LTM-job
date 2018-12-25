package io.bitjob.export;


public enum EmTaskType {

    trade_loanFlow(100, "网贷交易系统-放款"),
    trade_loanFlow_vir(101, "网贷交易系统-放款虚户充值"),

    trade_repayFlow(110, "网贷交易系统-还款"),
    trade_repayFlow_vir(111, "网贷交易系统-还款虚户充值");

    private final int taskType;
    private final String title;

    private EmTaskType(int taskType, String title) {

        this.taskType = taskType;
        this.title = title;
    }

    public int taskType() {
        return taskType;
    }


    public String title() {
        return title;
    }
}
