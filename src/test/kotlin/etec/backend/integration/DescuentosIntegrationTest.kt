
package etec.backend.integration

import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class DescuentosIntegrationTest(
    private val navegador: MockMvc
) : BehaviorSpec({

    Given("el sistema completo con sus servicios reales") {

        When("el usuario 123 solicita un descuento sobre 100.0") {

            val respuesta = navegador.perform(
                get("/api/descuentos")
                    .param("userID", "123")
                    .param("monto", "100.0")
            )

            Then("debe responder HTTP 200") {
                respuesta.andExpect(status().isOk)
            }

            And("debe aplicar un descuento del 25 por ciento") {
                respuesta.andExpect(
                    jsonPath("$.montoFinal").value(75.0)
                )
            }
        }

        When("un cliente envía un monto negativo") {

            val respuesta = navegador.perform(
                get("/api/descuentos")
                    .param("userID", "123")
                    .param("monto", "-100.0")
            )

            Then("debe responder HTTP 400") {
                respuesta.andExpect(status().isBadRequest)
            }

            And("debe devolver el mensaje de error") {
                respuesta.andExpect(
                    jsonPath("$.error")
                        .value("El monto debe ser mayor a cero")
                )
            }
        }
    }
})
