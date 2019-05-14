package gxnu.newkids.entity;

public class W2W {
    private Integer w2w_id;
    private String raw_word;
    private String now_word;

    @Override
    public String toString() {
        return "W2W{" +
                "w2w_id=" + w2w_id +
                ", raw_word='" + raw_word + '\'' +
                ", now_word='" + now_word + '\'' +
                '}';
    }

    public Integer getW2w_id() {
        return w2w_id;
    }

    public void setW2w_id(Integer w2w_id) {
        this.w2w_id = w2w_id;
    }

    public String getRaw_word() {
        return raw_word;
    }

    public void setRaw_word(String raw_word) {
        this.raw_word = raw_word;
    }

    public String getNow_word() {
        return now_word;
    }

    public void setNow_word(String now_word) {
        this.now_word = now_word;
    }
}
