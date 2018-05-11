/* Copyright (c) 2017-2018 MIT 6.031 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package karaoke;

import java.io.IOException;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

/**
 * Filter that adds standard headers to every response.
 * 
 * <p>PS4 instructions: you may use, modify, or remove this class.
 */
public class HeadersFilter extends Filter {
    
    private final Headers headers = new Headers();
    
    @Override public String description() { return "Add headers to all responses"; }
    
    /**
     * Add a new header to the set of standard headers.
     * @param key header name, e.g. "Content-Type"
     * @param value header value, e.g. "text/plain"
     */
    public void add(String key, String value) {
        headers.add(key, value);
    }
    
    @Override public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        exchange.getResponseHeaders().putAll(headers);
        chain.doFilter(exchange);
    }
}
