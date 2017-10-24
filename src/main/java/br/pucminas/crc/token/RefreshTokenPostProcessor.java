package br.pucminas.crc.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by luiz on 24/10/17.
 */

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken>
{
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass)
    {
        return returnType.getMethod().getName().equals("postAccessToken");
    }// end supports()

    @Override
    public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter methodParameter,
                                             MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                             ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse)
    {
        HttpServletRequest req = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        HttpServletResponse resp = ((ServletServerHttpResponse) serverHttpResponse).getServletResponse();

        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;

        String refreshToken = body.getRefreshToken().getValue();

        adicionarRefreshTokenNoCookie(refreshToken, req, resp);

        removerRefreshTokenDoBody(token);

        return body;
    }// end beforeBodyWrite()

    private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token)
    {
        token.setRefreshToken(null);
    }// end removerRefreshTokenDoBody()

    private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp)
    {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

        refreshTokenCookie.setHttpOnly(true);

        refreshTokenCookie.setSecure(false);

        refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");

        refreshTokenCookie.setMaxAge(2592000);

        resp.addCookie(refreshTokenCookie);
    }// end adicionarRefreshTokenNoCookie()
}// end class RefreshTokenPostProcessor
