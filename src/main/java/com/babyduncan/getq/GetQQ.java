package com.babyduncan.getq;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.protocol.*;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Guohao
 * Date: 11/15/13
 * Time: 10:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetQQ {

    private static final Logger logger = Logger.getLogger(GetQQ.class);

    public static void main(String... args) {
        // get qq from guest of http://meishi.qq.com/profiles/737191831
        // first get all the html
        HttpProcessor httpproc = HttpProcessorBuilder.create()
                .add(new RequestContent())
                .add(new RequestTargetHost())
                .add(new RequestConnControl())
                .add(new RequestUserAgent("Test/1.1"))
                .add(new RequestExpectContinue(true)).build();

        HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

        HttpCoreContext coreContext = HttpCoreContext.create();
        HttpHost host = new HttpHost("meishi.qq.com", 80);
        coreContext.setTargetHost(host);

        DefaultBHttpClientConnection conn = new DefaultBHttpClientConnection(8 * 1024);
        ConnectionReuseStrategy connStrategy = DefaultConnectionReuseStrategy.INSTANCE;

        try {

            String[] targets = {
                    "/profiles/1916960731"};

            List<String> qqList = new ArrayList<String>();

            while (true) {
                // dead loop worker
                try {
                    for (int i = 0; i < targets.length; i++) {
                        if (!conn.isOpen()) {
                            Socket socket = new Socket(host.getHostName(), host.getPort());
                            socket.setSoTimeout(3000);
                            conn.bind(socket);
                        }
                        BasicHttpRequest request = new BasicHttpRequest("GET", targets[i]);
                        httpexecutor.preProcess(request, httpproc, coreContext);
                        HttpResponse response = httpexecutor.execute(request, conn, coreContext);
                        httpexecutor.postProcess(response, httpproc, coreContext);
                        String result = EntityUtils.toString(response.getEntity());
                        while (result.indexOf("\"/profiles/") > 0) {
                            result = result.substring(result.indexOf("/profiles/"));
                            String tempqq = result.substring(10, result.indexOf("\"><"));
                            if (!qqList.contains(tempqq)) {
                                System.out.println("--------------> get one qq " + result.substring(10, result.indexOf("\"><")));
                                logger.info(result.substring(10, result.indexOf("\"><")));
                                qqList.add(tempqq);
                                if(qqList.size()>10000){
                                    qqList.remove(567);
                                }
                            }
                            result = result.substring(result.indexOf("\"><"));
                        }
                        if (!connStrategy.keepAlive(response, coreContext)) {
                            conn.close();
                        } else {
                            System.out.println("Connection kept alive...");
                        }
                    }
                } catch (Exception e) {
                    Thread.sleep(3000);
                    continue;
                }
                System.out.println("loop once ..");
                Thread.sleep(1000);
                //dead loop worker
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
