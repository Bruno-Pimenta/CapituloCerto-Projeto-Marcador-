/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Bruno
 */
public abstract class Marcador {
    private int id;
    private String nome;
    private String status;
    private float nota;
    private String anotacoes;
    private String autores;
    private String categorias;
    
     public Marcador(String nome, String status, float nota, String anotacoes, String autores, String categorias) {
        this.nome = nome;
        this.status = status;
        this.nota = nota;
        this.anotacoes = anotacoes;
        this.autores = autores;
        this.categorias = categorias;
    }

    public Marcador(int id, String nome, String status, float nota, String anotacoes, String autores, String categorias) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.nota = nota;
        this.anotacoes = anotacoes;
        this.autores = autores;
        this.categorias = categorias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }
}
