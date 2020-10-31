/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerencia.pkg1.pkg0;

/**
 *
 * @author ramon
 */
public class Fluxo {
    
    private String COL_PROT;
    private String COL_MET;
    private String COL_IP_SRC;
    private int COL_PORT_SRC;
    private String COL_IP_DST;
    private int COL_PORT_DST;
    private int id;
    private String detail1;
    private String detail2;
    

    public Fluxo() {
    }

    public Fluxo(String COL_PROT, String COL_MET, String COL_IP_SRC, int COL_PORT_SRC, String COL_IP_DST, int COL_PORT_DST, int id, String detail1, String detail2) {
        this.COL_PROT = COL_PROT;
        this.COL_MET = COL_MET;
        this.COL_IP_SRC = COL_IP_SRC;
        this.COL_PORT_SRC = COL_PORT_SRC;
        this.COL_IP_DST = COL_IP_DST;
        this.COL_PORT_DST = COL_PORT_DST;
        this.id = id;
        this.detail1 = detail1;
        this.detail2 = detail2;
        
    }
    
    public static String detalhesFluxo(Fluxo flux) {
        if (!flux.getDetail1().equalsIgnoreCase(" ")) {
            if (flux.getCOL_PROT().equalsIgnoreCase("http")) {
                return ("URL: " + flux.getDetail1());
            } else if (flux.getCOL_PROT().equalsIgnoreCase("smtp")) {
                return ("Sender: " + flux.getDetail1() + "\n" + "Recipient: " + flux.getDetail2());
            } else if (flux.getCOL_PROT().equalsIgnoreCase("ftp")) {
                return ("Command: " + flux.getDetail1()  + "\n" + "File name: " + flux.getDetail2());
            }
        } else {
            return ("The flow has no details.");
        }
        return ("Error");
    }
    
    public String getCOL_PROT() {
        return COL_PROT;
    }

    public void setCOL_PROT(String COL_PROT) {
        this.COL_PROT = COL_PROT;
    }

    public String getCOL_IP_SRC() {
        return COL_IP_SRC;
    }

    public void setCOL_IP_SRC(String COL_IP_SRC) {
        this.COL_IP_SRC = COL_IP_SRC;
    }

    public int getCOL_PORT_SRC() {
        return COL_PORT_SRC;
    }

    public void setCOL_PORT_SRC(int COL_PORT_SRC) {
        this.COL_PORT_SRC = COL_PORT_SRC;
    }

    public String getCOL_IP_DST() {
        return COL_IP_DST;
    }

    public void setCOL_IP_DST(String COL_IP_DST) {
        this.COL_IP_DST = COL_IP_DST;
    }

    public int getCOL_PORT_DST() {
        return COL_PORT_DST;
    }

    public void setCOL_PORT_DST(int COL_PORT_DST) {
        this.COL_PORT_DST = COL_PORT_DST;
    }

    /**
     * @return the COL_MET
     */
    public String getCOL_MET() {
        return COL_MET;
    }

    /**
     * @param COL_MET the COL_MET to set
     */
    public void setCOL_MET(String COL_MET) {
        this.COL_MET = COL_MET;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the detail1
     */
    public String getDetail1() {
        return detail1;
    }

    /**
     * @param detail1 the detail1 to set
     */
    public void setDetail1(String detail1) {
        this.detail1 = detail1;
    }

    /**
     * @return the detail2
     */
    public String getDetail2() {
        return detail2;
    }

    /**
     * @param detail2 the detail2 to set
     */
    public void setDetail2(String detail2) {
        this.detail2 = detail2;
    }
    
}

