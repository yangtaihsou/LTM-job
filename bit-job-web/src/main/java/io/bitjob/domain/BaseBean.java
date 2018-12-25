package io.bitjob.domain;

import java.io.Serializable;

/**
 * <strong>ClassName :BaseBean <br>
 * </strong> <strong>Description :<br>
 * </strong> <strong>Create on : 2013-3-12<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) 京东商城 Software Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author 杨宽 yangkuan@360buy.com <br>
 * @version V1.0 <strong></strong><br>
 * <br>
 * <strong>修改历史:</strong><br>
 * 修改人 修改日期 修改描述<br>
 * -------------------------------------------<br>
 * <br>
 * <br>
 */
public abstract class BaseBean implements Serializable {

    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 1L;
    private String orderBy;
    private int startRow = 0;
    private int endRow = 0;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }


}
