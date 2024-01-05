/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Principal;
import Autenticacao.AutenticarUsuario;
import Service.MarcadorLivrosService;
import Service.UsuarioService;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Bruno
 */
public class Menu {
    public static void telaLogin(){
        int selecionado=0;  
        Scanner sc = new Scanner(System.in);
        while(selecionado!=3){
            System.out.println("Selecione o identificador referente a operação desejada:");
            System.out.println("1 - Login\n2 - Cadastrar\n3 - Sair");
            selecionado = sc.nextInt();
            switch(selecionado){
                case 1 -> {
                    try {
                        AutenticarUsuario.realizarLogin();
                        if(AutenticarUsuario.getUserId()>0){
                            telaSelecao();
                        }
                    } catch (SQLException ex) {
                        System.out.println("Não foi possível efetuar o login. " + ex.getMessage());
                    }
                }
                
                case 2 -> UsuarioService.cadastrarUsuario();
                
                case 3 -> System.exit(0);  
                
                default -> System.out.println("Entrada inválida");
            }
        } 
    }
    
    private static void telaSelecao() throws SQLException{
        Scanner sc = new Scanner(System.in);
        int selecionado=0;  
        while(selecionado!=10){
            System.out.println("Selecione o identificador referente a operação desejada:");
            System.out.println("""
                               1 - Adicionar marcador
                               2 - Buscar marcador por id
                               3 - Buscar todos os marcadores 
                               4 - Buscar marcadores por categoria
                               5 - Buscar marcadores por autor
                               6 - Atualizar página marcada
                               7 - Atualizar avaliação da obra
                               8 - Atualizar anotação da obra
                               9 - Excluir marcador
                               10 - Logout""");
            selecionado = sc.nextInt();
                        
            switch(selecionado){
                case 1:
                    MarcadorLivrosService.insereMarcador();
                break;
                
                case 2:
                    MarcadorLivrosService.buscaId();
                break;
                
                case 3:
                    MarcadorLivrosService.buscarTodos();
                break;
                
                case 4:
                    MarcadorLivrosService.buscarCategoria();
                break;    
                
                case 5:
                    MarcadorLivrosService.buscarPorAutor();
                break;
                
                case 6:
                    MarcadorLivrosService.atualizarPaginaMarcada();
                break;    
                
                case 7:
                    MarcadorLivrosService.atualizarAvaliacaoObra();
                break;    
                
                case 8:
                    MarcadorLivrosService.atualizarAnotacaoObra();
                break;
                
                case 9:
                    MarcadorLivrosService.excluirMarcador();
                break; 
                
                case 10:
                    MarcadorLivrosService.logout();
                break;
                
                default:
                    System.out.println("Identificador de operação inválido.");
            }
        }    
    }
}
