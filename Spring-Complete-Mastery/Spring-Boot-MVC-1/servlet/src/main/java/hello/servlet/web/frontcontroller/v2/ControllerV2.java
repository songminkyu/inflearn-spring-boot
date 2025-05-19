package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.ViewRenderer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ControllerV2 {
    ViewRenderer process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
