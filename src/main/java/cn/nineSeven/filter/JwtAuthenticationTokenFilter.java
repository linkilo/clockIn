package cn.nineSeven.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.nineSeven.constant.AppHttpCodeEnum;
import cn.nineSeven.constant.SystemConstant;
import cn.nineSeven.entity.Result;
import cn.nineSeven.entity.pojo.LoginUser;
import cn.nineSeven.utils.WebUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    RedisTemplate redisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if(StrUtil.isBlank(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        boolean verify = JWTUtil.verify(token, SystemConstant.JWTKey.getBytes());
        if(!verify) {
            authenticateFail(response);
            return;
        }
        JWT jwt = JWTUtil.parseToken(token);
        Long userId = (Long) jwt.getPayload("userId");
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(SystemConstant.redisLoginUser + userId);

        if(loginUser == null){
            authenticateFail(response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private static void authenticateFail(HttpServletResponse response) {
        WebUtils.renderString(response, JSON.toJSONString(Result.errorResult(AppHttpCodeEnum.NEED_LOGIN)));
    }
}
