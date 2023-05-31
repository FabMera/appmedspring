package med.voll.api.domain.pacientes;

import med.voll.api.domain.direccion.Direccion;

public record DatosDetalladoPaciente(String nombre, String mail, String telefono, String documento, Direccion direccion) {

    public DatosDetalladoPaciente(Paciente paciente) {
        this(paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(), paciente.getDocumento(), paciente.getDireccion());
    }

}
