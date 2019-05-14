// pages/test/test.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

p1:function(){
wx.navigateTo({
  url: '../index/index',
})
},
  p2: function () {
    wx.navigateTo({
      url: '../study/study',
    })
  },
  p3: function () {
    wx.navigateTo({
      url: '../word/word?word=take&id=-1',
    })
  },
  p4: function () {
    wx.navigateTo({
      url: '../checkword/checkword',
    })
  },
  p5: function () {
    wx.navigateTo({
      url: '../paper/paper',
    })
  },
  p6: function () {
    wx.navigateTo({
      url: '../checkpaper/checkpaper',
    })
  },
  p7: function () {
    wx.navigateTo({
      url: '../login/login',
    })
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