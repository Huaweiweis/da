package com.zcf.utils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zcf.common.json.Body;
import com.zcf.controller.api.ServiceInitialize;
import com.zcf.pojo.Redenvelopes;
import com.zcf.pojo.Room;
import com.zcf.pojo.Userroom;
import com.zcf.pojo.Users;
import com.zcf.service.RedenvelopesService;
import com.zcf.service.RoomService;
import com.zcf.service.UserroomService;
import com.zcf.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息推送
 * 
 * @author chenkang
 *
 */

@Slf4j
@Component
@ServerEndpoint("/websocket/{roominfo}")
public class WebSocket {
	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;
	// 用来存放每个客户端对应的MyWebSocket对象。可以使用Map来存放(遍历的用set)，其中Key可以为用户标识
	private static Map<String, WebSocket> clients = new ConcurrentHashMap<>();
	// private List<WebSocket> webSocketSet = new ArrayList<WebSocket>();
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	private String Key;

	private RoomService roomService = (RoomService) ServiceInitialize.getApplicationContext().getBean("roomService");
	private UserroomService userroomService = (UserroomService) ServiceInitialize.getApplicationContext().getBean("userroomService");
	private UsersService usersService = (UsersService) ServiceInitialize.getApplicationContext().getBean("usersService");
	private RedenvelopesService redenvelopesservice = (RedenvelopesService) ServiceInitialize.getApplicationContext().getBean("redenvelopesservice");


	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(@PathParam("roominfo") String roominfo, Session session) {
		this.session = session;
		System.err.println("{"+roominfo+"}");
		roominfo="{"+roominfo+"}";
		clients.put( roominfo, this);
		Key = roominfo;
		JSONObject jsonObject=JSONObject.fromObject(roominfo);

		Body sfjqrroom = roomService.sfjqrroom(jsonObject.getInt("roomid"));
		int code = sfjqrroom.getMeta().getCode();
//		
		Body entrancerooms = roomService.entrancerooms(jsonObject.getInt("roomid"),jsonObject.getInt("userid"),jsonObject.getString("password"));
		addOnlineCount();
		log.info("有新连接加入！当前在线人数为" + getOnlineCount() + "加入的管理员ID为:" + roominfo+"房间信息"+entrancerooms);
		List<Userroom> selectList = userroomService.selectList(new EntityWrapper<Userroom>().eq("room_id", jsonObject.get("roomid")));

			for (int i = 0; i < selectList.size(); i++) {
				Users user = usersService.selectById(selectList.get(i).getUserId());
				Map<String,Object> map = new HashMap<>();
				map.put("content",JsonUtils.objectToJson(entrancerooms)); 
				map.put("action", "reloadCount");
				System.out.println(buildKey(user.getUserId(), jsonObject.getString("roomid"), jsonObject.getString("password")));
				send2User(JsonUtils.objectToJson(map), buildKey(user.getUserId(), jsonObject.getString("roomid"), jsonObject.getString("password")));
			}
		}
//	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		System.out.println(String.format("%s离开房间", Key));
		JSONObject jsonObject=JSONObject.fromObject(Key);
		Room quituser = roomService.quituser(jsonObject.getInt("userid"),jsonObject.getInt("roomid"));
		System.out.println(quituser+"离开用户");

		List<Userroom> selectList = userroomService.selectList(new EntityWrapper<Userroom>().eq("room_id", jsonObject.getInt("roomid")));
		System.out.println(selectList.toString());
		for (int i = 0; i < selectList.size(); i++) {
			Users selectById = usersService.selectById(selectList.get(i).getUserId());
			Map<String,Object> map = new HashMap<>();
			map.put("content", selectById);
			map.put("action", "quit");// 退出
			send2User(JsonUtils.objectToJson(map), buildKey(selectById.getUserId(), jsonObject.getString("roomid"), jsonObject.getString("password")));
		}
		
