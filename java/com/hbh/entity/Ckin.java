package com.hbh.entity;

import java.util.Date;

public class Ckin {
    private String inid;

    private String proid;

    private String pname;

    private Integer num;

    private Date indate;

    private String marks;

    // 添加入库状态字段
    private Integer status = 0;

    public String getInid() {
        return inid;
    }

    public void setInid(String inid) {
        this.inid = inid == null ? null : inid.trim();
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid == null ? null : proid.trim();
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getIndate() {
        return indate;
    }

    public void setIndate(Date indate) {
        this.indate = indate;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks == null ? null : marks.trim();
    }

    // 添加入库状态字段的getter和setter方法
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}