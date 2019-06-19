//var baseurl = "http://47.111.22.176:80/auto_food";
//var baseurl = "http://192.168.28.176:8088/millionaire_game";
var baseurl = "http://39.98.219.33:8015/millionaire_game";
//var baseurl = "http://lujun.free.idcfengye.com/auto_food";
var basetime = 600000;
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) {
		return unescape(r[2]);
	}
	return null;

}