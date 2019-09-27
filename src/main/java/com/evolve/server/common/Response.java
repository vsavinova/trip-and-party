package com.evolve.server.common;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Response {
    String code;
    Object result;

    public Response(String code, Object result) {
        this.code = code;
        this.result = result;
    }
}
