package by.rom.projectapi.exception;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
@AllArgsConstructor
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public ResponseEntity<ErrorResponse> handleError(WebRequest request) {

        Map<String, Object> attributes = errorAttributes.getErrorAttributes(
                request,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.EXCEPTION, ErrorAttributeOptions.Include.MESSAGE)
        );

        return ResponseEntity
                .status((Integer) attributes.get("status"))
                .body(ErrorResponse
                        .builder()
                        .message((String) attributes.get("message"))
                        .timestamp(LocalDateTime.now())
                        .path(request.getDescription(false))
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .build()
                );
    }
}
