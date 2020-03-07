package tda.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread{
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private Socket socket;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            String html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Java Server</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <h1>java server from scratch</h1>\n" +
                    "</body>\n" +
                    "</html>";

            final String CLRF = "\n\r";
            String response = "HTTP/1.1 200 OK" + CLRF +
                    "Content-Length: " + html.getBytes().length + CLRF + CLRF + html + CLRF + CLRF;
            outputStream.write(response.getBytes());


//            try{
//                sleep(5000);
//            }catch(Exception e){
//                e.printStackTrace();
//            }


        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("Error in inputstream",e);
                }
            }
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    LOGGER.error("Error in outptstream",e);
                }
            }

            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    LOGGER.error("Error in socket",e);
                }
            }


        }
    }
}
