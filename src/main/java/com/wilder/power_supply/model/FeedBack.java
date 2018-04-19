package com.wilder.power_supply.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author:Wilder Gao
 * @time:2018/4/20
 * @Discription：返回格式类
 */
public class FeedBack<T> {
    @Getter @Setter
    private int status;

    @Getter @Setter
    private T info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "FeedBack{" +
                "status=" + status +
                ", info=" + info +
                '}';
    }
}
