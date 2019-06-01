const config = require('../config.js');
const util = require('util.js');
const app = getApp();

let touchDotX = 0; //X按下时坐标
let touchDotY = 0; //y按下时坐标
let interval; //计时器
let time = 0; //从按下到松开共多少时间*100
// 触摸开始事件  
const touchStart = (e) => {

  touchDotX = e.touches[0].pageX;
  // 获取触摸时的原点    
  touchDotY = e.touches[0].pageY;
  // 使用js计时器记录时间        
  interval = setInterval(function() {
    time++;
  }, 49);
}
// 触摸结束事件  ,t为设置的持续时间，单位为100ms
const touchEnd = (e, moveX, moveY, min_t,max_t) => {

  let act = "";

  let touchMoveX = e.changedTouches[0].pageX;
  let touchMoveY = e.changedTouches[0].pageY;
  let tmX = touchMoveX - touchDotX;
  let tmY = touchMoveY - touchDotY;

  if (time > min_t ) {
    let absX = Math.abs(tmX);
    let absY = Math.abs(tmY);

    if (absX > 2 * absY && absX >= moveX) {
      if (tmX < 0) {
        //  console.log("左滑=====")
        act = "left";
      } else {
        // console.log("右滑=====")
        act = "right";
      }
    }
    if (absY > absX * 2 && absY >= moveY) {
      if (tmY < 0) {
        //   console.log("上滑动=====")
        act = "up";
      } else {
        // console.log("下滑动=====")
        act = "down";
      }

    }
  }

  clearInterval(interval);
  // 清除setInterval    
  time = 0;

  return act;
}
const getHiddenAni = () => {
  return wx.createAnimation({
    duration: 0, //动画时长
    timingFunction: 'step-start', //动画的效果 
    delay: 0 //动画延迟时间，单位 ms
  });
}

const getShowAni = (time) => {
  return wx.createAnimation({
    duration: time, //动画时长
    timingFunction: 'ease', //动画的效果 
    delay: 100 //动画延迟时间，单位 ms
  });
}

let start_time = null;
const studyStart = () => {
  start_time = new Date();
}

const studyEnd = () => {
  if (start_time != null)
    return ((new Date()).getTime() - start_time.getTime()) / 1000;
  else return 0
}

const getFromciba  = (word, callback) =>{

  word = word.toLowerCase(); //小写

  //请求单词
  wx.request({
    url: config.service.cibaUrl,
    data: {
      w: word,
     // key: cibakey,
      type: "json"
    },
    method: 'GET',
    success(res) {

      let cibaword = {};
      cibaword = JSON.parse(res.data.res); 

      //请求例句
      wx.request({
        url: config.service.cibaUrl,
        data: {
          w: word,
         // key: cibakey,
          type: "xml"
        },
        method: 'GET',
        success(res) {
   
          let xmlDoc = res.data.res;
        
          let cibaSents = util.xml2word(xmlDoc);
         
          cibaword.sents = cibaSents;
         
          callback && callback(cibaword);
        },
      })
    
    },
  })
}



module.exports = {
  touchStart,
  touchEnd,
  getHiddenAni,
  getShowAni,
  studyStart,
  studyEnd,
  getFromciba,


}