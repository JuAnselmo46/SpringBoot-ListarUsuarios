package com.juliana.cadastro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.juliana.cadastro.entities.Usuario;
import com.juliana.cadastro.repositories.UsuarioRepository;

@Service
public class UsuarioService {
   
    @Autowired
    private UsuarioRepository repository;
   
    public List<Usuario> listarTodos(){
        return repository.findAll();
    }
    
    // Buscar usuário por ID
    public Usuario buscarPorId(Long id) {
        // findById retorna Optional, usamos orElse(null) para retornar null se não existir
        return repository.findById(id).orElse(null);
    }
   
    public Usuario cadastrar(Usuario usuario) {
        // senha criptografada
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        return repository.save(usuario);
    }
    
    public Usuario atualizar(Long id, Usuario dados) {
    	Usuario usuario = repository.findById(id).orElse(null);

    	if (usuario == null) {
    	return null;
    	}

    	usuario.setNome(dados.getNome());
    	usuario.setEmail(dados.getEmail());
    	usuario.setSenha(dados.getSenha());
    	usuario.setPerfil(dados.getPerfil());
    	usuario.setEndereco(dados.getEndereco());
    	usuario.setBairro(dados.getBairro());
    	usuario.setComplemento(dados.getComplemento());
    	usuario.setCep(dados.getCep());
    	usuario.setCidade(dados.getCidade());
    	usuario.setEstado(dados.getEstado());

    	return repository.save(usuario);
    	}

    	public boolean deletar(Long id) {
    	if (!repository.existsById(id)) {
    	return false;
    	}

    	// localhost:8080/usuarios/3 DELETE
    	repository.deleteById(id);
    	return true;
    	}
    	
        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

            public Usuario login(String email, String senha) {
                           
                Usuario usuario =  repository.findByEmail(email);
               
                if (usuario == null) {
                    return null;
                }
               
                // Validar senha com bcrypt
                if (!passwordEncoder.matches(senha, usuario.getSenha())) {
                    return null;
                }
                return usuario;
            }
       
}

