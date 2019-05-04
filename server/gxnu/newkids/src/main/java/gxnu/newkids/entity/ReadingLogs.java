package gxnu.newkids.entity;

import java.sql.Timestamp;

//t_reading_logs 阅读记录表
public class ReadingLogs {

   private Integer r_logs_id;
   private Integer p_id;
   private String open_id;
   private Integer r_score;
   private Timestamp r_start_time;
   private Timestamp r_end_time;

   @Override
   public String toString() {
      return "ReadingLogs{" +
              "r_logs_id=" + r_logs_id +
              ", p_id=" + p_id +
              ", open_id='" + open_id + '\'' +
              ", r_score=" + r_score +
              ", r_start_time=" + r_start_time +
              ", r_end_time=" + r_end_time +
              '}';
   }

   public Integer getR_logs_id() {
      return r_logs_id;
   }

   public void setR_logs_id(Integer r_logs_id) {
      this.r_logs_id = r_logs_id;
   }

   public Integer getP_id() {
      return p_id;
   }

   public void setP_id(Integer p_id) {
      this.p_id = p_id;
   }

   public String getOpen_id() {
      return open_id;
   }

   public void setOpen_id(String open_id) {
      this.open_id = open_id;
   }

   public Integer getR_score() {
      return r_score;
   }

   public void setR_score(Integer r_score) {
      this.r_score = r_score;
   }

   public Timestamp getR_start_time() {
      return r_start_time;
   }

   public void setR_start_time(Timestamp r_start_time) {
      this.r_start_time = r_start_time;
   }

   public Timestamp getR_end_time() {
      return r_end_time;
   }

   public void setR_end_time(Timestamp r_end_time) {
      this.r_end_time = r_end_time;
   }
}
