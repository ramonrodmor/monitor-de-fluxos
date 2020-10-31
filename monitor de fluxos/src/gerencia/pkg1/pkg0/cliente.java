/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerencia.pkg1.pkg0; 
import gerencia.pkg1.pkg0.tela;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
 
public class cliente 
{
    int responseCode;
    String responseMsg;
    
    public int POST(String uri) throws IOException
    {
        URL url = new URL(uri);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setRequestMethod("POST");
        httpCon.setDoOutput(true);
        httpCon.setRequestProperty("Content-Type", "application/json");
        StringBuffer str = new StringBuffer();
        String value = null;
        str.append(value);
        OutputStream output = httpCon.getOutputStream();
        output.write(str.toString().getBytes());
        output.flush();
        responseCode=httpCon.getResponseCode();
        responseMsg=httpCon.getResponseMessage();
        System.out.println(responseCode);
        System.out.println(responseMsg);
        if (responseCode==200){
            tela.log.append("Monitor installed.");
        } else if (responseCode==409) {
            tela.log.append("Monitor is already installed.");
        }
        output.close();
        System.out.println("Enviando POST com este url " + url);
        return httpCon.getResponseCode();
    }
     
    public int DELETE(String uri) throws IOException
    {
        URL url = new URL(uri);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setRequestMethod("DELETE");
        httpCon.setDoOutput(true);
        httpCon.setRequestProperty("Content-Type", "application/json");
        StringBuffer str = new StringBuffer();
        String value = null;
        str.append(value);
        OutputStream output = httpCon.getOutputStream();
        output.write(str.toString().getBytes());
        output.flush();
        responseCode=httpCon.getResponseCode();
        responseMsg=httpCon.getResponseMessage();
        System.out.println(responseCode);
        System.out.println(responseMsg);
        if (httpCon.getResponseCode()==200){
            tela.log.append("Monitor uninstalled.");
        } else if (responseCode==404) {
            tela.log.append("The monitor is not installed.");
        }
        output.close();
        return httpCon.getResponseCode();
    }
     
}