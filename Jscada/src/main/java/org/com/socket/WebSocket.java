package org.com.socket;


import org.com.utils.HexString;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;

public class WebSocket  {
    /**
     * 发送的字符串
     */
    private   String str;
    /**
     * ip
     */
    private   String ip;
    /**
     * 端口
     */
    private   int  port ;

    /**读取报文的长度*/
    private  int cd;
    Socket socket = new Socket();
    public  WebSocket(String str,String ip,int port,int cd)  {
        this.str=str;
        this.ip=ip;
        this.port=port;
        this.cd=cd;
    }


    public HashMap<String, String> call() {
        HashMap<String, String> map=new HashMap<String, String>(8);
        try {
            //创建一个流套接字并将其连接到指定主机上的指定端口号
            socket.connect(new InetSocketAddress(ip, port), 1000);
            //向服务器端发送数据
            OutputStream os = socket.getOutputStream();
            // 向服务器端发送一条消息
            byte[] bytes = HexString.hexStringToByteArray(str);
            os.write(bytes);
            //读取服务器端数据
            // 读Sock里面的数据
            // 封装输入流（接收客户端的流）
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            DataInputStream dis = new DataInputStream(bis);
            byte[] bs = new byte[cd];
            while (dis.read(bs) != -1) {
               String ret = HexString.bytesToHexString(bs);
                if (dis.available() == 0) {
                    ret=ret.replaceAll(" ","").trim();
                    map.put(ip,ret);
//                    System.out.format("ip是 %s ,发送的数据是 %s ,返回的数据是 %s,采集时间是 %tT",ip,str, ret,new Date());
                    break;
                }
            }
            os.close();
            bis.close();
            dis.close();
        } catch(Exception e){
            System.out.println("主机异常:" + e.getMessage());
            map.put(ip,"");
        } finally{
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    socket = null;
                    System.out.println("主机 finally 异常:" + e.getMessage());
                }
            }
        }
        return map;
    }

}
