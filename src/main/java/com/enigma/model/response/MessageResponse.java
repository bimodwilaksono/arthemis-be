package com.enigma.model.response;

public class MessageResponse extends CommonResponse {
    public MessageResponse(String code, String status, String message) {
        this.setCode(code);
        this.setStatus(status);
        this.setMessage(message);
    }
}


