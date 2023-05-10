package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico) {
        medicoRepository.save(new Medico(datosRegistroMedico));

    }

    //Pageable tiene un tamaño por defecto de 20, pero se puede cambiar con @PageableDefault size
    // pero si especificamos el tamaño en la url, este ultimo tiene prioridad sobre el @PageableDefault size que se haya especificado en el codigo
    //Metodo para listar los medicos
    @GetMapping
    public Page<DatosListadoMedicos> listadoMedicos(@PageableDefault(size = 5) Pageable paginacion) {
        //este metodo devuelve todos los medicos (activos e inactivos)
        //return medicoRepository.findAll(paginacion).map(DatosListadoMedicos::new);
        //este metodo (abajo) devuelve solo los medicos que esten activos
        return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedicos::new);
        //se puede modificar la nomenclatura de los metodos de la interfaz MedicoRepository para que sean mas descriptivos y claros (findByActivoTrue)
    }

    //metodo para actualizar los datos del medico
    @PutMapping
    @Transactional
    public void actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);

    }

    //Metodo para eliminar un medico por id,PATHVARIABLE es la forma de pasar parametros en la url de la peticion http (en este caso el id)
    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
    }
    //Metodo para eliminar un medico por id,PATHVARIABLE es la forma de pasar parametros en la url de la peticion http (en este caso el id)
        /* @DeleteMapping("/{id}")
           @Transactional
           public void eliminarMedico(@PathVariable Long id) {
          Medico medico = medicoRepository.getReferenceById(id);
          medicoRepository.delete(medico);*/

}


