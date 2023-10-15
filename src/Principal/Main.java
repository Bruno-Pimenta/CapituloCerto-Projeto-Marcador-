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
import java.sql.SQLException;
import java.util.Scanner;

import Service.UsuarioService;
/**
 *
 * @author Bruno
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException{
        //login();
        /*MarcadorLivros ml = new MarcadorLivros("Bruno", 16, 5, "Completo", 0, "Não tem", "N", "bl" );
        MarcadorLivrosRepository mLR = MarcadorLivrosRepository.pegaInstancia();
        mLR.insereMarcadorLivros(ml);*/
        /*System.out.println(ml);*/
        
    }
    
    /*public static void login() throws SQLException{
        try {
            AutenticarUsuario.login();
        } catch (IllegalArgumentException e) {
            System.out.println("Usuário e/ou senha inválidos " + e.getMessage());
        }
    }*/
}
