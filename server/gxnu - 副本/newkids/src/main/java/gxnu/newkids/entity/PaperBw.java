package gxnu.newkids.entity;

//t_paper_bw 文章单词表
public class PaperBw {

    private Integer p_bw_id;
    private Integer p_id;
    private String bw_id;
    private float p_bw_score;

    @Override
        public String toString() {
            return "PaperBw{" +
                "p_bw_id=" + p_bw_id +
                ", p_id=" + p_id +
                ", bw_id=" + bw_id +
                ", p_bw_score=" + p_bw_score +
                '}';
    }

    public Integer getP_bw_id() {
        return p_bw_id;
    }

    public void setP_bw_id(Integer p_bw_id) {
        this.p_bw_id = p_bw_id;
    }

    public Integer getP_id() {
        return p_id;
    }

    public void setP_id(Integer p_id) {
        this.p_id = p_id;
    }

    public String getBw_id() {
        return bw_id;
    }

    public void setBw_id(String bw_id) {
        this.bw_id = bw_id;
    }

    public float getP_bw_score() {
        return p_bw_score;
    }

    public void setP_bw_score(float p_bw_score) {
        this.p_bw_score = p_bw_score;
    }
}
