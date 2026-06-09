package com.senai.revisao2.services;

import com.senai.revisao2.dtos.UsuarioDto;
import com.senai.revisao2.entities.UsuarioEntity;
import com.senai.revisao2.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioDto realizarLogin(UsuarioDto usuarioDto){

       Optional<UsuarioEntity> usuarioOP = repository.findByEmailAndSenha(usuarioDto.getEmail(), usuarioDto.getSenha());

       UsuarioDto usuarioDtoRetorno = new UsuarioDto();

       if (usuarioOP.isPresent()){
           usuarioDtoRetorno = converterEntityParaDto(usuarioOP.get());
           return usuarioDtoRetorno;
       }

        return usuarioDtoRetorno;
    }

    private UsuarioDto converterEntityParaDto(UsuarioEntity usuario){
        UsuarioDto usuarioDto = new UsuarioDto();

        usuarioDto.setNome(usuario.getNome());
        usuarioDto.setEmail(usuario.getEmail());

        return usuarioDto;
    }

    private UsuarioEntity converterDtoParaEntity(UsuarioDto usuarioDto){
        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setNome(usuarioDto.getNome());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setSenha(usuarioDto.getSenha());

        return usuario;
    }

}
