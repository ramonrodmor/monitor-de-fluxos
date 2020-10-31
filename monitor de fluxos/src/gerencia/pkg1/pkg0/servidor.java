/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerencia.pkg1.pkg0;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import gerencia.pkg1.pkg0.tela;
import gerencia.pkg1.pkg0.Fluxo;
import gerencia.pkg1.pkg0.ProdutoTableModel;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class servidor {

    Boolean delete = false;
    HttpServer server;

    public servidor() {
        try {
            server = HttpServer.create(new InetSocketAddress(8086), 0);
            server.createContext("/flows/http", new MyHandlerHttp());
            server.createContext("/flows/ftp", new MyHandlerFtp());
            server.createContext("/flows/smtp", new MyHandlerSmtp());
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Conexão encerrada...");
            server.stop(0);
        }
    }

    public class MyHandlerHttp implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            
            int id = -1;
            String idStr1;
            String tipo = "";

            URI uri = t.getRequestURI();
            String path = uri.getPath();
            System.out.println(">>>> " + path);
            try {
                String idStr = path.substring(path.lastIndexOf('p') + 2, path.lastIndexOf('/'));
                id = Integer.parseInt(idStr);
                idStr1 = path.substring(path.lastIndexOf('/') + 1);
                tipo = idStr1;
                System.out.println("Lendo a id: " + Integer.toString(id));
            } catch (Exception e) {
                System.out.println(">>>> O fluxo não possui nenhum detalhe");
            }

            if (t.getRequestMethod().equalsIgnoreCase("POST")) {
                try {
                    if (tipo.equalsIgnoreCase("data")) {
                        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
                        BufferedReader br = new BufferedReader(isr);
                        String query = br.readLine();
                        //System.out.println(query);
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(query);
                            //String aux = obj.getJSONObject("pref").getString("pref");
                            //System.out.println("aux: " + aux);
                            String fluxo = obj.toString();
                            String detalhe = obj.getString("url");
                            // Acrescenta os detalhes ao respectivo fluxo
                            for (int i = 0; i < ProdutoTableModel.getFluxCount(); i++) {
                                if (ProdutoTableModel.flux.get(i).getId() == id) {
                                    ProdutoTableModel.flux.get(i).setDetail1(detalhe);
                                    System.out.println("Detalhe do fluxo " + ProdutoTableModel.flux.get(i).getId() + ": " + ProdutoTableModel.flux.get(i).getDetail1());
                                    System.out.println("Alterou o detalhe do fluxo http> detalhe: " + detalhe);
                                }
                            }

                        } catch (JSONException ex) {
                            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        // send response
                        String response = "response: ";
                        t.sendResponseHeaders(200, response.length());
                        OutputStream os = t.getResponseBody();
                        os.write(response.toString().getBytes());
                        os.close();

                    } else {
                        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
                        BufferedReader br = new BufferedReader(isr);
                        String query = br.readLine();
                        //System.out.println(query);
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(query);
                            //String aux = obj.getJSONObject("pref").getString("pref");
                            //System.out.println("aux: " + aux);
                            String fluxo = obj.toString();

                            Fluxo f = new Fluxo("http", "start", obj.getString("ip_src"), obj.getInt("port_src"), obj.getString("ip_dst"), obj.getInt("port_dst"), Integer.parseInt(obj.getString("id")), " ", " ");
                            tela.inserirFluxo(f);
                            System.out.println("Recebeu um fluxo http> ip: " + fluxo);
                        } catch (JSONException ex) {
                            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        // send response
                        String response = "response: ";
                        t.sendResponseHeaders(200, response.length());
                        OutputStream os = t.getResponseBody();
                        os.write(response.toString().getBytes());
                        os.close();
                    }
                } catch (NumberFormatException | IOException e) {
                }
            } else if (t.getRequestMethod().equalsIgnoreCase("DELETE")) {

                try {
                    InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String query = br.readLine();
                    //System.out.println(query);
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(query);
                        //String aux = obj.getJSONObject("pref").getString("pref");
                        //System.out.println("aux: " + aux);
                        String detalhe = "";
                        for (int i = 0; i < ProdutoTableModel.getFluxCount(); i++) {
                            if (ProdutoTableModel.flux.get(i).getId() == obj.getInt("id")) {
                                detalhe = ProdutoTableModel.flux.get(i).getDetail1();
                                System.out.println("Detalhe do fluxo " + ProdutoTableModel.flux.get(i).getId() + ": " + ProdutoTableModel.flux.get(i).getDetail1());
                                System.out.println("Alterou o detalhe do fluxo http> detalhe: " + detalhe);
                            }
                        }
                        Fluxo f = new Fluxo("http", "end", obj.getString("ip_src"), obj.getInt("port_src"), obj.getString("ip_dst"), obj.getInt("port_dst"), Integer.parseInt(obj.getString("id")), detalhe, "");
                        tela.inserirFluxo(f);
                        
                    } catch (JSONException ex) {
                        Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // send response
                    String response = "response: ";
                    t.sendResponseHeaders(200, response.length());
                    OutputStream os = t.getResponseBody();
                    os.write(response.toString().getBytes());
                    os.close();
                } catch (NumberFormatException | IOException e) {
                }
            } else {
                String response = "This is the response " + t.getRequestMethod() + t.getResponseCode();
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    public class MyHandlerFtp implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {

            int id = -1;
            String idStr1;
            String tipo = "";

            URI uri = t.getRequestURI();
            String path = uri.getPath();
            System.out.println(">>>> " + path);
            try {
                String idStr = path.substring(path.lastIndexOf('p') + 2, path.lastIndexOf('/'));
                id = Integer.parseInt(idStr);
                idStr1 = path.substring(path.lastIndexOf('/') + 1);
                tipo = idStr1;
                System.out.println("Lendo a id: " + Integer.toString(id));
            } catch (Exception e) {
                System.out.println(">>>> O fluxo não possui nenhum detalhe");
            }

            if (t.getRequestMethod().equalsIgnoreCase("POST")) {
                try {
                    if (tipo.equalsIgnoreCase("data")) {
                        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
                        BufferedReader br = new BufferedReader(isr);
                        String query = br.readLine();
                        //System.out.println(query);
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(query);
                            //String aux = obj.getJSONObject("pref").getString("pref");
                            //System.out.println("aux: " + aux);
                            String fluxo = obj.toString();
                            String detalhe1 = obj.getString("command");
                            String detalhe2 = obj.getString("file");
                            // Acrescenta os detalhes ao respectivo fluxo
                            for (int i = 0; i < ProdutoTableModel.getFluxCount(); i++) {
                                if (ProdutoTableModel.flux.get(i).getId() == id) {
                                    ProdutoTableModel.flux.get(i).setDetail1(detalhe1);
                                    ProdutoTableModel.flux.get(i).setDetail2(detalhe2);
                                    System.out.println("Detalhe do fluxo " + ProdutoTableModel.flux.get(i).getId() + ": " + ProdutoTableModel.flux.get(i).getDetail1() + "; " + ProdutoTableModel.flux.get(i).getDetail2());
                                    System.out.println("Alterou o detalhe do fluxo http> detalhe: " + detalhe1 + "; " + detalhe2);
                                }
                            }

                        } catch (JSONException ex) {
                            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        // send response
                        String response = "response: ";
                        t.sendResponseHeaders(200, response.length());
                        OutputStream os = t.getResponseBody();
                        os.write(response.toString().getBytes());
                        os.close();
                    } else {
                        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
                        BufferedReader br = new BufferedReader(isr);
                        String query = br.readLine();
                        //System.out.println(query);
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(query);
                            //String aux = obj.getJSONObject("pref").getString("pref");
                            //System.out.println("aux: " + aux);
                            String fluxo = obj.toString();
                            Fluxo f = new Fluxo("ftp", "start", obj.getString("ip_src"), obj.getInt("port_src"), obj.getString("ip_dst"), obj.getInt("port_dst"), Integer.parseInt(obj.getString("id")), " ", " ");
                            tela.inserirFluxo(f);
                            System.out.println("Recebeu um fluxo ftp : " + fluxo);
                        } catch (JSONException ex) {
                            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        // send response
                        String response = "response: ";
                        t.sendResponseHeaders(200, response.length());
                        OutputStream os = t.getResponseBody();
                        os.write(response.toString().getBytes());
                        os.close();
                    }
                } catch (NumberFormatException | IOException e) {
                }
            } else if (t.getRequestMethod().equalsIgnoreCase("DELETE")) {

                try {
                    InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String query = br.readLine();
                    //System.out.println(query);
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(query);
                        //String aux = obj.getJSONObject("pref").getString("pref");
                        //System.out.println("aux: " + aux);
                        String detalhe1 = "";
                        String detalhe2 = "";
                        for (int i = 0; i < ProdutoTableModel.getFluxCount(); i++) {
                            if (ProdutoTableModel.flux.get(i).getId() == obj.getInt("id")) {
                                detalhe1 = ProdutoTableModel.flux.get(i).getDetail1();
                                detalhe2 = ProdutoTableModel.flux.get(i).getDetail2();
                                System.out.println("Detalhe do fluxo " + ProdutoTableModel.flux.get(i).getId() + ": " + ProdutoTableModel.flux.get(i).getDetail1() + "; " + ProdutoTableModel.flux.get(i).getDetail2());
                                System.out.println("Alterou o detalhe do fluxo http> detalhe: " + detalhe1 + "; " + detalhe2);
                            }
                        }
                        Fluxo f = new Fluxo("ftp", "end", obj.getString("ip_src"), obj.getInt("port_src"), obj.getString("ip_dst"), obj.getInt("port_dst"), Integer.parseInt(obj.getString("id")), detalhe1, detalhe2);
                        tela.inserirFluxo(f);
                    } catch (JSONException ex) {
                        Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // send response
                    String response = "response: ";
                    t.sendResponseHeaders(200, response.length());
                    OutputStream os = t.getResponseBody();
                    os.write(response.toString().getBytes());
                    os.close();
                } catch (NumberFormatException | IOException e) {
                }
            } else {
                String response = "This is the response " + t.getRequestMethod() + t.getResponseCode();
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    public class MyHandlerSmtp implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            
            int id = -1;
            String idStr1;
            String tipo = "";

            URI uri = t.getRequestURI();
            String path = uri.getPath();
            System.out.println(">>>> " + path);
            try {
                String idStr = path.substring(path.lastIndexOf('p') + 2, path.lastIndexOf('/'));
                id = Integer.parseInt(idStr);
                idStr1 = path.substring(path.lastIndexOf('/') + 1);
                tipo = idStr1;
                System.out.println("Lendo a id: " + Integer.toString(id));
            } catch (Exception e) {
                System.out.println(">>>> O fluxo não possui nenhum detalhe");
            }

            if (t.getRequestMethod().equalsIgnoreCase("POST")) {
                try {
                    if (tipo.equalsIgnoreCase("data")) {
                        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
                        BufferedReader br = new BufferedReader(isr);
                        String query = br.readLine();
                        //System.out.println(query);
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(query);
                            //String aux = obj.getJSONObject("pref").getString("pref");
                            //System.out.println("aux: " + aux);
                            String fluxo = obj.toString();
                            String detalhe1 = obj.getString("sender");
                            String detalhe2 = obj.getString("recipient");
                            // Acrescenta os detalhes ao respectivo fluxo
                            for (int i = 0; i < ProdutoTableModel.getFluxCount(); i++) {
                                if (ProdutoTableModel.flux.get(i).getId() == id) {
                                    ProdutoTableModel.flux.get(i).setDetail1(detalhe1);
                                    ProdutoTableModel.flux.get(i).setDetail2(detalhe2);
                                    System.out.println("Detalhe do fluxo " + ProdutoTableModel.flux.get(i).getId() + ": " + ProdutoTableModel.flux.get(i).getDetail1() + "; " + ProdutoTableModel.flux.get(i).getDetail2());
                                    System.out.println("Alterou o detalhe do fluxo http> detalhe: " + detalhe1 + "; " + detalhe2);
                                }
                            }

                        } catch (JSONException ex) {
                            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        // send response
                        String response = "response: ";
                        t.sendResponseHeaders(200, response.length());
                        OutputStream os = t.getResponseBody();
                        os.write(response.toString().getBytes());
                        os.close();

                    } else {
                        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
                        BufferedReader br = new BufferedReader(isr);
                        String query = br.readLine();
                        //System.out.println(query);
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(query);
                            //String aux = obj.getJSONObject("pref").getString("pref");
                            //System.out.println("aux: " + aux);
                            Fluxo f = new Fluxo("smtp", "start", obj.getString("ip_src"), obj.getInt("port_src"), obj.getString("ip_dst"), obj.getInt("port_dst"), Integer.parseInt(obj.getString("id")), " ", " ");
                            tela.inserirFluxo(f);
                            delete = true;
                        } catch (JSONException ex) {
                            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        // send response
                        String response = "response: ";
                        t.sendResponseHeaders(200, response.length());
                        OutputStream os = t.getResponseBody();
                        os.write(response.toString().getBytes());
                        os.close();
                    }
                } catch (NumberFormatException | IOException e) {
                }
            } else if ((t.getRequestMethod().equalsIgnoreCase("DELETE")) && (delete)) {
                delete = false;
                try {
                    InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String query = br.readLine();
                    //System.out.println(query);
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(query);
                        //String aux = obj.getJSONObject("pref").getString("pref");
                        //System.out.println("aux: " + aux);
                        String detalhe1 = "";
                        String detalhe2 = "";
                        for (int i = 0; i < ProdutoTableModel.getFluxCount(); i++) {
                            if (ProdutoTableModel.flux.get(i).getId() == obj.getInt("id")) {
                                detalhe1 = ProdutoTableModel.flux.get(i).getDetail1();
                                detalhe2 = ProdutoTableModel.flux.get(i).getDetail2();
                                System.out.println("Detalhe do fluxo " + ProdutoTableModel.flux.get(i).getId() + ": " + ProdutoTableModel.flux.get(i).getDetail1() + "; " + ProdutoTableModel.flux.get(i).getDetail2());
                                System.out.println("Alterou o detalhe do fluxo http> detalhe: " + detalhe1 + "; " + detalhe2);
                            }
                        }                        
                        Fluxo f = new Fluxo("smtp", "end", obj.getString("ip_src"), obj.getInt("port_src"), obj.getString("ip_dst"), obj.getInt("port_dst"), Integer.parseInt(obj.getString("id")), detalhe1, detalhe2);
                        tela.inserirFluxo(f);
                    } catch (JSONException ex) {
                        Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // send response
                    String response = "response: ";
                    t.sendResponseHeaders(200, response.length());
                    OutputStream os = t.getResponseBody();
                    os.write(response.toString().getBytes());
                    os.close();
                } catch (NumberFormatException | IOException e) {
                }
            } else {
                String response = "This is the response " + t.getRequestMethod() + t.getResponseCode();
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }
}