		clients.remove(Key); // 从set中删除
		subOnlineCount(); // 在线数减1
		log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message 客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message) {//addredenvelopes
		log.info("控制器收到消息：{}",message);
		//"{senduser:{userid:1,roomid:1,password：123},content:{money：5,count：5},action:publish}"
		JSONObject jsonObject=JSONObject.fromObject(message);
		if(jsonObject.get("action").toString().equals("publish")) {
			JSONObject senduser=JSONObject.fromObject(jsonObject.get("senduser").toString());
			JSONObject content=JSONObject.fromObject(jsonObject.get("content").toString());
			Redenvelopes addredenvelopes = redenvelopesservice.addredenvelopes(senduser.getInt("userid"), content.getDouble("money"), content.getInt("count"), senduser.getInt("roomid"));
			//通知房间所有人
			List<Userroom> selectList = userroomService.selectList(new EntityWrapper<Userroom>().eq("room_id", senduser.getInt("roomid")));
			for (int i = 0; i < selectList.size(); i++) {
				Users user = usersService.selectById(selectList.get(i).getUserId());
				System.out.println("要发送的用户信息"+buildKey(senduser.getInt("userid"),senduser.getString("roomid"),senduser.getString("password")));
				send2User(buildcontent(addredenvelopes,senduser.toString()),buildKey(user.getUserId(),senduser.getString("roomid"),senduser.getString("password")));
			}
		}
		//{senduser：{userid:1,roomid:1,roomid:23}，action：qianbao}
		if(jsonObject.get("action").toString().equals("qianbao")) {
			log.info("控制器收到消息：{}",message);
			JSONObject senduser=JSONObject.fromObject(jsonObject.get("senduser").toString());
			JSONObject content=JSONObject.fromObject(jsonObject.get("content").toString());
			Body addredenvelopes = redenvelopesservice.adduserredenvelopes(senduser.getInt("userid"),content.getInt("redenvelopesid"));
			//通知房间所有人
			List<Userroom> selectList = userroomService.selectList(new EntityWrapper<Userroom>().eq("room_id", senduser.getInt("roomid")));
			for (int i = 0; i < selectList.size(); i++) {
				Users user = usersService.selectById(selectList.get(i).getUserId());
				send2User(buildcontents(addredenvelopes,senduser.toString()),buildKey(user.getUserId(),senduser.getString("roomid"),senduser.getString("password")));
				log.info("发送"+buildcontents(addredenvelopes,senduser.toString()), buildKey(user.getUserId(),senduser.getString("roomid"),senduser.getString("password")));
			}
			JSONObject json = JSONObject.fromObject(addredenvelopes.getData());
			System.out.println(json.toString().length());
			if(json.toString().length()==4) {
				System.out.println("进去了");
			}else{
				Integer productid=json.getInt("geshu");//商品id
				if(productid==0) {
					redenvelopesservice.getsuccess(content.getInt("redenvelopesid"),senduser.getInt("roomid"));
					Body usersuccess = redenvelopesservice.usersuccess(content.getInt("redenvelopesid"));
					List<Userroom> selectLists = userroomService.selectList(new EntityWrapper<Userroom>().eq("room_id", senduser.getInt("roomid")));
					for (int i = 0; i < selectLists.size(); i++) {
						Users users = usersService.selectById(selectLists.get(i).getUserId());
						send2User(buildcontentss(addredenvelopes,senduser.toString()),buildKey(users.getUserId(),senduser.getString("roomid"),senduser.getString("password")));
						send2User(getsuccess(usersuccess,senduser.toString()),buildKey(users.getUserId(),senduser.getString("roomid"),senduser.getString("password")));
						log.info("发送"+buildcontentss(addredenvelopes,senduser.toString()), buildKey(users.getUserId(),senduser.getString("roomid"),senduser.getString("password")));
					}
				}
			}
		}
		if(jsonObject.get("action").toString().equals("chaibao")) {
			
			
			log.info("控制器收到消息：{}","进来+===========================================");
			JSONObject senduser=JSONObject.fromObject(jsonObject.get("senduser").toString());
			JSONObject content=JSONObject.fromObject(jsonObject.get("content").toString());
			
			Body addredenvelopes = redenvelopesservice.getunclaimed(content.getInt("redenvelopesid"));
			System.out.println("+_+++++=+++++++++++++======="+addredenvelopes.getMeta().getCode());
			int code = addredenvelopes.getMeta().getCode();
			
			if(code == 502) {
				System.out.println("第二次进 +_+++++=+++++++++++++=======");
				
			}else {
				System.out.println("第一次 +_+++++=+++++++++++++======="+code);
				if(addredenvelopes.getMeta().getCode()==200) {
					
					JSONObject fromObject = JSONObject.fromObject(addredenvelopes);
					System.out.println(fromObject);
					redenvelopesservice.getsuccess(content.getInt("redenvelopesid"),senduser.getInt("roomid"));
					Body usersuccess = redenvelopesservice.usersuccess(content.getInt("redenvelopesid"));
					
					//通知房间所有人
					List<Userroom> selectLists = userroomService.selectList(new EntityWrapper<Userroom>().eq("room_id", senduser.getInt("roomid")));
					for (int i = 0; i < selectLists.size(); i++) {
						Users users = usersService.selectById(selectLists.get(i).getUserId());
						send2User(buildcontentss(addredenvelopes,senduser.toString()),buildKey(users.getUserId(),senduser.getString("roomid"),senduser.getString("password")));
						send2User(getsuccess(usersuccess,senduser.toString()),buildKey(users.getUserId(),senduser.getString("roomid"),senduser.getString("password")));
						log.info("发送"+buildcontentss(addredenvelopes,senduser.toString()), buildKey(users.getUserId(),senduser.getString("roomid"),senduser.getString("password")));
					}
				}
			}
		}
		//{senduser：{userid:1,roomid:1,roomid:23}，action：jiesan}
		if(jsonObject.get("action").toString().equals("jiesan")) {
			JSONObject senduser=JSONObject.fromObject(jsonObject.get("senduser").toString());
			Body jiesan = roomService.jiesan(senduser.getInt("userid"),senduser.getInt("roomid"));
			//通知房间所有人
			List<Userroom> selectList = userroomService.selectList(new EntityWrapper<Userroom>().eq("room_id", senduser.getInt("roomid")));
			for (int i = 0; i < selectList.size(); i++) {
				Users user = usersService.selectById(selectList.get(i).getUserId());
				send2User(buildcontentsss(jiesan,senduser.toString()),buildKey(user.getUserId(),senduser.getString("roomid"),senduser.getString("password")));
				log.info("发送"+buildcontentsss(jiesan,senduser.toString()), buildKey(user.getUserId(),senduser.getString("roomid"),senduser.getString("password")));
			}
			UserroomService userroomService12 = (UserroomService) ContextLoader.getCurrentWebApplicationContext().getBean("userroomService");
			List<Userroom> selectList12 = userroomService12.selectList(new EntityWrapper<Userroom>().eq("room_id",senduser.getInt("roomid")));
			if(!selectList12.isEmpty()) {
				for (int i = 0; i < selectList12.size(); i++) {
					userroomService12.deleteById(selectList12.get(i).getUserRoomId());
				}
			}
		}
		//{senduser:{userid:1,roomid:1,password：123},content:{money：5,count：5},action:publish}"
		if(jsonObject.get("action").toString().equals("zhuanrang")) {
			JSONObject senduser=JSONObject.fromObject(jsonObject.get("senduser").toString());
			JSONObject content=JSONObject.fromObject(jsonObject.get("content").toString());
			Body zhuanrang = roomService.zhuanrang(senduser.getInt("userid"),senduser.getInt("roomid"),content.getInt("yonghuid"));
			
			List<Userroom> selectList = userroomService.selectList(new EntityWrapper<Userroom>().eq("room_id", senduser.getInt("roomid")));
			for (int i = 0; i < selectList.size(); i++) {
				Users user = usersService.selectById(selectList.get(i).getUserId());
				send2User(buildcontentssss(zhuanrang,senduser.toString()),buildKey(user.getUserId(),senduser.getString("roomid"),senduser.getString("password")));
				log.info("发送"+buildcontentssss(zhuanrang,senduser.toString()), buildKey(user.getUserId(),senduser.getString("roomid"),senduser.getString("password")));
			}
		}
		//{senduser:{userid:1,roomid:1,password：123},content:{money：5,count：5},action:publish}
		if(jsonObject.get("action").toString().equals("faxiaoxi")) {
			JSONObject senduser=JSONObject.fromObject(jsonObject.get("senduser").toString());
			JSONObject content=JSONObject.fromObject(jsonObject.get("content").toString());
			
			Users users = usersService.selectById(content.getInt("yonghuid"));
			send2User(faxiaoxi(senduser.toString(), content.getInt("yonghuid")),buildKey(users.getUserId(),senduser.getString("roomid"),senduser.getString("password")));
		}
		//{senduser:{userid:1,roomid:1,password：123},action:jiesan}
		if(jsonObject.get("action").toString().equals("jisan")) {
			JSONObject senduser=JSONObject.fromObject(jsonObject.get("senduser").toString());
			
			List<Userroom> selectList = userroomService.selectList(new EntityWrapper<Userroom>().eq("room_id", senduser.getInt("roomid")));
			for (int i = 0; i < selectList.size(); i++) {
				Users user = usersService.selectById(selectList.get(i).getUserId());
				send2User(jiesan(senduser.toString()),buildKey(user.getUserId(),senduser.getString("roomid"),senduser.getString("password")));
				log.info("发送"+jiesan(senduser.toString()), buildKey(user.getUserId(),senduser.getString("roomid"),senduser.getString("password")));
			}
		}
	}

	/**
	 * 解散房间
	 * @param senduser
	 * @return
	 */
	private String jiesan(String senduser) {
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("bonus", "房间解散");
		map.put("senduser", senduser);
		map.put("action", "faxiaoxi");
		return JsonUtils.objectToJson(map); 
	}

	/**
	 * 发送消息
	 * @param senduser
	 * @param content
	 * @return
	 */
	private String faxiaoxi(String senduser,Integer content) {
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("bonus", "是否愿意成为成为房主");
		map.put("senduser", senduser);
		map.put("content", content);
		map.put("action", "faxiaoxi");
		return JsonUtils.objectToJson(map); 
	}


	private String buildcontentssss(Body zhuanrang, String senduser) {
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("bonus", zhuanrang);
		map.put("senduser", senduser);
		map.put("action", "zhuanrang");
		return JsonUtils.objectToJson(map); 
	}
	
	private String buildcontentsss(Body addredenvelopes, String senduser) {
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("bonus", addredenvelopes);
		map.put("senduser", senduser);
		map.put("action", "jiesan");
		return JsonUtils.objectToJson(map); 
	}
	
	private String getsuccess(Body usersuccess, String senduser) {
		Map<String,Object> map = new HashMap<>();

		map.put("bonus", usersuccess);
		map.put("senduser", senduser);
		map.put("action", "getsuccess");
		return JsonUtils.objectToJson(map); 
	}
	
	private String buildcontentss(Body addredenvelopes, String senduser) {
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("bonus", addredenvelopes);
		map.put("senduser", senduser);
		map.put("action", "chaibao");
		return JsonUtils.objectToJson(map); 
	}
	private String buildcontents(Body addredenvelopes, String senduser) {
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("bonus", addredenvelopes);
		map.put("senduser", senduser);
		map.put("action", "qianbao");
		return JsonUtils.objectToJson(map); 
	}

	private String buildcontent(Redenvelopes packg,String senduser) {
			Map<String,Object> map = new LinkedHashMap<>();
			map.put("packg", packg);
			map.put("senduser", senduser);
			map.put("action", "publish");
			return JsonUtils.objectToJson(map);
	}

	
	private String buildKey(Integer userid,String roomid,String pwd) {
			Map<String,Object> map = new LinkedHashMap<>();
			map.put("userid", userid);
			map.put("roomid", roomid);
			map.put("password", pwd);
			return JsonUtils.objectToJson(map);
	}
	
	
	/**
	 * 给指定的人发送消息
	 * 
	 * @param message
	 */
	private void send2User(String message, String key) {
		try {
			if (clients.get(key) != null) {
				clients.get(key).sendMessage(message);
			} else {
				log.info("当前用户不在线");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	/**
	 * 发送给所有
	 * 
	 * @param message
	 */
	public void sendMessageAll(String message) {
		for (WebSocket item : clients.values()) {
			item.session.getAsyncRemote().sendText(message);
		}
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebSocket.onlineCount--;
	}
}
