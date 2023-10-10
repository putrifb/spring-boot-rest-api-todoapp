package restapi.todoapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ResourceBadRequest extends RuntimeException{
    public ResourceBadRequest (String message){super(message);}
}
