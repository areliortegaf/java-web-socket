/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket.ws;

import fi.iki.elonen.NanoHTTPD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Frost
 */
public class WebServer extends NanoHTTPD {

    public WebServer() throws IOException {
        super(8082);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    protected String getIndex() {
        StringBuilder builder = new StringBuilder();

        InputStream in = getClass().getResourceAsStream("/index.html");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while (null != (line = reader.readLine())) {
                builder.append(line).append("\n");
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }

        return builder.toString();
    }

    @Override
    public Response serve(IHTTPSession session) {
        return newFixedLengthResponse(getIndex());
    }

    public static void main(String... args) {
        try {
            WebServer webServer = new WebServer();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

}
