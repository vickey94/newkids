// pages/login/login.js
const app = getApp()
const config = require('../../config.js')
const util = require('../../utils/util.js')

Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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

  bingGetUserInfo: function (e) {
    console.log(e.detail);

    wx.showLoading({
      title: '登录中！',
      mask: true,
    })

    wx.login({
      success: res => {
      //  console.log(config.service.loginUrl)
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        
        wx.request({
          url: config.service.loginUrl, // 仅为示例，并非真实的接口地址
          data: {
            code: res.code,
            rawData: e.detail.rawData,//用户非敏感信息                  
            signature: e.detail.signature,//签名                  
            encryptedData: e.detail.encryptedData,//用户敏感信息                  
            iv: e.detail.iv,//解密算法的向量
          },
          method: 'GET',
          success(res) {
         //   console.log(res.data)
            if(res.data.status == 1){
              wx.setStorageSync('userInfo', res.data.userInfo );
              app.globalData.userInfo = res.data.userInfo ;

              wx.reLaunch({
                url: '../index/index',
              });
            }
          },
          fail(err) {
            console.log(err)
            wx.showModal({
              title: 'fail',
              content: '登录失败，可能是网络错误或服务器错误',
              success(res) {
              }
            })
          },
          complete() {
            wx.hideLoading();
          }
        })
      }
    })
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