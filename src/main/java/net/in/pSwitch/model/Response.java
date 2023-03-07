package net.in.pSwitch.model;

import lombok.Data;

@Data
public class Response<T> {
    private String message;
    private boolean error=false;
    private T result;
}
