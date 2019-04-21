const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}


/*登录验证流程
先检查，app.globalData.userInfo ,如果不存在，则
然后检查Storage里的登录状态
返回false时，页面开始调用登录服务，同时存储到Storage中
*/
const isLogged = app => {
  if (app.globalData.userInfo != null) return true;
  
  var userInfo = wx.getStorageSync('userInfo');
  if (userInfo != null && userInfo != '' && userInfo != undefined) {
    app.globalData.userInfo = userInfo;
    return true;
  } else
    return false;
}


module.exports = {
  formatTime,
  isLogged,
}