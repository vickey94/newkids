package gxnu.newkids.entity;

import java.sql.Timestamp;

//t_user_wb 用户单词书表
public class UserWb {

    private Integer u_wb_id;

    private String open_id;

    private String wb_id;

    private Integer u_wb_status;

    private Integer u_wb_rate;

    private Timestamp u_wb_time;

    @Override
    public String toString() {
        return "UserWb{" +
                "u_wb_id=" + u_wb_id +
                ", open_id='" + open_id + '\'' +
                ", wb_id=" + wb_id +
                ", u_wb_status=" + u_wb_status +
                ", u_wb_rate=" + u_wb_rate +
                ", u_wb_time=" + u_wb_time +
                '}';
    }

    public Integer getU_wb_id() {
        return u_wb_id;
    }

    public void setU_wb_id(Integer u_wb_id) {
        this.u_wb_id = u_wb_id;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getWb_id() {
        return wb_id;
    }

    public void setWb_id(String wb_id) {
        this.wb_id = wb_id;
    }

    public Integer getU_wb_status() {
        return u_wb_status;
    }

    public void setU_wb_status(Integer u_wb_status) {
        this.u_wb_status = u_wb_status;
    }

    public Integer getU_wb_rate() {
        return u_wb_rate;
    }

    public void setU_wb_rate(Integer u_wb_rate) {
        this.u_wb_rate = u_wb_rate;
    }

    public Timestamp getU_wb_time() {
        return u_wb_time;
    }

    public void setU_wb_time(Timestamp u_wb_time) {
        this.u_wb_time = u_wb_time;
    }
}
