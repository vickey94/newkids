// pages/paper/paper.js
const app = getApp()
const config = require('../../config.js')
const util = require('../../utils/util.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    p_id:null,
    paper: null,
    userInfo:null,
    hasRead:true,
    p_score: 0,

    r_score:0,
    r_start_time:null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(e) {
    var that = this;
    var p_id = e.p_id;
    console.log(p_id );
    that.setData({
      p_id: p_id 
    })
    wx.request({
      url: config.service.getOnePaperUrl,
      data: {
        p_id: p_id 
      },
      method: 'GET',
      success(res) {
        console.log(res.data.paper);
        that.setData({
          paper: res.data.paper,
        })
      },
    })

    if (e.hasRead == "false"){
      var userInfo = wx.getStorageSync("userInfo");
      that.setData({
        hasRead : false,
        userInfo : userInfo,
      })
    }
  },

  checkPaper :function(){
    var that = this; 
    var d =that.data;

    var p_score = d.p_score;

    var readingLogs = {};
    
    readingLogs.open_id = d.userInfo.openId;
    readingLogs.p_id = d.paper.p_id;
    readingLogs.r_score = d.r_score;
    readingLogs.r_start_time = d.r_start_time;
    readingLogs.r_end_time = Date.parse(new Date());
    console.log(JSON.stringify(readingLogs))
    wx.request({
      url: config.service.finishPaperUrl,
      data: {
        rlJson: JSON.stringify(readingLogs),
        p_id: readingLogs.p_id,
        p_score: p_score,
      },
      method: 'GET',
      success(res) {
        var reading_his = wx.getStorageSync("reading_his");
        reading_his.push(readingLogs.p_id);
        wx.setStorageSync("reading_his", reading_his);

       that.setData({
         hasRead:true
       })
      },
      complete(){ }
    }) 
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    var p_id = this.data.p_id ;
    var reading_his = wx.getStorageSync("reading_his");
    console.log(reading_his)
    for (var i = 0; i < reading_his.length;i++){ 
      if (reading_his[i] == p_id ){
        this.setData({
          hasRead:true,
        })
        break;
      }
    }
    this.setData({
      r_start_time: Date.parse(new Date()),
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {
    var paper = this.data.paper;

    return {
      title: " Reading | newKids",
      path: '../paper/paper?p_id=' + paper.p_id + '&hasRead=' +true,
    } 
  }
})