package med.voll.api.pacientes;

import jakarta.validation.constraints.NotNull;
import med.voll.api.direccion.DatosDireccion;

//Se crea esta clase para modificar los datos del paciente (nombre, documento, direccion)
public record DatosActualizarPaciente(@NotNull Long id, String nombre, String documento, DatosDireccion direccion) {
}
