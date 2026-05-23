package com.example.empre_go.service;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.empre_go.dto.AutenticacaoDto;
import com.example.empre_go.dto.TokenDto;
import com.example.empre_go.dto.UsuarioDto;
import com.example.empre_go.models.Usuario;

public interface UsuarioService {

    Usuario salvar(UsuarioDto dto);

    UsuarioDto obterUsuarioPorId(Integer id);

    List<UsuarioDto> obterUsuarios();

    UserDetails autenticarTeste(Usuario usuario);

    TokenDto autenticar(AutenticacaoDto autenticacao);
}