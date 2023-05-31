package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;

//Se crea esta clase para modificar los datos del medico (nombre, documento, direccion)
public record DatosActualizarMedico(@NotNull Long id, String nombre, String documento, DatosDireccion direccion) {

}
