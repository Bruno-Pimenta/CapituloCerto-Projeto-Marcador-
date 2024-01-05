/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Autenticacao.AutenticarUsuario;
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
    public static void insereMarcador(){
        Scanner sc = new Scanner(System.in);
        MarcadorLivrosRepository marcadorLivrosRepository = MarcadorLivrosRepository.pegaInstancia();
        String nome, status, anotacoes, autores, categorias;
        int numeroDePaginas = 0;
        int paginaAtual = 0;
        System.out.println("Digite o nome do livro que será marcado:");
        nome = sc.nextLine();
        while(numeroDePaginas<=0){
            System.out.println("Digite o número de páginas do livro que será marcado:");
            numeroDePaginas = sc.nextInt();
            if(numeroDePaginas<=0){
                System.out.println("O valor deve ser maior do que 0");
            }
        }
        
        while(paginaAtual<=0){
            System.out.println("Digite a página que está sendo lida do livro atualmente (digite 0 se não iniciou a leitura):");
            paginaAtual = sc.nextInt();
            if(paginaAtual<0){
                System.out.println("O valor deve ser maior ou igual a 0");
            }
        }
        
        if(paginaAtual==0){
            status = "Leitura não iniciada.";
        }
        else if(paginaAtual/numeroDePaginas<1){
            status = "Leitura em andamento";
        }
        else{
            status = "Leitura concluída";
        }
        
        float nota=0;
        System.out.println("Insira uma avaliação do livro (0 a 10)");
        nota = sc.nextFloat();
        while(nota<=0){
            System.out.println("Insira uma avaliação do livro (0 a 10)");
            nota=sc.nextFloat();
            if(nota<0||nota>10){
                System.out.println("O valor deve ser entr 0 a 10");
            }
        }
        
               
        System.out.println("Digite uma anotação/observação sobre a obra que seja pertinente (pode deixar vazio):");
        sc.nextLine();
        anotacoes = sc.nextLine();
        
        System.out.println("Digite os nomes dos autores (separadas por vírgula) envolvidos na obra:");
        autores = sc.nextLine();
        
        System.out.println("Digite as categorias (separadas por vírgula) inerentes a obra:");
        categorias = sc.nextLine();
        
        MarcadorLivros marcadorLivro = new MarcadorLivros(nome, numeroDePaginas,paginaAtual, status, nota, anotacoes, autores, categorias);
        marcadorLivrosRepository.insereMarcadorLivros(marcadorLivro);
    }
    
    public static void buscaId() throws SQLException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o identificador do marcador que deseja ser buscado:");
        int id = sc.nextInt();
        MarcadorLivrosRepository marcadorLivrosRepository = MarcadorLivrosRepository.pegaInstancia();
        MarcadorLivros marcador = marcadorLivrosRepository.buscarPorId(id);
        System.out.println(marcador);
    }
    
    public static void buscarTodos() throws SQLException{
        MarcadorLivrosRepository marcadorLivrosRepository = MarcadorLivrosRepository.pegaInstancia();
        List<MarcadorLivros> listaMarcadorLista = marcadorLivrosRepository.buscarTodos();
            for(MarcadorLivros aux: listaMarcadorLista){
                System.out.println(aux);
            }
    }
    
    public static void atualizarPaginaMarcada() throws SQLException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o identificador do marcador que deseja ter a página marcada atualizada:");
        int idMarcador = sc.nextInt();
        int paginaAtual = 0;
        while(paginaAtual<=0){
            System.out.println("Informe o número da página em que você se encontra na leitura atual:");
            paginaAtual = sc.nextInt();
            if(paginaAtual<=0){
                System.out.println("Página inválida (número da página deve ser maior que 0).");
            }
        }
        MarcadorLivrosRepository marcadorLivrosRepository = MarcadorLivrosRepository.pegaInstancia();
        marcadorLivrosRepository.atualizarPagina(idMarcador, paginaAtual);
    }
    
    public static void excluirMarcador() throws SQLException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o identificador do marcador que deseja ser excluído:");
        int idMarcador = sc.nextInt();
        MarcadorLivrosRepository marcadorLivrosRepository = MarcadorLivrosRepository.pegaInstancia();
        marcadorLivrosRepository.excluir(idMarcador);
    }
    
    public static void buscarCategoria() throws SQLException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite as categorias/tags do livro (separadas por vírgula):");
        String categoria = sc.nextLine();
        MarcadorLivrosRepository marcadorLivrosRepository = MarcadorLivrosRepository.pegaInstancia();
        List<MarcadorLivros> listaMarcadorLista = marcadorLivrosRepository.buscaPorTags(categoria);
            for(MarcadorLivros aux: listaMarcadorLista){
                System.out.println(aux);
            }
    }
    
    public static void atualizarAvaliacaoObra() throws SQLException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o identificador do marcador que deseja atualizar a avaliação:");
        int idMarcador = sc.nextInt();
        float nota = 0;
        while(nota<=0){
            System.out.println("Digite a sua avaliação sobre o livro (entre 0 e 10):");
            nota = sc.nextFloat();
            if(nota<=0){
                System.out.println("Página inválida (número da página deve ser maior que 0).");
            }
        }
        MarcadorLivrosRepository marcadorLivrosRepository = MarcadorLivrosRepository.pegaInstancia();
        marcadorLivrosRepository.atualizarAvaliacao(idMarcador, nota);
    }
    
    public static void atualizarAnotacaoObra() throws SQLException{
        Scanner sc = new Scanner(System.in);
        MarcadorLivrosRepository marcadorLivrosRepository = MarcadorLivrosRepository.pegaInstancia();
        System.out.println("Digite o identificador do marcador que deseja atualizar a anotação:");
        int idMarcador = sc.nextInt();
        MarcadorLivros marcadorLivro = marcadorLivrosRepository.buscarPorId(idMarcador);
        if(marcadorLivro!=null){
            System.out.println("Anotação atual:\n"+marcadorLivro.getAnotacoes());
            System.out.println("Faça sua nova observação referênte a obra:");
            sc.nextLine();
            String anotacao = sc.nextLine();
            marcadorLivrosRepository.atualizarAnotacao(idMarcador, anotacao);
        }
        else{
            System.out.println("Não foi encontrado o marcador referente ao id informado:\n");
        }
    }
    
    public static void buscarPorAutor() throws SQLException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome do autor para buscar os marcadores associados a ele:");
        String autor = sc.nextLine();
        MarcadorLivrosRepository marcadorLivrosRepository = MarcadorLivrosRepository.pegaInstancia();
        List<MarcadorLivros> listaMarcadorLista = marcadorLivrosRepository.buscaPorAutor(autor);
            for(MarcadorLivros aux: listaMarcadorLista){
                System.out.println(aux);
            }
        if(listaMarcadorLista.isEmpty()){
            System.out.println("Nenhum registro encontrado.");
        }    
    }
    
    public static void logout(){
        AutenticarUsuario.resetUserId();
    }
    
}
