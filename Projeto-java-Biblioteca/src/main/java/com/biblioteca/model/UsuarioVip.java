package com.biblioteca.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("VIP")
public class UsuarioVip extends Usuario {
    
    @Column(name = "desconto_multa")
    private double descontoMulta = 0.2;
    
    // Construtores
    public UsuarioVip() {
        super();
    }
    
    public UsuarioVip(String nome, String email) {
        super(nome, email);
    }
    
    public UsuarioVip(String nome, String email, double descontoMulta) {
        super(nome, email);
        this.descontoMulta = descontoMulta;
    }
    
    // Getters e Setters
    public double getDescontoMulta() {
        return descontoMulta;
    }
    
    public void setDescontoMulta(double descontoMulta) {
        this.descontoMulta = descontoMulta;
    }
    
    @Override
    public String getTipoUsuario() {
        return "VIP";
    }
}