/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sirh.sirh.exception;

import java.util.ArrayList;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 *
 * @author jarellano22
 */
@Getter
public class Exceptions extends Exception {

    private static final long serialVersionUID = 1L;
    private ArrayList<String> messages = new ArrayList<>();
    private Integer code;
    private HttpStatus code2;

    public Exceptions(ArrayList<String> messages, Integer code) {
        super();
        this.messages = messages;
        this.code = code;
    }

    public Exceptions(String messages, Integer code) {
        super();
        this.messages.add(messages);
        this.code = code;
    }

    public Exceptions(ArrayList<String> messages, HttpStatus code2) {
        super();
        this.messages = messages;
        this.code2 = code2;
    }

    public Exceptions(String messages, HttpStatus code2) {
        super();
        this.messages.add(messages);
        this.code2 = code2;
    }

}
