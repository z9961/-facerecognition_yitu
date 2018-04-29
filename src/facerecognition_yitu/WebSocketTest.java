package facerecognition_yitu;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/WebSocketTest")
public class WebSocketTest {
	private static List<Session> sessions = new ArrayList<Session>();

	@OnOpen
	public void open(Session session) {
		sessions.add(session);
	}

	public void broadcast(List<Session> sessions, String msg) {
		for (Iterator iter = sessions.iterator(); iter.hasNext();) {
			Session session = (Session) iter.next();
			try {
				session.getBasicRemote().sendText(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/*@OnMessage
	public void messgae(Session session, String msg) {
		System.out.println("!!");
		System.out.println("消息内容:" + msg.length());

	}*/

	@OnMessage
	public void binaryMessage(Session session, ByteBuffer msg) {
		System.out.println("Binary message: " + msg.toString());
	}

	@OnMessage
	public void pongMessage(Session session, PongMessage msg) {
		System.out.println("Pong message: " + msg.getApplicationData().toString());
	}

	@OnClose
	public void close(Session session) {

	}

}
