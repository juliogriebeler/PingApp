package br.com.juliogriebeler.pingapp.dto;

import java.io.Serializable;

public class GetResponse implements Serializable {

    private int code;
    private String message;

    public GetResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("{\"code\"=%d, \"message\"=%s}", code, message);
    }
}
