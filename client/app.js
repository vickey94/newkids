//app.js
const util = require('utils/util.js')
var config = require('config.js')

App({
  onLaunch: function () {

    /**
     * 登录流程
     * --引导页
     * 获取登录态，如果
     */

    wx.showLoading({ mask:true})

    var that = this;
  
    if (!util.isLogged(that)) {
      wx.hideLoading();
      wx.reLaunch({
        url: 'pages/login/login',
      })
    } else {
      console.log(that.globalData.userInfo)
      wx.hideLoading();
      wx.reLaunch({
        url: 'pages/index/index',
      })
    }


  },
  globalData: {
    userInfo: null
  }
})