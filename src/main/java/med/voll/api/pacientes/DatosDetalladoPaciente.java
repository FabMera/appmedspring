package med.voll.api.pacientes;

import med.voll.api.direccion.Direccion;

import java.util.Optional;

public record DatosDetalladoPaciente(String nombre, String mail, String telefono, String documento, Direccion direccion) {

    public DatosDetalladoPaciente(Paciente paciente) {
        this(paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(), paciente.getDocumento(), paciente.getDireccion());
    }

}
