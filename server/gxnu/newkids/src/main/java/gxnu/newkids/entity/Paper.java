package gxnu.newkids.entity;

import java.sql.Timestamp;

//t_papers 文章表
public class Paper {

    private Integer p_id;
    private String p_abstract;
    private String paper;
    private String paper_raw;
    private float p_score;
    private Integer p_times;
    private Timestamp p_update_time;

    @Override
    public String toString() {
        return "Paper{" +
                "p_id=" + p_id +
                ", p_abstract='" + p_abstract + '\'' +
                ", paper='" + paper + '\'' +
                ", paper_raw='" + paper_raw + '\'' +
                ", p_score=" + p_score +
                ", p_times=" + p_times +
                ", p_update_time=" + p_update_time +
                '}';
    }

    public Integer getP_id() {
        return p_id;
    }

    public void setP_id(Integer p_id) {
        this.p_id = p_id;
    }

    public String getP_abstract() {
        return p_abstract;
    }

    public void setP_abstract(String p_abstract) {
        this.p_abstract = p_abstract;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getPaper_raw() {
        return paper_raw;
    }

    public void setPaper_raw(String paper_raw) {
        this.paper_raw = paper_raw;
    }

    public float getP_score() {
        return p_score;
    }

    public void setP_score(float p_score) {
        this.p_score = p_score;
    }

    public Integer getP_times() {
        return p_times;
    }

    public void setP_times(Integer p_times) {
        this.p_times = p_times;
    }

    public Timestamp getP_update_time() {
        return p_update_time;
    }

    public void setP_update_time(Timestamp p_update_time) {
        this.p_update_time = p_update_time;
    }
}
