    var webSocket;

    function send_msg(msg) {
        if (webSocket != null) {
            webSocket.send(JSON.stringify(msg));
        } else {
            alert("您已掉线...");
        }
    };
 
    function closeWs() {
        webSocket.close();
    };
    initWebSocket();
    function initWebSocket() {
        var workerId = localStorage.getItem("workerId");
        if(null!=workerId){
            if ("WebSocket" in window) {
                if (webSocket == null) {
                	//var url = "ws://101.132.125.248:6046/yike/websocket/"+workerId;
                    var url = "ws://localhost:8080/yike/websocket/"+workerId;
                    webSocket = new WebSocket(url);
                } else{
                	console.log("建立连接")
                }
                webSocket.onopen = function () {
                	console.log("建立连接")
                };
                webSocket.onmessage = function (evt) {
              
                };
                webSocket.onclose = function () {
                	//关闭的时候向后太发送错误码 关闭这个
                	console.log("连接关闭")
                };
            }
            else {
                // 浏览器不支持 WebSocket
                alert("您的浏览器不支持 WebSocket!");
            }        	
        }else{
        	console.log("未登录websocket连接失败");
        }
        
        

    }