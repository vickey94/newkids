package gxnu.newkids.entity;

import java.sql.Timestamp;

//t_missing 查询无词表
public class Missing {

    private Integer miss_id;
    private String miss_word;
    private Timestamp s_time;
    private Integer miss_times;

    @Override
    public String toString() {
        return "Missing{" +
                "miss_id=" + miss_id +
                ", miss_word='" + miss_word + '\'' +
                ", s_time=" + s_time +
                ", miss_times=" + miss_times +
                '}';
    }

    public Integer getMiss_id() {
        return miss_id;
    }

    public void setMiss_id(Integer miss_id) {
        this.miss_id = miss_id;
    }

    public String getMiss_word() {
        return miss_word;
    }

    public void setMiss_word(String miss_word) {
        this.miss_word = miss_word;
    }

    public Timestamp getS_time() {
        return s_time;
    }

    public void setS_time(Timestamp s_time) {
        this.s_time = s_time;
    }

    public Integer getMiss_times() {
        return miss_times;
    }

    public void setMiss_times(Integer miss_times) {
        this.miss_times = miss_times;
    }
}
