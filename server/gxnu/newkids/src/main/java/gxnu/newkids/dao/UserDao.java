package gxnu.newkids.dao;

import gxnu.newkids.entity.UserSession;

public interface UserDao {

    UserSession getUserSession(String open_id);

    void insertUserSession(UserSession userSession);

    void updateUserSession(UserSession userSession);

    void userVisit(UserSession userSession);

    void userCheck(String open_id);

    int getUserCheckTimes(String open_id);
}
