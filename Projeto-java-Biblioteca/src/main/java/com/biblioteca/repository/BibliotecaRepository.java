package com.biblioteca.repository;

import com.biblioteca.model.Biblioteca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {
    
    // Busca por nome (filtro)
    List<Biblioteca> findByNomeContainingIgnoreCase(String nome);
    
    // Busca por endereço (filtro)
    List<Biblioteca> findByEnderecoContainingIgnoreCase(String endereco);
    
    // Busca por gerente
    List<Biblioteca> findByGerenteId(Long gerenteId);
}