package facerecognition_yitu;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;



public class Test {  
	  
    public static void main(String[] args) throws DeploymentException, IOException, InterruptedException {  
        WebSocketContainer ws = ContainerProvider.getWebSocketContainer();  
        String url = "ws://ws://127.0.0.1:8080/facerecognition_yitu/websocket";  
       MyClient client = new MyClient();  
        Session session =  ws.connectToServer(client, URI.create(url));   
        int turn = 0;  
        while(turn++ < 10){  
            session.getBasicRemote().sendText("client send: " + turn);  
            Thread.sleep(1000);  
        }  
        new CountDownLatch(1).await();  
         
   }  
} 
