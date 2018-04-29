package facerecognition_yitu;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;


	@ClientEndpoint  
	public class MyClient {  
	    @OnOpen    
	    public void onOpen(Session session) {    
	       System.out.println("I was accpeted by her!");  
	    }    
	     
	    @OnClose    
	    public void onClose() {  
	        System.out.println("client close!");  
	    }    
	     
	    @OnMessage    
	    public void onMessage(String message, Session session) {   
	        System.out.println("客户端收到消息: " + message);   
	    }    
	     
	   
	    @OnError    
	    public void onError(Session session, Throwable error) {    
	         System.out.println("客户端发生错误");  
	        error.printStackTrace();    
	    
	}  
}
