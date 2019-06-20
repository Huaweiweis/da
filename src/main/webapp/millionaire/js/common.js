// var baseurl = "http://39.98.219.33:8015/millionaire_game";
var baseurl = "http://localhost:8080";
var basetime = 600000;
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) {
		return unescape(r[2]);
	}
	return null;

}