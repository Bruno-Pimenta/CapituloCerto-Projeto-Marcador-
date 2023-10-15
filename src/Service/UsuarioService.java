/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Autenticacao.AutenticarUsuario;
import Model.Usuario;
import Repository.UsuarioRepository;
import java.util.Scanner;

/**
 *
 * @author Bruno
 */
public class UsuarioService {

    public UsuarioService() {
    }
        
    public void cadastrarUsuario(){
        String nome, senha, confereSenha;
        int tipo;
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome de usuário");
        nome = sc.nextLine();
        System.out.println("Digite a senha");
        senha = sc.nextLine();
        System.out.println("Confirme a senha");
        confereSenha = sc.nextLine();
        if(senha.equals(confereSenha)==true){
            System.out.println("Digite o número referente ao plano");
            System.out.println("1 - Licença gratuita\n2 - Plano Premium: R$ 9.99");
            tipo = sc.nextInt();
            if(tipo>=1&&tipo<=2){
                senha = AutenticarUsuario.retornaHash(senha);
                Usuario usuario = new Usuario(nome, senha, tipo);
                UsuarioRepository uR = new UsuarioRepository();
                uR.cadastrarUsuario(usuario);
            }
            else{
                System.out.println("Digito inválido");
            }
        }
        else{
            System.out.println("Senha não confere");
        }
    }
}
