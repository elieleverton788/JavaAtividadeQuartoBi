package com.biblioteca.repository;

import com.biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Busca por nome (filtro)
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
    
    // Busca por email
    Optional<Usuario> findByEmail(String email);
    
    // Verifica se email já existe
    boolean existsByEmail(String email);
}