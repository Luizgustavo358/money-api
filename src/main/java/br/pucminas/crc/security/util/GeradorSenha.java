package br.pucminas.crc.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by luiz on 26/10/17.
 */
public class GeradorSenha
{
    public static void main(String[] args)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println(encoder.encode("admin"));
    }// end main()
}// end class GeradorSenha
