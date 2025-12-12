package com.biblioteca.controller;

import com.biblioteca.model.Biblioteca;
import com.biblioteca.model.Gerente;
import com.biblioteca.service.BibliotecaService;
import com.biblioteca.service.GerenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/bibliotecas")
public class BibliotecaController {
    
    @Autowired
    private BibliotecaService bibliotecaService;
    
    @Autowired
    private GerenteService gerenteService;
    
    // Listar/Consultar com filtro
    @GetMapping
    public String listar(@RequestParam(required = false) String filtro, Model model) {
        List<Biblioteca> bibliotecas;
        
        if (filtro != null && !filtro.trim().isEmpty()) {
            bibliotecas = bibliotecaService.buscarPorNome(filtro);
            model.addAttribute("filtro", filtro);
        } else {
            bibliotecas = bibliotecaService.listarTodas();
        }
        
        model.addAttribute("bibliotecas", bibliotecas);
        return "biblioteca/listar";
    }
    
    // Formulário de cadastro
    @GetMapping("/novo")
    public String novoFormulario(Model model) {
        model.addAttribute("biblioteca", new Biblioteca());
        model.addAttribute("gerentes", gerenteService.listarTodos());
        return "biblioteca/formulario";
    }
    
    // Salvar (cadastro)
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Biblioteca biblioteca, 
                        @RequestParam Long gerenteId,
                        RedirectAttributes redirectAttributes) {
        try {
            Gerente gerente = gerenteService.buscarPorId(gerenteId)
                .orElseThrow(() -> new RuntimeException("Gerente não encontrado"));
            
            biblioteca.setGerente(gerente);
            bibliotecaService.salvar(biblioteca);
            
            redirectAttributes.addFlashAttribute("mensagemSucesso", 
                "Biblioteca cadastrada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", 
                "Erro ao cadastrar biblioteca: " + e.getMessage());
        }
        
        return "redirect:/bibliotecas";
    }
    
    // Formulário de edição
    @GetMapping("/editar/{id}")
    public String editarFormulario(@PathVariable Long id, Model model, 
                                   RedirectAttributes redirectAttributes) {
        Biblioteca biblioteca = bibliotecaService.buscarPorId(id)
            .orElse(null);
        
        if (biblioteca == null) {
            redirectAttributes.addFlashAttribute("mensagemErro", 
                "Biblioteca não encontrada!");
            return "redirect:/bibliotecas";
        }
        
        model.addAttribute("biblioteca", biblioteca);
        model.addAttribute("gerentes", gerenteService.listarTodos());
        return "biblioteca/formulario";
    }
    
    // Atualizar (update)
    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id, 
                           @ModelAttribute Biblioteca biblioteca,
                           @RequestParam Long gerenteId,
                           RedirectAttributes redirectAttributes) {
        try {
            Gerente gerente = gerenteService.buscarPorId(gerenteId)
                .orElseThrow(() -> new RuntimeException("Gerente não encontrado"));
            
            biblioteca.setId(id);
            biblioteca.setGerente(gerente);
            bibliotecaService.salvar(biblioteca);
            
            redirectAttributes.addFlashAttribute("mensagemSucesso", 
                "Biblioteca atualizada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", 
                "Erro ao atualizar biblioteca: " + e.getMessage());
        }
        
        return "redirect:/bibliotecas";
    }
    
    // Deletar
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (!bibliotecaService.existe(id)) {
                redirectAttributes.addFlashAttribute("mensagemErro", 
                    "Biblioteca não encontrada!");
            } else {
                bibliotecaService.deletar(id);
                redirectAttributes.addFlashAttribute("mensagemSucesso", 
                    "Biblioteca removida com sucesso!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", 
                "Erro ao remover biblioteca: " + e.getMessage());
        }
        
        return "redirect:/bibliotecas";
    }
}