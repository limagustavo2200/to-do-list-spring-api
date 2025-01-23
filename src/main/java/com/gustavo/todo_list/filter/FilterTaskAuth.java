package com.gustavo.todo_list.filter;

import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.gustavo.todo_list.user.IUserRepository;
import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


// Filtro para autenticar os usuarios na criação de tasks
@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    IUserRepository userRepository;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        
        // Salva o valor do path em servletPath
        var servletPath = request.getServletPath();
        
        // Verifica se o Path inicia com a "/tasks/" e entro no if
        if (servletPath.startsWith("/tasks/")) {
            // Pegar autenticação (usuario e senha) que é passando no header da requisição
            var authorization = request.getHeader("Authorization");  
            
            // pegando o valor do auth e removendo o basic e depois removendo os espaços com o trim
            var authEncoded = authorization.substring("Basic".length()).trim();  
                    
            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            var authString = new String(authDecode);

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            System.out.println("Authorization");
            System.out.println(username);
            System.out.println(password);

            // validar usuario que veio do header da requisição e tratado acima seprando em variaveis especificas como username e password
            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401, "Usuário não encontrado!");
            } else {
                // validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if(passwordVerify.verified) {

                    // Se as credenciais (usuário e senha) passadas no cabeçalho da requisição forem validadas com sucesso no banco de dados,
                    // o filtro atribui o ID do usuário autenticado ao atributo "idUser" da requisição.
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401, "Credenciais incorretas!");
                }
            }     
        } else {
            filterChain.doFilter(request, response);
        }
    }  
}
