/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket.ws;
import java.net.InetSocketAddress;
import java.util.Collection;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Frost
 */
public class SocketServer extends WebSocketServer {
    
    private float latitude;
    private float longitude;
    public String robar;
    public String madres;
    
    

    public SocketServer(int port) {
        super(new InetSocketAddress(port));
        
        
    }

    @Override
    public void onOpen(WebSocket ws, ClientHandshake ch) {
        System.out.println("Se inicio el servidor");
    }

    @Override
    public void onClose(WebSocket ws, int code, String reason, boolean remote) {
        System.out.println(ws + " se desconecto del servidor!");
    }

    @Override
    public void onMessage(WebSocket ws, String message) {
        System.out.println("Mensaje: " + message);
        robar = message;
        JSONParser parser = new JSONParser();

        try {
            JSONObject object = (JSONObject) parser.parse(message);

            latitude = Float.valueOf(object.get("latitude").toString());
            longitude = Float.valueOf(object.get("longitude").toString());

            send(latitude, longitude);
        } catch (ParseException exception) {
            ws.close();
        }
    }
    
    //puras tonteras
    public String metodo(){
    madres = robar;
    String cosa = "prueba" + madres;
    return cosa;
}
    @Override
    public void onError(WebSocket ws, Exception exception) {
        System.err.println("Ocurrio un error!");
    }

    protected void send(float latitude, float longitude) {
        JSONObject object = new JSONObject();
        object.put("latitude", latitude);
        object.put("longitude", longitude);
        String json = object.toJSONString();
        
        Collection<WebSocket> connections = connections();
        synchronized (connections) {
            for (WebSocket connection : connections) {
                connection.send(json);
            }
        }
    }
float x = latitude;
float y =longitude;
    public static void main(String... args) {
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebSocketImpl.DEBUG = false;
                int port = 8885;

                SocketServer server = new SocketServer(port);
                server.start();
            }
        }).start();
        
    }

}
