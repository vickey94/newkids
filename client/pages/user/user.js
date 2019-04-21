// pages/user/user.js
const app = getApp()
const config = require('../../config.js')
const util = require('../../utils/util.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo : null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.showLoading({
     
      mask: true,
    })
    var loginurl = '../login/login';

    wx.getSetting({
      success: (res) => {
        // console.log(res.authSetting);
        if (res.authSetting['scope.userInfo']) {
          console.log('用户已授权')

          wx.checkSession({
            success() {
              console.log('session_key 未过期')
              // session_key 未过期，并且在本生命周期一直有效
              if (!util.isLogged(app)) {
                wx.hideLoading();
                console.log('未登录');
                wx.reLaunch({
                  url: loginurl,
                })
              } else {
                console.log(app.globalData.userInfo)
                wx.hideLoading();
              }
            },
            fail() {
              console.log('session_key 已经失效，需要重新执行登录流程')
              // session_key 已经失效，需要重新执行登录流程
              wx.reLaunch({
                url: loginurl,
              })
            }
          })

        } else {
          console.log('用户未授权')
          wx.reLaunch({
            url: loginurl,
          })
        }
      }
    })

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})