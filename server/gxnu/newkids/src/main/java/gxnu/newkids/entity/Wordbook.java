package gxnu.newkids.entity;

//t_wordbooks 单词书表
public class Wordbook {

   private String wb_id;

   private String wordbook;

   private Integer wb_num;

   private Integer wb_status;

   private Integer wb_order;

   private float wb_score;

   private Integer wb_parent_id;

    @Override
    public String toString() {
        return "Wordbook{" +
                "wb_id=" + wb_id +
                ", wordbook='" + wordbook + '\'' +
                ", wb_num=" + wb_num +
                ", wb_status=" + wb_status +
                ", wb_order=" + wb_order +
                ", wb_score=" + wb_score +
                ", wb_parent_id=" + wb_parent_id +
                '}';
    }

    public String getWb_id() {
        return wb_id;
    }

    public void setWb_id(String wb_id) {
        this.wb_id = wb_id;
    }

    public String getWordbook() {
        return wordbook;
    }

    public void setWordbook(String wordbook) {
        this.wordbook = wordbook;
    }

    public Integer getWb_num() {
        return wb_num;
    }

    public void setWb_num(Integer wb_num) {
        this.wb_num = wb_num;
    }

    public Integer getWb_status() {
        return wb_status;
    }

    public void setWb_status(Integer wb_status) {
        this.wb_status = wb_status;
    }

    public Integer getWb_order() {
        return wb_order;
    }

    public void setWb_order(Integer wb_order) {
        this.wb_order = wb_order;
    }

    public float getWb_score() {
        return wb_score;
    }

    public void setWb_score(float wb_score) {
        this.wb_score = wb_score;
    }

    public Integer getWb_parent_id() {
        return wb_parent_id;
    }

    public void setWb_parent_id(Integer wb_parent_id) {
        this.wb_parent_id = wb_parent_id;
    }
}
