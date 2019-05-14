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

    wx.showLoading({ mask: true })

    var that = this;

    util.refreshData();

    if (!util.isLogged(that)) {
      wx.hideLoading();
      wx.reLaunch({
        url: 'pages/login/login',
      })
    } else {
      console.log(that.globalData.userInfo)
      wx.hideLoading();
      wx.request({
        url: config.service.visitUrl,
        data: {
          open_id: that.globalData.userInfo.openId
        },
        method: 'GET',
        success(res) {
        },
        fail(err) {
          console.log(err)
          wx.showModal({
            title: 'fail',
            content: '访问服务器失败，可能是网络错误或服务器错误',
          })
        },
      })
    }

  },
  globalData: {
    userInfo: null,
  }
  /**
   * 本地存储包括
   * userInfo: null, 用户信息
   * 
   * words: null,  当前背诵的单词组
   * hasStudyWords: null, 已学习单词
   * leftWords: null, 剩余单词
   */
})