/**
 * @(#)AuthFilter.java, Jun 14, 2013. 
 * 
 */
package com.cloudstone.emenu.web.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloudstone.emenu.util.AuthHelper;

/**
 * @author xuhongfeng
 *
 */
public class AuthFilter implements Filter {
    private String loginUrl;
    private AuthPattern[] escapePatterns;

    /* ---------- Override --------- */
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        String url = req.getRequestURI().toLowerCase();
        String method = req.getMethod().toLowerCase();

        //check escape
        for (AuthPattern p : escapePatterns) {
            if (p.matches(url, method)) {
                chain.doFilter(request, response);
                return;
            }
        }
        
        if (AuthHelper.isLogin(req, resp)) {
            chain.doFilter(request, response);
            return;
        }
        
        //TODO check api category
        resp.sendRedirect(loginUrl);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    /* ---------- inject --------- */
    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    /**
     * @param escapeUrls the escapeUrls to set
     */
    public void setEscapePatterns(String[] escapePatterns) {
        this.escapePatterns = new AuthPattern[escapePatterns.length];
        for (int i = 0; i < escapePatterns.length; ++i) {
            this.escapePatterns[i] = new AuthPattern(escapePatterns[i].toLowerCase());
        }
    }
    
    /* ---------- Inner Class --------- */
    private class AuthPattern {

        /**
         * url pattern
         */
        Pattern pattern;

        /**
         * method, get,put,delete,post,head,*
         */
        String[] methods;

        /**
         * line is like "/api/users/::post"
         * @param line
         */
        AuthPattern(String line) {
            int idx = line.indexOf("::");
            String url, method;
            if (idx > 0) {
                url = line.substring(0, idx);
                method = line.substring(idx+2);
            } else {
                url = line;
                method = "*";
            }
            this.pattern = Pattern.compile(url);
            this.methods = method.split(",");
        }

        public boolean matches(String url, String method) {
            if (pattern.matcher(url).matches()) {
                if (methods == null) {
                    return true;
                }
                for (String m : methods) {
                    if ("*".equals(m) || method.equals(m)) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
    }
}
