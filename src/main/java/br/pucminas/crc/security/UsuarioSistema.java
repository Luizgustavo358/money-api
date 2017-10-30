package br.pucminas.crc.security;

import java.util.Collection;

import br.pucminas.crc.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Created by luiz on 30/10/17.
 */
public class UsuarioSistema extends User
{
    private static final long serialVersionUID = 1L;

    private Usuario usuario;

    public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities)
    {
        super(usuario.getEmail(), usuario.getSenha(), authorities);
        this.usuario = usuario;
    }// end construtor

    public Usuario getUsuario()
    {
        return usuario;
    }// end getUsuario()
}// end class UsuarioSistema
