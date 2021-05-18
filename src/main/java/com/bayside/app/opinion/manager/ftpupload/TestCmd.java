package com.bayside.app.opinion.manager.ftpupload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TestCmd {
public static void main(String[] args) {
	Runtime rt=Runtime.getRuntime();
	try {
		Process process = rt.exec("cmd /k start java -jar  C:/crawler-metasearch-1.0-SNAPSHOT.jar");
		/*while(process.isAlive()){
			ByteArrayOutputStream   baos   =   new   ByteArrayOutputStream();
			BufferedReader reader =  new BufferedReader(new InputStreamReader(process.));
			System.out.println(reader.readLine());
		}*/
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public static void main22(String[] args) {
	try {   
        Process process = Runtime.getRuntime().exec("cmd.exe");   
        OutputStream outputStream = process.getOutputStream();  
        ByteArrayOutputStream   baos   =   new   ByteArrayOutputStream();
        final InputStream inputStream = process.getInputStream();   
        new Thread(new Runnable(){   
            byte[] cache = new byte[1024];   
            public void run() {   
                 System.out.println("listener started");   
                try {   
                    while(inputStream.read(cache)!=-1){ 
                    	baos.write(cache);
                        System.out.println(baos.toString());   
                }   
                } catch (IOException e) {   
                    //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.   
                }   
            }   
        }).start();   
         outputStream.write(new byte[]{'d','i','r','\n'});   
        //inputStream.   
        int i = process.waitFor();   
        System.out.println("i=" + i);   
    } catch (Exception e) {   
        e.printStackTrace();   
    }  
}
public static void main456(String[] args) throws IOException {
	 Process process = Runtime.getRuntime().exec("cmd");
	 
	 
}

}
