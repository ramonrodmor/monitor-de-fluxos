package gerencia.pkg1.pkg0;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ramon
 */
import gerencia.pkg1.pkg0.Fluxo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ProdutoTableModel extends AbstractTableModel {

    //constantes que vão representar as colunas
    //(só para facilitar o entendimento do código)
    private final int COL_PROT = 0;
    private final int COL_MET = 1;
    private final int COL_IP_SRC = 2;
    private final int COL_PORT_SRC = 3;
    private final int COL_IP_DST = 4;
    private final int COL_PORT_DST = 5;
    //lista dos flux que serão exibidos
    static public List<Fluxo> flux;
    

    public ProdutoTableModel() {
        flux = new ArrayList();
    }

    public ProdutoTableModel(List<Fluxo> lista) {
        this();
        flux.addAll(lista);
    }

    public int getRowCount() {
        //cada produto na lista será uma linha
        return flux.size();
    }
    
    static public int getFluxCount() {
        //cada produto na lista será uma linha
        return flux.size();
    }

    public int getColumnCount() {
        //vamos exibir 7 colunas
        return 6;
    }

    @Override
    public String getColumnName(int column) {
        //qual o nome da coluna
        if (column == COL_PROT) {
            return "PROTOCOL";
        } else if (column == COL_MET) {
            return "FLOW";
        } else if (column == COL_IP_SRC) {
            return "IP SRC";
        } else if (column == COL_PORT_SRC){
            return "PORT SRC";
        } else if (column == COL_IP_DST) {
            return "IP DST";
        } else if (column == COL_PORT_DST){
            return "PORT DST";
        }
        return "";
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        //retorna a classe que representa a coluna
        return String.class;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        //pega o produto da linha
        Fluxo p = flux.get(rowIndex);

        //verifica qual valor deve ser retornado
        if (columnIndex == COL_PROT) {
            return p.getCOL_PROT();
        } else if (columnIndex == COL_MET) {
            return p.getCOL_MET();
        } else if (columnIndex == COL_IP_SRC) {
            return p.getCOL_IP_SRC();
        } else if (columnIndex == COL_PORT_SRC) {
            return p.getCOL_PORT_SRC();
        } else if (columnIndex == COL_IP_DST) {
            return p.getCOL_IP_DST();
        } else if (columnIndex == COL_PORT_DST) {
            return p.getCOL_PORT_DST();
        }
        return "";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //no nosso caso todas vão ser editáveis, entao retorna true pra todas
        return false;
    }
    
    public void inserir(Fluxo p) {
        flux.add(p);
        fireTableDataChanged();
    }
 
    public void excluir(int pos) {
        flux.remove(pos);
        fireTableDataChanged();
    }
 
    public void excluir(Fluxo p) {
        flux.remove(p);
        fireTableDataChanged();
    }
 
    public void ordenarPorNome() {
        //ordena pelo nome
        Collections.sort(flux, new Comparator<Fluxo>() {
 
            public int compare(Fluxo o1, Fluxo o2) {
                return o1.getCOL_PROT().compareTo(o2.getCOL_PROT());
            }
        });
 
        //avisa que a tabela foi alterada
        fireTableDataChanged();
    }
 
    public void ordenarPorQuantidade() {
        //ordena pelo nome
        Collections.sort(flux, new Comparator<Fluxo>() {
 
            public int compare(Fluxo o1, Fluxo o2) {
                return o1.getCOL_IP_SRC().compareTo(o2.getCOL_IP_SRC());
            }
        });
 
        //avisa que a tabela foi alterada
        fireTableDataChanged();
    }
 
    public void misturar() {
        //mistura a lista
        Collections.shuffle(flux);
 
        //avisa que a tabela foi alterada
        fireTableDataChanged();
    }
 
   public Fluxo getCliente(int pos) {
        if (pos >= flux.size()) {
            return null;
        }

        return flux.get(pos);
    }
}
