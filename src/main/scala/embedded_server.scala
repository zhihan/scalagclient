package org.mine.gclient.embedded

/**
  A simple embedded Jetty server that is used to obtain authentication
  tokens. Adapted from 'Embedding Jetty' examples from Jetty.
 */

import java.io.IOException
import java.io.PrintWriter
 
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
 
import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.handler.AbstractHandler
import org.eclipse.jetty.server.Server

object Handler extends AbstractHandler {
  val greeting = "Hello"
  
  @throws(classOf[IOException])
  @throws(classOf[ServletException]) 
  override def handle(target:String, baseRequest:Request,
    request:HttpServletRequest, response:HttpServletResponse) {
    response.setContentType("text/html; charset=utf-8")
    response.setStatus(HttpServletResponse.SC_OK)

    val out = response.getWriter()
    out.println("<h1>" + greeting + "</h1>");
    baseRequest.setHandled(true);
  }
}

object MyServer {
  val server = new Server(8080)
  def start {
    server.setHandler(Handler)
    server.start()
    server.join()
  }
}
