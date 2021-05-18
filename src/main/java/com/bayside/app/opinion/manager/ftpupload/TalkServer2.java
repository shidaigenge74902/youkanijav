package com.bayside.app.opinion.manager.ftpupload;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TalkServer2 {
    public static void main(String[] args) {
        ServerSocket server=null;
        Socket you=null;
        String s=null;
        DataOutputStream out=null;
        DataInputStream in=null;
        try {
            server=new ServerSocket(4700);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            System.out.println("等待客户呼叫");
            you=server.accept();
            out=new DataOutputStream(you.getOutputStream());
            in=new DataInputStream(you.getInputStream());
            while(true){
                s=in.readUTF();
                int m=Integer.parseInt(s);
                out.writeUTF("你好,我是服务器");
                out.writeUTF("你说的数乘2后是:"+2*m);
                System.out.println("服务器收到:"+s);
                Thread.sleep(500);
            }
        } catch (Exception e) {
            System.out.println("客户端已断开"+e);
        }
    }
}