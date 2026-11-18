
package etec.backend.controller

import etec.backend.dto.ErrorResponse
import etec.backend.exception.MontoMenorACeroException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MontoMenorACeroException::class)
    fun manejarPrecondicionInvalida(
        ex: MontoMenorACeroException
    ): ResponseEntity<ErrorResponse> {

        val cuerpoError = ErrorResponse(
            error = ex.message ?: "Parámetros inválidos"
        )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(cuerpoError)
    }
}
