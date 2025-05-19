package hello.servlet.web.frontcontroller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public interface ViewRenderer {
    void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
