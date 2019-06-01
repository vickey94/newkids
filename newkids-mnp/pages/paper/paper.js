// pages/paper/paper.js
const config = require('../../config.js');
const util = require('../../utils/util.js');
const func = require('../../utils/func.js')
const d = getApp().globalData;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    navH:0,
    p_id : -1,

    userInfo :null,

    paper:null,

    p_score:0,

    r_start_time:0,
    
    rLogs:null,

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (e) {
    let that = this;

  
    console.log(e.p_id)
    let p_id = e.p_id;
   
    if(p_id == undefined){
      wx.reLaunch({
        url: '../index/index',
      })
    }

    let userInfo = d.userInfo || null;

    that.setData({
      p_id: p_id,
      userInfo: userInfo,
      r_start_time: new Date(),
      navH:d.navH,
    })

  if(userInfo!=null){
    wx.request({
      url: config.service.getreadinglogsUrl,
      data: {
        open_id : d.userInfo.openId,
        p_id: p_id
      },
      method: 'GET',
      success(res) {
        let rLogs = res.data.res;
      //  console.log(rLogs)
        if(rLogs.length > 0){
          that.setData({
            rLogs: rLogs[0],
            p_score : rLogs[0].r_score,
          })
        }
       }
    })
  }

    wx.request({
      url: config.service.getOnePaperUrl,
      data: {
        p_id: p_id
      },
      method: 'GET',
      success(res) {

        let paper = res.data.paper;
        paper.p_update_time_f = util.formatTime(new Date(paper.p_update_time));

        that.setData({
          paper: paper
        })
      },
    })
 

  },
  
  //用户打分完成阅读
  setScore:function(e){
    if (this.data.p_score > 0) return;

    let that = this;
    let p_score = e.currentTarget.dataset.value;
   that.setData({
     p_score: p_score
   })

   let rl  = {};
   rl.p_id = that.data.p_id;
   rl.open_id = d.userInfo.openId;
   rl.r_score = p_score;
   rl.r_start_time = that.data.r_start_time;
   rl.r_end_time = new Date();

   that.setData({
     rLogs:rl
   })

    wx.request({
     url: config.service.finishPaperUrl,
     data:{
       rlJson : rl,
       p_id : that.data.p_id,
       p_score: p_score,
     },
     method:'GET',
     success(res){
      // console.log(res)
     }
   })

    d.today.today_reading.push(that.data.paper);
 
    wx.setStorage({
      key: 'today_reading',
      data: d.today.today_reading,
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

    return {
      title:  "Reading | newKids",
      path: 'pages/paper/paper?p_id=' + this.data.p_id ,
    }
  },
  
  touchStart: function (e) {
    if (d.userInfo == "") return;
    func.touchStart(e)
  },
  touchEnd: function (e) {
    if (d.userInfo == "") return;
    let that = this;
   
    let moveY = 200 / d.pDrp;
    let moveX = 100 / d.pDrp;

 

    //如果滑动长度大于pTop,则 up,down,left,right,
    let act = func.touchEnd(e, moveX, moveY, 1, 10)
    //  console.log(act)

    if (act == "right") {
       wx.navigateBack({
         delta: 1,
       })
    }

  
  },
})