/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package revisão;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author calbe
 */
public class FakeBD {
    
    private static Vector<Produto> produtos;
    //método que abre e lê os dados da tabela do excel
    private static void loadArquivo(){
        
        //correção na criação do vetor que conterá os produtos
        if(produtos == null){
            produtos = new Vector<>();
        }else{
            produtos.clear();
        }
        
        File fileCsv =  new File("C:\\Users\\calbe\\Desktop\\Projeto Saulo\\Revisão\\produtos.csv");
        //daqui em diante toda a codificação é voltada para a leitura do arquivo
        try{
           FileReader readMark = new FileReader(fileCsv); 
           
           //leitura por linha
           BufferedReader bufRead = new BufferedReader(readMark);
           //leitura da primeira linha(que é descartada).
           bufRead.readLine();
           String line = bufRead.readLine();
           
           while(line != null){
               //leitura de todas as linhas depois da primeira
               
               String info[] = line.split(";");
               int code = Integer.parseInt(info[0]);
               String nome = info[1];
               double preco = Double.parseDouble(info[2]);
               int quant = Integer.parseInt(info[3]);
               
               //adição dos produtos ao vetor dinâmico
               produtos.add(new Produto(code,nome,preco,quant));
               
               //comando que pede a leitura da segunda e todas as outras linhas após a primeira
               line = bufRead.readLine();  
           }
           
           //liberação do arquivo para outros possíveis processos
           bufRead.close();
           
        }catch(FileNotFoundException e){
            System.err.println("O arquivo especificado não existe ou não foi encontrado");
        }catch(IOException f){
            System.err.println("Arquivo Corrompido");
        }
        
        
    }
    //méotodo que faz a busca de um produto pelo seu código de barras
    public static Produto searchCode(int code){
        //verificação se o arquivo foi carregado
        if(produtos == null){
            loadArquivo();
        }
        
        //busca o produto através do código dado pelo usuário
        for(Produto prodA : produtos){
            if(prodA.getCodigo() == code){
                return prodA;
            }
        // caso o código buscado não coincida com nenhum código de nenhum produto, ele returna nulo pois não existe um produto com aquele código.    
        }
        return null;
    }
    
    public static void updateFile() {
        
        File file = new  File("C:\\Users\\calbe\\Desktop\\Projeto Saulo\\Revisão\\produtos.csv");
        
        try {
            FileWriter escriba = new FileWriter(file);
            
            BufferedWriter bufEscriba = new BufferedWriter(escriba);
            
            for(int i = 0; i < produtos.size(); i++){
                bufEscriba.write(produtos.get(i).toString()+"\n");
            }
            
            bufEscriba.flush();
            bufEscriba.close();
            
        } catch (IOException ex) {
            System.err.println("Falha no dispositivo.");
        }
        
    }
}
