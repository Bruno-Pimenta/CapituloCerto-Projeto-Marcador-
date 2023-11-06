/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Principal;

import Autenticacao.AutenticarUsuario;
import Model.Marcador;
import Model.MarcadorLivros;
import Model.MarcadorSeries;
import Model.Usuario;
import Repository.MarcadorLivrosRepository;
import Service.MarcadorLivrosService;
import java.sql.SQLException;
import java.util.Scanner;

import Service.UsuarioService;
import java.util.List;
/**
 *
 * @author Bruno
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException, Exception{
        //login();
        
        MarcadorLivrosService mLS = new MarcadorLivrosService();
        //mLS.cadastrarMarcadorDeLivros();
        //mLS.excluirMarcadorLivro(5);
        //List lista = mLS.buscaPorTags();
        mLS.atualizarPaginaLivro();
        MarcadorLivros mL = mLS.buscarPorId(4);
        System.out.println(mL);
    }
    
    /*public static void login() throws SQLException{
        try {
            AutenticarUsuario.login();
        } catch (IllegalArgumentException e) {
            System.out.println("Usuário e/ou senha inválidos " + e.getMessage());
        }
    }*/
}
