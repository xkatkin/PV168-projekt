
package cz.muni.fi.pv168.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class MujFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter inicializován");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("požadavek na "+((HttpServletRequest)req).getRequestURI());
        chain.doFilter(req,res);
    }

    @Override
    public void destroy() {
    }
}