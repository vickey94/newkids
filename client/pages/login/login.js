// pages/login/login.js
const app = getApp()
const config = require('../../config.js')
const util = require('../../utils/util.js')

Page({

  /**
   * 页面的初始数据
   */
  data: {
   // hasUserInfo: false,
   wbs:null,
   wb_id:null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.clearStorageSync();
    //初始化设置一些数据
    wx.setStorageSync('groupNum', 10);
    //初始化背单词模块
    wx.setStorageSync('words', []);
    wx.setStorageSync('hasStudyWords', []);
    wx.setStorageSync('leftWords', []);

    //授权页面，提示用户选择默认的单词书
    var that = this;
    wx.request({
      url: config.service.getAllWordbookUrl,
      data: {},
      method: 'GET',
      success(res) {
        //console.log(res.data.res);
        var wbs = res.data.res;
        that.setData({
          wbs: wbs,
        })
      if(wbs.length > 0){
        that.setData({
          wb_id:wbs[0].wb_id
        })
      }
      },
      fail(err) {
        console.log(err)
        wx.showModal({
          title: 'fail',
          content: '访问服务器失败，可能是网络错误或服务器错误',
        })
      },
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

  bingGetUserInfo: function (e) {
    var that = this;
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
          url: config.service.loginUrl, 
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
            if (res.data.status == 1) {
              wx.setStorageSync('userInfo', res.data.userInfo);
              app.globalData.userInfo = res.data.userInfo;

              //获取用户信息以后，向后台设置wordbook
              var wb_id = that.data.wb_id;
              var open_id = app.globalData.userInfo.openId;

              wx.request({
                url: config.service.updateWordbookUrl,
                data:{
                  open_id:open_id,
                  wb_id : wb_id,
                },
                method:'GET',
                success(res){
                  
                  console.log(res.data);
                  wx.reLaunch({
                    url: '../test/test',
                  });
                  
                },fail(err){
                  console.log(err)
                  wx.showModal({
                    title: 'fail',
                    content: '访问失败，网络错误或服务器错误',
                    success(res) {
                    }
                  })
                }
              })

          
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