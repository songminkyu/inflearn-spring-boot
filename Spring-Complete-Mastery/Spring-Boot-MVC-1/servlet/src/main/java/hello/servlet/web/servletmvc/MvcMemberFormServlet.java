package hello.servlet.web.servletmvc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";

        /**
         * RequestDispatcher:
         * 클라이언트로부터 들어온 요청을 JSP/서블릿 등 다른 리소스로 전달하는 인터페이스
         * - forward: 서블릿에서 JSP로 제어를 넘기는 역할을 함 (서버 내부에서 호출이 일어남)
         * - include: 다른 리소스의 결과를 현재 응답에 포함시킴
         *
         * 클라이언트(브라우저)는 서버 내부에서 일어나는 forward 과정을 알 수 없으며,
         * URL이 변경되지 않고 최초 요청 URL만 표시됨
         */
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}