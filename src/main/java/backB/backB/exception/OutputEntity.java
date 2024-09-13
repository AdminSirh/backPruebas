/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backB.backB.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import static com.backB.backB.util.Response.INTERNALERROR;
import java.util.ArrayList;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 *
 * @author jarellano22
 */
@Data
public class OutputEntity<T> {

    @JsonIgnore
    private HttpStatus code;
    private ArrayList<String> messages = new ArrayList<>();
    private Integer error = 0;
    private T data;

    public OutputEntity<T> success(Integer code, String message, T data) {
        this.code = this.code(code);
        this.messages.add(message);
        this.data = data;
        return this;
    }

    public OutputEntity<T> failed(Integer code, String message, T data) {
        this.error = 1;
        this.code = this.code(code);
        this.messages.add(message);
        this.data = data;
        return this;
    }

    public OutputEntity<T> failed(Integer code, ArrayList<String> message, T data) {
        this.error = 1;
        this.code = this.code(code);
        this.messages = (message);
        this.data = data;
        return this;
    }

    public OutputEntity<T> error() {
        this.error = 1;
        this.code = this.code(500);
        this.messages.add(INTERNALERROR.getKey());
        this.data = null;
        return this;
    }

    private HttpStatus code(Integer code) {
        HttpStatus status = null;
        switch (code) {
            case 200:
                status = HttpStatus.OK;
                break;
            case 201:
                status = HttpStatus.CREATED;
                break;
            case 404:
                status = HttpStatus.NOT_FOUND;
                break;
            case 400:
                status = HttpStatus.BAD_REQUEST;
                break;
            case 500:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            case 409:
                status = HttpStatus.CONFLICT;
                break;
            case 10:
                status = HttpStatus.BAD_REQUEST;
                break;
            case 11:
                status = HttpStatus.BAD_REQUEST;
                break;
            case 1:
                status = HttpStatus.BAD_REQUEST;
                break;
            case 2:
                status = HttpStatus.OK;
                break;
            case 3:
                status = HttpStatus.BAD_REQUEST;
                break;
            case 4:
                status = HttpStatus.BAD_REQUEST;
                break;
            case 5:
                status = HttpStatus.BAD_REQUEST;
                break;
        }
        return status;
    }

    public void success(String datos_de_banco_modificados_con_éxito) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
