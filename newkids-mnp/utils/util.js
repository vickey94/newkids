var Parser = require('../lib/dom-parser.js')

const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

 /* return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')*/
  return [year, month, day].map(formatNumber).join('/')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

const xml2word = xml => {
 // console.log(xml)
  var word = {};
  var XMLParser = new Parser.DOMParser();
  var doc = XMLParser.parseFromString(xml)
  var key = doc.getElementsByTagName('key')['0'];
  var sents = doc.getElementsByTagName('sent');

  var temp = [];
  for (var i = 0; i < sents.$$length; i++) {
    var sent = {};
    var ss = sents[i];
    ss.index = i;
    sent.orig = ss.firstChild.firstChild.nodeValue;
    sent.trans = ss.lastChild.firstChild.nodeValue;

    temp[i] = sent;
  }
  return temp;
}

module.exports = {
  formatTime,
  xml2word,
}
