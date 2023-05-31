package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    //Constructor Injection - Inyeccion por constructor - Se inyecta la dependencia a traves del constructor de la clase que la necesita (MedicoController)
    public MedicoController(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }
    //DTO - Data Transfer Object - Objeto de Transferencia de Datos - Clase que se usa para transferir datos entre subsistemas de un software

    //Metodo para registrar un medico
    //UricomponetsBuilder nos sirve para capturar los datos de la URL
    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder) {
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(),
                medico.getNombre(), medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getNumero(), medico.getDireccion().getCiudad(), medico.getDireccion().getDistrito(),
                medico.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/medicos/${id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
        //return codigo de estado 201 (created),url de la nueva entidad creada con su id y el cuerpo de la respuesta

    }

    //Pageable tiene un tamaño por defecto de 20, pero se puede cambiar con @PageableDefault size
    // pero si especificamos el tamaño en la url, este ultimo tiene prioridad sobre el @PageableDefault size que se haya especificado en el codigo
    //Metodo para listar los medicos
    @GetMapping
    public ResponseEntity<Page<DatosListadoMedicos>> listadoMedicos(@PageableDefault(size = 5) Pageable paginacion) {
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedicos::new));
        //este metodo devuelve todos los medicos (activos e inactivos)
        //return medicoRepository.findAll(paginacion).map(DatosListadoMedicos::new);
        //este metodo (abajo) devuelve solo los medicos que esten activos
        //return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedicos::new);
        //se puede modificar la nomenclatura de los metodos de la interfaz MedicoRepository para que sean mas descriptivos y claros (findByActivoTrue)
    }

    //metodo para actualizar los datos del medico
    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(),
                medico.getNombre(), medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getNumero(), medico.getDireccion().getCiudad(), medico.getDireccion().getDistrito(),
                medico.getDireccion().getComplemento())));
    }

    //Metodo para eliminar un medico de manera logica (cambia el estado del medico a inactivo)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornarDatosMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        var datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(),
                medico.getNombre(), medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getNumero(), medico.getDireccion().getCiudad(), medico.getDireccion().getDistrito(),
                medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosRespuestaMedico);
    }


}


