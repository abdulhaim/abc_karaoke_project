/* Copyright (c) 2017-2018 MIT 6.031 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package karaoke;

import java.io.IOException;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

/**
 * Logging filter that reports request URLs, exceptions, and response codes to
 * the console.
 * 
 * <p>PS4 instructions: you may use, modify, or remove this class.
 */
public class LogFilter extends Filter {
    
    @Override public String description() { return "Log requests"; }
    
    @Override public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        System.err.println(" -> " + exchange.getRequestMethod() + " " + exchange.getRequestURI());
        try {
            chain.doFilter(exchange);
        } catch (IOException | RuntimeException e) {
            System.err.print(" !! ");
            e.printStackTrace();
            throw e; // after logging, let the exception continue
        }
        System.err.println(" <- " + exchange.getResponseCode());
    }
}
