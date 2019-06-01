package gxnu.newkids.entity;

public class CibaWord {

    private Integer w_id ;
    private String word;
    private String w_exchange;
    private String w_voice;
    private Integer w_times;

    @Override
    public String toString() {
        return "CibaWord{" +
                "w_id=" + w_id +
                ", word='" + word + '\'' +
                ", w_exchange='" + w_exchange + '\'' +
                ", w_voice='" + w_voice + '\'' +
                ", w_times=" + w_times +
                '}';
    }

    public Integer getW_id() {
        return w_id;
    }

    public void setW_id(Integer w_id) {
        this.w_id = w_id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getW_exchange() {
        return w_exchange;
    }

    public void setW_exchange(String w_exchange) {
        this.w_exchange = w_exchange;
    }

    public String getW_voice() {
        return w_voice;
    }

    public void setW_voice(String w_voice) {
        this.w_voice = w_voice;
    }

    public Integer getW_times() {
        return w_times;
    }

    public void setW_times(Integer w_times) {
        this.w_times = w_times;
    }
}
