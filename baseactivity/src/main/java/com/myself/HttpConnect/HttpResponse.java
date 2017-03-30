package com.myself.HttpConnect;

/**
 * Created by Emiya on 16/8/26.
 */
public class HttpResponse<T> {
    private String code;
    private String message;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/--------------------------------------------------------------------/\n");
        if (data != null)
            stringBuilder.append(data.getClass().getName()).append("\n");
        stringBuilder.append("code = ").append(code).append("\n");
        stringBuilder.append("message = ").append(message).append("\n");
        if (data != null)
            stringBuilder.append(data.toString()).append("\n");
        stringBuilder.append("/--------------------------------------------------------------------/\n");
        return stringBuilder.toString();
    }
}
