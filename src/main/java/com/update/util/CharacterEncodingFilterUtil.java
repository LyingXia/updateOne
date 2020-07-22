package com.update.util;

import javax.servlet.*;
import java.io.IOException;

/**
 * created by xiapf on 2018/8/8
 */
public class CharacterEncodingFilterUtil implements Filter {

    protected String encoding = null;

    protected FilterConfig filterConfig = null;

    protected boolean ignore = true;

    @Override
    public void destroy() {
        this.encoding = null;
        this.filterConfig = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)throws IOException, ServletException {
        if (ignore || (request.getCharacterEncoding() == null)) {
            String encoding= selectEncoding(request);
            if (encoding != null)
                request.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        // 获取初始化参数
        this.encoding = filterConfig.getInitParameter("encoding");
        String value= filterConfig.getInitParameter("ignore");
        if (value == null) {
            this.ignore = true;
        }else if (value.equalsIgnoreCase("true")) {
            this.ignore = true;
        }else if (value.equalsIgnoreCase("yes")) {
            this.ignore = true;
        }else
            this.ignore = false;

    }

    protected String selectEncoding(ServletRequest request) {
        return (this.encoding);
    }
}
