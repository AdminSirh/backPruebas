/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.BeneficiarioDTO;
import com.sirh.sirh.DTO.BeneficiarioEstatusDTO;
import com.sirh.sirh.DTO.Beneficiario_cartaDTO;
import com.sirh.sirh.DTO.Beneficiario_cartaEstatusDTO;
import com.sirh.sirh.DTO.ContactoDTO;
import com.sirh.sirh.DTO.DireccionDTO;
import com.sirh.sirh.DTO.LicenciaDTO;
import com.sirh.sirh.DTO.MedicoDTO;
import com.sirh.sirh.DTO.PersonaDTO;
import com.sirh.sirh.DTO.PersonaExpedienteDTO;
import com.sirh.sirh.DTO.PersonaGeneralesDTO;
import com.sirh.sirh.config.SystemControllerLog;
import com.sirh.sirh.entity.Beneficiario;
import com.sirh.sirh.entity.Beneficiario_carta;
import com.sirh.sirh.entity.Cat_Colonia;
import com.sirh.sirh.entity.Contacto;
import com.sirh.sirh.entity.Direccion;
import com.sirh.sirh.entity.Licencia;
import com.sirh.sirh.entity.Medico;
import com.sirh.sirh.entity.Persona;
import com.sirh.sirh.service.PersonaService;
import com.sirh.sirh.util.Response;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sirh.sirh.exception.Exceptions;
import sirh.sirh.exception.OutputEntity;

/**
 *
 * @author jarellano22
 */
@RestController
@RequestMapping("personas")
public class PersonaController {

    @Autowired
    PersonaService personaService;

