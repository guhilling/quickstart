package de.hilling.jee.service;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/ping",
        initParams = {
                @WebInitParam(name = "message", value = "hello, world!")
        })
public class Ping extends HttpServlet {

    private String message;

    @Override
    public void init(ServletConfig config) {
        message = config.getInitParameter("message");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.getWriter()
                .println(message);
    }
}
