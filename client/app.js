//app.js
const util = require('utils/util.js')
var config = require('config.js')

App({
  onLaunch: function () {

    wx.showLoading({ mask:true})

   //未登录
    if (!util.isLogged(this)){
      wx.hideLoading();
      console.log('未登录');
      wx.reLaunch({
        url: 'pages/login/login',
      })
    }else{
      console.log(this.globalData.userInfo)
      wx.hideLoading();
    }
  
  },
  globalData: {
    userInfo: null
  }
})