    // Muestra en paginacion a todos los candidatos 
    @GetMapping(value = "/candidatos")
    public ResponseEntity<List<Persona>> paginacionPersona() {
        try {

            List<Persona> page = personaService.personasSinExpediente();
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Guarda datos basicos para crear registro de candidato nuevo validado por curp 
    @PostMapping(value = "/guardarPersona")
    public ResponseEntity<OutputEntity<Persona>> guardarPersona(@RequestBody PersonaDTO persona) {
        OutputEntity<Persona> out = new OutputEntity<>();
        try {
            if (personaService.existeCurp(persona.getCurp()) != null) {
                throw new Exceptions(Response.CURPEXISTE.getKey(), Response.CURPEXISTE.getCode());
            }
            personaService.save(persona);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), null);
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exceptions e) {
            Persona result = personaService.existeCurp(persona.getCurp());
            out.failed(e.getCode(), e.getMessages(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Buscar persona por ID datos generales entidad Persona
    @GetMapping(value = "/buscarPersona/{id}")
    public ResponseEntity<OutputEntity<Persona>> buscarPersona(@PathVariable Integer id) {
        OutputEntity<Persona> out = new OutputEntity<>();
        try {
            Persona result = personaService.findOne(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Buscar ultimo Id registrado en persona
    @GetMapping(value = "/ultimoIdPersona")
    public ResponseEntity<OutputEntity<Integer>> ultimoIdPersona() {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Integer result = personaService.ultimoIdPersona();
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Guarda datos generales para completar datos basicos
    @SystemControllerLog(operation = "guardarPersonaGenerales", type = "Guarda los datos generales de la persona") //Log de quien ejecuta el metodo
    @PostMapping(value = "/guardarPersonaGenerales/{id}")
    public ResponseEntity<OutputEntity<String>> guardarPersonaGenerales(@RequestBody PersonaGeneralesDTO persona, @PathVariable Integer id) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            personaService.save(persona, id);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos generales actualizados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Buscar persona por ID datos generales entidad Persona
    @GetMapping(value = "/buscarDireccion/{persona_id}")
    public ResponseEntity<OutputEntity<Direccion>> buscarDireccionPersona(@PathVariable("persona_id") Integer persona_id) {
        OutputEntity<Direccion> out = new OutputEntity<>();
        try {
            Direccion result = personaService.findDireccion(persona_id);

            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //************************* CONTROLADORES DE DIRECCIÓN *****************************************************
    //******************** GUARDAR DIRECCION DE PERSONA ************************
    @SystemControllerLog(operation = "guardarDireccionPersona", type = "Guardó los datos de la direccion de la persona") //Log de quien ejecuta el metodo
    @PostMapping(value = "/guardarDireccionPersona")
    public ResponseEntity<OutputEntity<String>> guardarDireccionPersona(@RequestBody Direccion direccion) {
        OutputEntity<String> out = new OutputEntity<>();
        try {

            personaService.saveDireccion(direccion);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Dirección actualizada");
            //out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "");
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //******************** EDITAR DIRECCION DE PERSONA ************************
    @SystemControllerLog(operation = "editarPersonaDireccion", type = "Editó los datos de la direccion de la persona") //Log de quien ejecuta el metodo
    @PostMapping(value = "/editarPersonaDireccion/{persona_id}")
    public ResponseEntity<OutputEntity<String>> editarPersonaDireccion(@RequestBody DireccionDTO persona, @PathVariable Integer persona_id) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            personaService.editarDireccionPersona(persona, persona_id);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Dirección actualizada");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //**************************** BUSCAR POR ID_COLONIA ***********************
    @GetMapping(value = "/buscarColonia/{id}")
    public ResponseEntity<OutputEntity<Cat_Colonia>> buscarColonia(@PathVariable Integer id) {
        OutputEntity<Cat_Colonia> out = new OutputEntity<>();
        try {
            Cat_Colonia result = personaService.findOneDireccion(id);

            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //************************* CONTROLADORES DE CONTACTO *****************************************************
    //******************** GUARDAR CONTACTO DE PERSONA ************************
    @SystemControllerLog(operation = "guardarContactoPersona", type = "Guardó los datos de la direccion de la persona") //Log de quien ejecuta el metodo
    @PostMapping(value = "/guardarContactoPersona")
    public ResponseEntity<OutputEntity<String>> guardarContactoPersona(@RequestBody Contacto contacto) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            personaService.saveContacto(contacto);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Los datos han sido guardados con éxito");
            //out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "");
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //******************** EDITAR CONTACTO DE PERSONA ************************
    @SystemControllerLog(operation = "editarPersonaContacto", type = "Editó los datos de la direccion de la persona") //Log de quien ejecuta el metodo
    @PostMapping(value = "/editarPersonaContacto/{persona_id}")
    public ResponseEntity<OutputEntity<String>> editarPersonaContacto(@RequestBody ContactoDTO contacto, @PathVariable Integer persona_id) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            personaService.editarContacto(contacto, persona_id);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Los datos han sido modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Buscar contacto por Id_Persona 
    @GetMapping(value = "/buscarContacto/{persona_id}")
    public ResponseEntity<OutputEntity<Contacto>> buscarContactoPersona(@PathVariable("persona_id") Integer persona_id) {
        OutputEntity<Contacto> out = new OutputEntity<>();
        try {
            Contacto result = personaService.findContacto(persona_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************** EDITAR EXPEDIENTE DE PERSONA ************************
    @PostMapping(value = "/editarPersonaExpediente/{persona_id}")
    public ResponseEntity<OutputEntity<String>> editarPersonaExpediente(@RequestBody PersonaExpedienteDTO persona, @PathVariable Integer persona_id) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            personaService.editarPersonaExpediente(persona, persona_id);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Expediente asignado al candidato");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @GetMapping(value = "/conteoDatosPersona/{id}")
    public ResponseEntity<OutputEntity<Integer>> conteoDatosFaltantesPersona(@PathVariable Integer id) {
    OutputEntity<Integer> out = new OutputEntity<>();
    try {
        Persona result = personaService.findOne(id);
        int contador = 0;

        // Lista de nombres de campos obligatorios
        List<String> camposObligatorios = Arrays.asList("rfc", "apellido_paterno","nombre","apellido_materno", "num_imss", "cat_estado" ,"Cat_cargo_admon_pub_si_no" , "Cat_estado_civil" /* Agrega otros campos obligatorios aquí */);

        // Verificar campos obligatorios
        for (String campo : camposObligatorios) {
            try {
                java.lang.reflect.Field f = result.getClass().getDeclaredField(campo);
                f.setAccessible(true);
                if (f.get(result) == null) {
                    contador++;
                }
            } catch (NoSuchFieldException e) {
                // Manejo de excepciones si el campo no existe o no es accesible
            }
        }
        out.success(Response.OK.getCode(), Response.OK.getKey(), contador);
        return new ResponseEntity<>(out, out.getCode());
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
    //************************ VERIFICAR DATOS FALTANTES**********************
    //------- Datos Faltantes de Persona -------
    /*@GetMapping(value = "/conteoDatosPersona/{id}")
    public ResponseEntity<OutputEntity<Integer>> conteoDatosFaltantesPersona(@PathVariable Integer id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Persona result = personaService.findOne(id);
            int contador = 0;

            if (result.getRfc() == null) {
                contador++;
            }
            if (result.getApellido_materno() == null) {
                contador++;
            }
            if (result.getApellido_paterno() == null) {
                contador++;
            }
            if (result.getCat_cargo_admon_pub_si_no() == null) {
                contador++;
            }
            if (result.getCat_credito_infonavit_si_no() == null) {
                contador++;
            }
            if (result.getCat_estado() == null) {
                contador++;
            }
            if (result.getCat_estado_civil() == null) {
                contador++;
            }
            if (result.getCat_estudios() == null) {
                contador++;
            }
            if (result.getCat_genero() == null) {
                contador++;
            }
            if (result.getCat_hijos_si_no() == null) {
                contador++;
            }
            if (result.getCat_nacionalidad() == null) {
                contador++;
            }
            if (result.getCat_inhabilitado_admon_pub_si_no() == null) {
                contador++;
            }
            if (result.getCurp() == null) {
                contador++;
            }
            if (result.getFecha_matrimonio() == null) {
                contador++;
            }
            if (result.getFecha_nacimiento() == null) {
                contador++;
            }
            if (result.getMatricula_smn() == null) {
                contador++;
            }
            if (result.getNum_imss() == null) {
                contador++;
            }
            if (result.getNombre() == null) {
                contador++;
            }

            out.success(Response.OK.getCode(), Response.OK.getKey(), contador);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    //------- Datos Faltantes de Contacto -------
    @GetMapping(value = "/conteoDatosContacto/{persona_id}")
    public ResponseEntity<OutputEntity<Integer>> conteoDatosFaltantesContacto(@PathVariable("persona_id") Integer persona_id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            int contador = 0;
            Contacto result = personaService.findContacto(persona_id);
            if (result == null) {
                contador++;
            } else {
                if (result.getMail() == null) {
                    contador++;
                }
                if (result.getNombre_emergencias() == null) {
                    contador++;
                }
                if (result.getTelefono_celular() == null) {
                    contador++;
                }
                if (result.getTelefono_emergencias() == null) {
                    contador++;
                }
                if (result.getTelefono_particular() == null) {
                    contador++;
                }
            }
            //System.out.println("Contador contacto " + contador);
            out.success(Response.OK.getCode(), Response.OK.getKey(), contador);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //------- Datos Faltantes de Dirección -------
    @GetMapping(value = "/conteoDatosDireccion/{persona_id}")
    public ResponseEntity<OutputEntity<Integer>> conteoDatosFaltantesDireccion(@PathVariable("persona_id") Integer persona_id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Direccion result = personaService.findDireccion(persona_id);
            int contador = 0;
            if (result == null) {
                contador++;
            } else {
                if (result.getCalle() == null) {
                    contador++;
                }
                if (result.getColonia_id() == null) {
                    contador++;
                }
                if (result.getNum_exterior() == null) {
                    contador++;
                }
                if (result.getNum_interior() == null) {
                    contador++;
                }

            }
            out.success(Response.OK.getCode(), Response.OK.getKey(), contador);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
     //------- Datos Faltantes de Licencia -------
    @GetMapping(value = "/conteoDatosLicencia/{persona_id}")
    public ResponseEntity<OutputEntity<Integer>> conteoDatosFaltantesLicencia(@PathVariable("persona_id") Integer persona_id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Licencia result = personaService.findLicencia(persona_id);
            int contador = 0;
            if (result == null) {
                contador++;
            } else {
                if (result.getNum_licencia() == null) {
                    contador++;
                }
                if (result.getTipo_licencia_id() == null) {
                    contador++;
                }
                if (result.getFin_vig_licencia() == null) {
                    contador++;
                }
                if (result.getInicio_vig_licencia() == null) {
                    contador++;
                }
            }
//            System.out.println("Contador " + contador);
            out.success(Response.OK.getCode(), Response.OK.getKey(), contador);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
     //------- Datos Faltantes de Beneficiarios -------
    @GetMapping(value = "/conteoDatosBeneficiarios/{trabajador_id}")
    public ResponseEntity<OutputEntity<Integer>> conteoDatosFaltantesBeneficiarios(@PathVariable("trabajador_id") Integer trabajador_id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            List<Beneficiario> result = personaService.findBeneficiario(trabajador_id);
            int contador = 0;
            
            if (result.isEmpty()) {
                contador++;
            } 
           
            out.success(Response.OK.getCode(), Response.OK.getKey(), contador);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarBeneficiarioPrimarios/{id_persona}")
    public ResponseEntity<Beneficiario_carta> listarBeneficiarioPrimario(@RequestBody @PathVariable("id_persona") Integer id_persona) {
        try {
            List<Beneficiario_carta> result = personaService.findBeneficiarioCarta(id_persona);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/conteoDatosBeneficiariosCarta/{persona_id}")
    public ResponseEntity<OutputEntity<Integer>> conteoDatosFaltantesBeneficiariosCarta(@PathVariable("persona_id") Integer persona_id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            List<Beneficiario_carta> result = personaService.findBeneficiarioCarta(persona_id);
            int contador = 0;
            
            if (result.isEmpty()) {
                contador++;
            } 
           
            out.success(Response.OK.getCode(), Response.OK.getKey(), contador);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
       //------- Datos Faltantes de Médico-------
    @GetMapping(value = "/conteoDatosMedicos/{persona_id}")
    public ResponseEntity<OutputEntity<Integer>> conteoDatosFaltantesMedicos(@PathVariable("persona_id") Integer persona_id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Medico result = personaService.findMedico(persona_id);
            int contador = 0;
            
            if (result == null) {
                contador++;
            } else {
                if (result.getAltura() == null) {
                    contador++;
                }
                if (result.getPeso() == null) {
                    contador++;
                }
                if (result.getTipo_sangre_id() == null) {
                    contador++;
                }
                if (result.getEnfermedades_si_no()== null) {
                    contador++;
                }
                if (result.getAlergias_si_no() == null) {
                    contador++;
                }
                if (result.getMedico_cabecera() == null) {
                    contador++;
                }
                if (result.getTelefono_medico()== null) {
                    contador++;
                }
                if (result.getClinica() == null) {
                    contador++;
                }
            }
//            System.out.println("Contador " + contador);
            out.success(Response.OK.getCode(), Response.OK.getKey(), contador);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Buscar persona por ID datos generales entidad Persona
    @GetMapping(value = "/buscarLicencia/{persona_id}")
    public ResponseEntity<OutputEntity<Licencia>> buscarLicencia(@PathVariable("persona_id") Integer persona_id) {
        OutputEntity<Licencia> out = new OutputEntity<>();
        try {
            Licencia result = personaService.findLicencia(persona_id);

            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/guardarLicenciaPersona")
    public ResponseEntity<OutputEntity<String>> guardarLicenciaPersona(@RequestBody Licencia licencia) {
        OutputEntity<String> out = new OutputEntity<>();
        try {

            personaService.saveLicencia(licencia);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Licencia actualizada");
            //out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "");
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @PostMapping(value = "/editarPersonaLicencia/{persona_id}")
    public ResponseEntity<OutputEntity<String>> editarPersonaLicencia(@RequestBody LicenciaDTO licencia, @PathVariable Integer persona_id) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            personaService.editarLicenciaPersona(licencia, persona_id);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Licencia actualizada");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //************************* CONTROLADORES DE MEDICO *****************************************************
    //******************** GUARDAR DATOS MEDICOS DE PERSONA ************************
    @PostMapping(value = "/guardarMedicoPersona")
    public ResponseEntity<OutputEntity<String>> guardarMedicoPersona(@RequestBody Medico medico) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            personaService.saveMedico(medico);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos medicos guardados con éxito");
            //out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "");
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //******************** EDITAR DATOS MEDICOS DE PERSONA ************************
    @PostMapping(value = "/editarMedicoPersona/{persona_id}")
    public ResponseEntity<OutputEntity<String>> editarMedicoPersona(@RequestBody MedicoDTO medico, @PathVariable Integer persona_id) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            personaService.editarMedico(medico, persona_id);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos medicos modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Buscar contacto por Id_Persona 
    @GetMapping(value = "/buscarMedico/{persona_id}")
    public ResponseEntity<OutputEntity<Medico>> buscarMedicoPersona(@PathVariable("persona_id") Integer persona_id) {
        OutputEntity<Medico> out = new OutputEntity<>();
        try {
            Medico result = personaService.findMedico(persona_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //************************* CONTROLADORES DE BENEFICIARIO *****************************************************
    //---------------- Guardar un beneficiario para trabajador --------------------
    @SystemControllerLog(operation = "guardarBeneficiarioPersona", type = "Guardó los datos de la direccion de la persona") //Log de quien ejecuta el metodo
    @PostMapping(value = "/guardarBeneficiarioPersona")
    public ResponseEntity<OutputEntity<String>> guardarBeneficiarioPersona(@RequestBody Beneficiario beneficiario) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            personaService.saveBeneficiario(beneficiario);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Los datos han sido guardados con éxito");
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //-------------Buscar los beneficiarios de cada persona mediante "persona_id"----------------------------
    @GetMapping(value = "/buscarBeneficiario/{trabajador_id}")
    public ResponseEntity<OutputEntity<List<Beneficiario>>> buscarBeneficiarioPersona(@PathVariable("trabajador_id") Integer trabajador_id) {
        OutputEntity<List<Beneficiario>> out = new OutputEntity<>();
        try {
            List<Beneficiario> result = personaService.findBeneficiario(trabajador_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //--------------- Editar los datos de un beficiario mediante el "id_beneficiario"------------------------
    @SystemControllerLog(operation = "editarPersonaBeneficiario", type = "Editó los datos de la direccion de la persona") //Log de quien ejecuta el metodo
    @PostMapping(value = "/editarPersonaBeneficiario/{id_beneficiario}")
    public ResponseEntity<OutputEntity<String>> editarPersonaBeneficiario(@RequestBody BeneficiarioDTO beneficiario, @PathVariable Integer id_beneficiario) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            personaService.editarBeneficiario(beneficiario, id_beneficiario);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Los datos han sido modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Guardar varios registros (beneficiarios) 
    @SystemControllerLog(operation = "guardarPersonasBeneficiario", type = "Guardó los datos de la direccion de la persona") //Log de quien ejecuta el metodo
    @PostMapping(value = "/guardarPersonasBeneficiario")
    public ResponseEntity<OutputEntity< List<Beneficiario>>> guardarPersonaBeneficiarios(@RequestBody List<Beneficiario> beneficiario) { //probar
        OutputEntity<List<Beneficiario>> out = new OutputEntity<>();
//        System.out.println("No. De Beneficiario " + beneficiario.size());
//            for(Beneficiario user : beneficiario) {
//            System.out.println(user.getApellido_materno());
//        }
        try {
            List<Beneficiario> iterable = personaService.saveAll(beneficiario);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), iterable);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }

    }
    
    //Elimina un BENEFICIARIO --> Cambia el estatus del beneficiario de 1 a 0   
    @SystemControllerLog(operation = "editarEstatusaBeneficiario", type = "Modifica el estatus de un beneficiario") //Log de quien ejecuta el metodo
    @PostMapping(value = "/editarEstatusBeneficiario/{id_beneficiario}")
    public ResponseEntity<OutputEntity<String>> editarEstatusBeneficiario(@RequestBody BeneficiarioEstatusDTO beneficiario, @PathVariable Integer id_beneficiario) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            personaService.eliminarBeneficiario(beneficiario, id_beneficiario);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Los datos han sido modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    
//************************* CONTROLADORES DE CARTA DESIGNATARIA BENEFICIARIOS *****************************************************
    //---------------- Guardar un beneficiario para persona --------------------
    @SystemControllerLog(operation = "guardarBeneficiarioPersonaCarta", type = "Guardó los datos de la direccion de la persona") //Log de quien ejecuta el metodo
    @PostMapping(value = "/guardarBeneficiarioPersonaCarta")
    public ResponseEntity<OutputEntity<String>> guardarBeneficiarioPersonaCarta(@RequestBody Beneficiario_carta beneficiario_carta) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            personaService.saveBeneficiarioCarta(beneficiario_carta);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Los datos han sido guardados con éxito");
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    
    
    //-------------Buscar los beneficiarios de cada persona mediante "persona_id"----------------------------
    @GetMapping(value = "/buscarBeneficiarioCarta/{persona_id}")
    public ResponseEntity<OutputEntity<List<Beneficiario_carta>>> buscarBeneficiarioPersonaCarta(@PathVariable("persona_id") Integer persona_id) {
        OutputEntity<List<Beneficiario_carta>> out = new OutputEntity<>();
        try {
            List<Beneficiario_carta> result = personaService.findBeneficiarioCarta(persona_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
    }
    
    @SystemControllerLog(operation = "editarPersonaBeneficiarioCarta", type = "Editó los datos de la direccion de la persona") //Log de quien ejecuta el metodo
    @PostMapping(value = "/editarPersonaBeneficiarioCarta/{id_beneficiario_carta}")
    public ResponseEntity<OutputEntity<String>> editarPersonaBeneficiarioCarta(@RequestBody Beneficiario_cartaDTO beneficiario_carta, @PathVariable Integer id_beneficiario_carta) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            personaService.editarBeneficiarioCarta(beneficiario_carta, id_beneficiario_carta);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Los datos han sido modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
     //Guardar varios registros (beneficiarios) 
    @SystemControllerLog(operation = "guardarPersonasBeneficiarioCarta", type = "Guardó los datos de la direccion de la persona") //Log de quien ejecuta el metodo
    @PostMapping(value = "/guardarPersonasBeneficiarioCarta")
    public ResponseEntity<OutputEntity< List<Beneficiario_carta>>> guardarPersonaBeneficiariosCarta(@RequestBody List<Beneficiario_carta> beneficiario_carta) { //probar
        OutputEntity<List<Beneficiario_carta>> out = new OutputEntity<>();
//        System.out.println("No. De Beneficiario " + beneficiario.size());
//            for(Beneficiario user : beneficiario) {
//            System.out.println(user.getApellido_materno());
//        }
        try {
            List<Beneficiario_carta> iterable = personaService.saveAllCarta(beneficiario_carta);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), iterable);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }

    }
    @SystemControllerLog(operation = "editarEstatusaBeneficiarioCarta", type = "Modifica el estatus de un beneficiario") //Log de quien ejecuta el metodo
    @PostMapping(value = "/editarEstatusBeneficiarioCarta/{id_beneficiario_carta}")
    public ResponseEntity<OutputEntity<String>> editarEstatusBeneficiarioCarta(@RequestBody Beneficiario_cartaEstatusDTO beneficiario_carta, @PathVariable Integer id_beneficiario_carta) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            personaService.eliminarBeneficiarioCarta(beneficiario_carta, id_beneficiario_carta);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Los datos han sido modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    @GetMapping(value = "/buscarBeneficiarioCartaSec/{persona_id}")
    public ResponseEntity<OutputEntity<List<Beneficiario_carta>>> buscarBeneficiarioPersonaCartaSec(@PathVariable("persona_id") Integer persona_id) {
        OutputEntity<List<Beneficiario_carta>> out = new OutputEntity<>();
        try {
            List<Beneficiario_carta> result = personaService.findBeneficiarioCartaSec(persona_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
    }
}
