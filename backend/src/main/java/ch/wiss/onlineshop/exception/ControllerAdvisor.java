package ch.wiss.onlineshop.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// Diese Klasse ist ein globaler Exception-Handler für den Controller.
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	// Diese Methode behandelt fehlende Request-Parameter und gibt eine benutzerdefinierte Fehlermeldung zurück.
	// Sie wird aufgerufen, wenn ein erforderlicher Parameter in der Anfrage fehlt.
    @Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", ex.getMessage());

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	// Diese Methode behandelt ungültige Argumente in der Anfrage, die durch @Valid annotierte Parameter betreffen.
	// Sie wird aufgerufen, wenn die Validierung eines Arguments fehlschlägt.
    @Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}