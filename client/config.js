var host = 'http://localhost:8081';
var config = {
  service: {
    host,
    // 登录地址，用于建立会话
    loginUrl: `${host}/login/valid`,
    //访问
    visitUrl: `${host}/login/visit`,
    }
}

module.exports = config;