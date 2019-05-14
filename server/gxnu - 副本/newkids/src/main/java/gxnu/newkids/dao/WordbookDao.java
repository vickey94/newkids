package gxnu.newkids.dao;


import gxnu.newkids.entity.UserWb;
import gxnu.newkids.entity.Wordbook;

import java.util.List;

public interface  WordbookDao {

    List<UserWb> selectUserWb(String open_id, String wb_id, int u_wb_status);

    void updateWbStatus(String open_id,String wb_id,int u_wb_status);

    void insertWb(UserWb userWb);

    List<Wordbook> selectWb(String wb_id);

}
