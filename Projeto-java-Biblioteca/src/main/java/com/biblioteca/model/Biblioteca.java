package com.biblioteca.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bibliotecas")
public class Biblioteca {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String endereco;
    
    // Associação com Gerente (requisito obrigatório de associação)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "gerente_id")
    private Gerente gerente;
    
    @OneToMany(mappedBy = "biblioteca", cascade = CascadeType.ALL)
    private List<Livro> livros = new ArrayList<>();
    
    // Construtores
    public Biblioteca() {}
    
    public Biblioteca(String nome, String endereco, Gerente gerente) {
        this.nome = nome;
        this.endereco = endereco;
        this.gerente = gerente;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public Gerente getGerente() {
        return gerente;
    }
    
    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }
    
    public List<Livro> getLivros() {
        return livros;
    }
    
    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }
    
    public void adicionarLivro(Livro livro) {
        livros.add(livro);
        livro.setBiblioteca(this);
    }
}