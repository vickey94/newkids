package gxnu.newkids.entity;

import java.sql.Timestamp;

//t_usersessionm
public class UserSession {

    private String open_id;
    private String uuid;
    private String skey;
    private Timestamp create_time;
    private Timestamp last_visit_time;
    private String session_key;
    private String user_info;

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getLast_visit_time() {
        return last_visit_time;
    }

    public void setLast_visit_time(Timestamp last_visit_time) {
        this.last_visit_time = last_visit_time;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getUser_info() {
        return user_info;
    }

    public void setUser_info(String user_info) {
        this.user_info = user_info;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "open_id='" + open_id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", skey='" + skey + '\'' +
                ", create_time=" + create_time +
                ", last_visit_time=" + last_visit_time +
                ", session_key='" + session_key + '\'' +
                ", user_info='" + user_info + '\'' +
                '}';
    }
}
