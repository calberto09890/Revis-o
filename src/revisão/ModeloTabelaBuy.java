/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package revisão;

import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author calbe
 */
public class ModeloTabelaBuy extends AbstractTableModel{
    
    private Vector<Produto> buyCart;
    public CompraGUI panel;
    
    public ModeloTabelaBuy(CompraGUI panel){
        this.buyCart = new Vector<>();
        this.panel = panel;
    }
    

    @Override
    public int getRowCount() {
        return buyCart.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        
        Produto tempo = buyCart.get(linha);
        
        switch(coluna){
            case 0: return tempo.getNome();
            case 1: return tempo.getPreco();
            case 2: return tempo.getQuantidade();
            case 3: return tempo.getQuantidade() * tempo.getPreco();
            default: return null;
        }
        
    }
    
    public void addProd(Produto sold) {
        this.buyCart.add(sold);
    }
    
    public void removeProd(int indice) {
        this.buyCart.remove(indice);
    }
    
    @Override
    public String getColumnName(int coluna){
        switch(coluna){
            case 0: return "Nome";
            case 1: return "Preço Unit.";
            case 2: return "Quantidade";
            case 3: return "Preço Parc";
            default: return null;
        }   
    }
    
    @Override
    public boolean isCellEditable (int linha, int coluna){
        if(coluna == 2){
            return true;
        }
        else {
            return false; 
        }
    }
    
    @Override
    public void setValueAt(Object newValue, int linha, int coluna){
        if(coluna == 2){
            
            //validação por senha do gerente
            String senha = JOptionPane.showInputDialog(null,
                    "Dê a senha do gerente:",
                    "Operação restrita",
                    JOptionPane.INFORMATION_MESSAGE
            );
            if(senha != null && senha.equals("12345")){

                buyCart.get(linha).setQuantidade((int)newValue);

                //tabela e valor da compra são atualizados com base na nova quantidade passada
                this.panel.f5();
            }
        }
    }
    
    @Override
    public Class<?> getColumnClass(int coluna){
        switch(coluna){
            case 0: return String.class;
            case 1: return Double.class;
            case 2: return Integer.class;
            case 3: return Double.class;
            default: return null;
        }
    } 
    
    public double calculaPrecoParcialC(){
        double valor = 0.0;
        //cálculo dos novos valores que entrarão na tabela
        for (Produto p : buyCart){
            valor += p.getQuantidade() * p.getPreco();
        }
        return valor;
    }

    public Vector<Produto> getBuyCart() {
        return buyCart;
    }
    
    public void resetCart(){
        this.buyCart.clear();
    }
}   
    
