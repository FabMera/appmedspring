package med.voll.api.pacientes;

public record DatosListadoPacientes(Long id, String nombre, String email, String documento, String telefono) {

    public DatosListadoPacientes(Paciente paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getDocumento(), paciente.getEmail(), paciente.getTelefono());
    }
}
