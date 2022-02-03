// package p.lodz.pl.pas.filters;
//
// import p.lodz.pl.pas.services.LoginService;
//
// import javax.inject.Inject;
// import javax.servlet.*;
// import javax.servlet.annotation.WebFilter;
// import javax.ws.rs.container.ContainerRequestContext;
// import javax.ws.rs.container.ContainerRequestFilter;
// import javax.ws.rs.container.PreMatching;
// import javax.ws.rs.core.MultivaluedMap;
// import java.io.IOException;
// import java.util.logging.Logger;
//
// @WebFilter("/*")
// // @PreMatching
// public class TokenFilter implements Filter {
//     private final Logger LOGGER = Logger.getLogger(getClass().getName());
//
//
//     @Inject
//     LoginService loginService;
//
//     @Override
//     public void init(FilterConfig filterConfig) throws ServletException {
//         Filter.super.init(filterConfig);
//     }
//
//     @Override
//     public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        
//     }
//
//     @Override
//     public void destroy() {
//         Filter.super.destroy();
//     }
//
//     // @Override
//     // public void filter(ContainerRequestContext containerRequestContext) throws IOException {
//     //     LOGGER.info("Filter triggered");
//     //     MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();
//     //     headers.add("Authorization", "Bearer " + loginService.getToken());
//     // }
// }