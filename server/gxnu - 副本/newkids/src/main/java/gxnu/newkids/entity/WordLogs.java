package gxnu.newkids.entity;

import java.sql.Timestamp;

//t_word_logs 背单词记录
public class WordLogs {

    private Integer w_logs_id;
    private String open_id;
    private String wb_id;
    private String bw_id;
    private Integer w_score;
    private Integer w_spend_time;
    private Timestamp w_last_time;

    @Override
    public String toString() {
        return "WordLogs{" +
                "w_logs_id=" + w_logs_id +
                ", bw_id='" + bw_id + '\'' +
                ", open_id='" + open_id + '\'' +
                ", wb_id='" + wb_id + '\'' +
                ", w_score=" + w_score +
                ", w_spend_time=" + w_spend_time +
                ", w_last_time=" + w_last_time +
                '}';
    }

    public Integer getW_logs_id() {
        return w_logs_id;
    }

    public void setW_logs_id(Integer w_logs_id) {
        this.w_logs_id = w_logs_id;
    }

    public String getBw_id() {
        return bw_id;
    }

    public void setBw_id(String bw_id) {
        this.bw_id = bw_id;
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

    public Integer getW_score() {
        return w_score;
    }

    public void setW_score(Integer w_score) {
        this.w_score = w_score;
    }

    public Integer getW_spend_time() {
        return w_spend_time;
    }

    public void setW_spend_time(Integer w_spend_time) {
        this.w_spend_time = w_spend_time;
    }

    public Timestamp getW_last_time() {
        return w_last_time;
    }

    public void setW_last_time(Timestamp w_last_time) {
        this.w_last_time = w_last_time;
    }
}
