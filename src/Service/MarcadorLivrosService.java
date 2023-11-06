/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.MarcadorLivros;
import Repository.MarcadorLivrosRepository;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Bruno
 */
public class MarcadorLivrosService {
    public void cadastrarMarcadorDeLivros(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Insira o nome do livro");
        String nome = sc.nextLine();
        System.out.println("Insira o número de páginas do livro");
        int numeroDePaginas = sc.nextInt();
        if(numeroDePaginas<=0){
            System.out.println("O número de páginas inserido é inválido");
            return;
        }
        System.out.println("Insira a página do que está lendo (digite 0 caso ainda não iniciou a leitura)");
        int paginaAtual = sc.nextInt();
        if(paginaAtual<0){
            System.out.println("O número número inserido é inválido");
            return;
        }
        String status = "";
        float nota = 0;
        if(numeroDePaginas==paginaAtual){
            status = "Concluído";
            System.out.println("De 0.00 até 10, dê uma nota para o livro");
            nota = sc.nextFloat();
            sc.next();
            if(nota<0.00||nota>10.0){
                System.out.println("O Valor inserido não está dentro do intervalo definido");
                return;
            }
        }
        else if(paginaAtual<numeroDePaginas){
            status = "Em progresso";
        }
        else{
            status = "Leitura não iniciada";
        }
        
        System.out.println("Insira alguma informação pertinente para a leitura");
        sc.nextLine();
        String anotacoes = sc.nextLine();
        
        System.out.println("Separadas por vírgula, insira as categorias/tags do livro");
        String categorias =sc.nextLine();
        
        System.out.println("Insira o nome dos autores");
        String autores =sc.nextLine();

        MarcadorLivros marcadorLivros = new MarcadorLivros(nome, numeroDePaginas, paginaAtual, status, nota,
        anotacoes, autores, categorias);
        
        MarcadorLivrosRepository mLR = new MarcadorLivrosRepository(); 
        mLR.insereMarcadorLivros(marcadorLivros);
        

    }
    
    public List<MarcadorLivros> buscaPorTags() throws Exception{
        Scanner sc = new Scanner(System.in);
        System.out.println("Separadas por vírgula, insira as categorias/tags que o livro que você buscar");
        String categorias = sc.nextLine();
        sc.close();
        MarcadorLivrosRepository mLR = new MarcadorLivrosRepository(); 
        List<MarcadorLivros> lista = mLR.buscaPorTags(categorias);
        return lista;
    }
    
    public List<MarcadorLivros> buscarTodos() throws Exception{
        MarcadorLivrosRepository mLR = new MarcadorLivrosRepository(); 
        List<MarcadorLivros> lista = mLR.buscarTodos();
        return lista;
    }
    
    public void excluirMarcadorLivro(int id) throws SQLException{
        MarcadorLivrosRepository mLR = new MarcadorLivrosRepository(); 
        mLR.excluir(id);
    }
    
    public MarcadorLivros buscarPorId(int id) throws Exception{
        MarcadorLivrosRepository mLR = new MarcadorLivrosRepository(); 
        return mLR.buscarPorId(id);
    }
    
    public void atualizarPaginaLivro() throws Exception{
        
        int id, paginaAtual;
        String status;
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Informe o id do livro que terá atualização na página marcada");
        id = sc.nextInt();
        System.out.println("Informe a página do livro que você está");
        paginaAtual = sc.nextInt();
        sc.close();
        
        MarcadorLivros marcadorLivro = buscarPorId(id);
        if(marcadorLivro!=null && marcadorLivro.getNumeroDePaginas()>paginaAtual && paginaAtual>0){
            MarcadorLivrosRepository mLR = new MarcadorLivrosRepository(); 
            mLR.atualizarPagina(id, paginaAtual,"Leitura em progresso" );
        }
        else if(marcadorLivro!=null && marcadorLivro.getNumeroDePaginas()==paginaAtual){
            MarcadorLivrosRepository mLR = new MarcadorLivrosRepository(); 
            mLR.atualizarPagina(id, paginaAtual,"Concluído" );
        }
        else{
            System.out.println("Não foi possível atualizar a página");
        }
        
    }
    
}
