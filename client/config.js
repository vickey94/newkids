var host = 'http://localhost:8081';
var config = {
  service: {
    host,
    // 登录地址，用于建立会话
    loginUrl: `${host}/login/valid`,
    }
}

module.exports = config;