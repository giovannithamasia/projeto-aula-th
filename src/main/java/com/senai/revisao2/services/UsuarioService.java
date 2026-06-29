package com.senai.revisao2.services;

import com.senai.revisao2.dtos.UsuarioDto;
import com.senai.revisao2.entities.UsuarioEntity;
import com.senai.revisao2.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioDto realizarLogin(String email, String senha) {

        Optional<UsuarioEntity> usuarioOP = repository.findByEmailAndSenha(email, senha);

        if (usuarioOP.isPresent()){
            return converterEntityParaDto(usuarioOP.get());
        }

        return null;
    }

    public List<UsuarioDto> obterListaUsuarios(){
        List<UsuarioEntity> listaUsuario = repository.findAll();
        List<UsuarioDto> listaDtos = new ArrayList<>();

        for(UsuarioEntity usuario : listaUsuario){
            listaDtos.add(converterEntityParaDto(usuario));
        }

        return listaDtos;
    }

    public void inserirUsuario(UsuarioDto usuarioDto){
        repository.save(converterDtoParaEntity(usuarioDto));
    }

    public UsuarioDto obterUsuarioPorId(Long id){
        UsuarioDto usuarioDto = new UsuarioDto();

        Optional<UsuarioEntity> usuarioOP = repository.findById(id);

        if (usuarioOP.isPresent()){
            usuarioDto = converterEntityParaDto(usuarioOP.get());
        }

        return usuarioDto;
    }


    public void atualizarUsuario(UsuarioDto usuarioDto){

        Optional<UsuarioEntity> usuarioOP = repository.findById(usuarioDto.getId());

        if (usuarioOP.isPresent()){
            UsuarioEntity usuario = usuarioOP.get();

            usuario.setNome(usuarioDto.getNome());
            usuario.setEmail(usuarioDto.getEmail());

            if (!usuarioDto.getSenha().isEmpty()){
                usuario.setSenha(usuarioDto.getSenha());
            }

            repository.save(usuario);

        }

    }

    public void excluir(Long id){
        repository.deleteById(id);
    }

    private UsuarioDto converterEntityParaDto(UsuarioEntity usuario){
        UsuarioDto usuarioDto = new UsuarioDto();

        usuarioDto.setId(usuario.getId());
        usuarioDto.setNome(usuario.getNome());
        usuarioDto.setEmail(usuario.getEmail());

        return usuarioDto;
    }

    private UsuarioEntity converterDtoParaEntity(UsuarioDto usuarioDto){
        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setId(usuarioDto.getId());
        usuario.setNome(usuarioDto.getNome());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setSenha(usuarioDto.getSenha());

        return usuario;
    }
}
