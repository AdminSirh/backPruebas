/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

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
import com.sirh.sirh.entity.Beneficiario;
import com.sirh.sirh.entity.Beneficiario_carta;
import com.sirh.sirh.entity.Cat_Colonia;
import com.sirh.sirh.entity.Contacto;
import com.sirh.sirh.entity.Direccion;
import com.sirh.sirh.entity.Licencia;
import com.sirh.sirh.entity.Medico;
import com.sirh.sirh.entity.Persona;
import com.sirh.sirh.repository.BeneficiarioRepository;
import com.sirh.sirh.repository.Beneficiario_cartaRepository;
import com.sirh.sirh.repository.Cat_ColoniaRepository;
import com.sirh.sirh.repository.ContactoRepository;
import com.sirh.sirh.repository.DireccionRepository;
import com.sirh.sirh.repository.LicenciaRepository;
import com.sirh.sirh.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.sirh.sirh.repository.PersonaRepository;
import com.sirh.sirh.service.PersonaService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 *
 * @author jarellano22
 */
@Service
@Transactional
public class PersonaServiceImpl implements PersonaService {
    
    @Autowired
    private PersonaRepository personaRepository;
    
    @Autowired
    private DireccionRepository direccionRepository;
    
    @Autowired
    private Cat_ColoniaRepository cat_ColoniaRepository;
    
    @Autowired
    private ContactoRepository contactoRepository;
    
    @Autowired
    private LicenciaRepository licenciaRepository;
    
    @Autowired
    private MedicoRepository medicoRepository;
    
    @Autowired
    private BeneficiarioRepository beneficiarioRepository;
    
    @Autowired
    private Beneficiario_cartaRepository Beneficiario_cartaRepository;
    
    @Override
    public List<Persona> personasSinExpediente() {
        // Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("apellido_paterno"));
        return personaRepository.personasSinExpediente();
    }

    // ********************************************
    // ******** Servicios Para Guardar datos generales  basicos *******
    // ********************************************
    @Override
    public Persona save(PersonaDTO persona) {
        Persona candidato = new Persona();
        candidato.setNombre(persona.getNombre());
        candidato.setApellido_paterno(persona.getApellido_paterno());
        candidato.setApellido_materno(persona.getApellido_materno());
        candidato.setCat_genero(persona.getCat_genero());
        candidato.setCat_estado_civil(persona.getCat_estado_civil());
        candidato.setCurp(persona.getCurp().toUpperCase());
        LocalDate datetime = LocalDate.now();
        candidato.setFecha_captura(datetime);
        candidato.setFecha_modificacion(datetime);
        candidato.setExpediente_si_no(0);
        return personaRepository.save(candidato);
    }

    @Override
    public Persona existeCurp(String curp) {
        return personaRepository.existeCurp(curp);
    }
    
    @Override
    public Persona findOne(Integer id) {
        return personaRepository.findById(id).get();
    }
    
    @Override
    public Integer ultimoIdPersona() {
        return personaRepository.ultimoIdPersona();
    }

    // ********************************************
    // ******** Servicios Para Guardar datos generales completos *******
    // ********************************************
    @Override
    public Persona save(PersonaGeneralesDTO persona, Integer id) {
        
        Persona p = this.personaRepository.findById(id).get();
        Persona candidato = p;
        candidato.setNombre(persona.getNombre());
        candidato.setApellido_paterno(persona.getApellido_paterno());
        candidato.setApellido_materno(persona.getApellido_materno());
        
        candidato.setFecha_nacimiento(persona.getFecha_nacimiento());
        candidato.setCat_estado(persona.getCat_estado_nacimiento());
        
        candidato.setCat_nacionalidad(persona.getCat_nacionalidad());
        candidato.setCat_estudios(persona.getCat_estudios());
        candidato.setMatricula_smn(persona.getMatricula_smn());
        
        candidato.setCurp(persona.getCurp().toUpperCase());
        candidato.setRfc(persona.getRfc().toUpperCase());
        
        candidato.setCat_genero(persona.getCat_genero());
        candidato.setNum_imss(persona.getNum_imss());
        candidato.setCat_estado_civil(persona.getCat_estado_civil());
        candidato.setFecha_matrimonio(persona.getFecha_matrimonio());
        candidato.setCat_hijos_si_no(persona.getHijos_si_no());
        
        candidato.setCat_cargo_admon_pub_si_no(persona.getCargo_admon_pub_si_no());
        candidato.setCat_inhabilitado_admon_pub_si_no(persona.getInhabilitado_admon_pub_si_no());
        candidato.setCat_credito_infonavit_si_no(persona.getCredito_infonavit_si_no());
        
        LocalDate datetime = LocalDate.now();
        candidato.setFecha_modificacion(datetime);
        return personaRepository.save(candidato);
    }
    // ********************************************
    // ******** Servicios Para Direccion *******
    // ********************************************

    @Override
    public Direccion findDireccion(Integer persona_id) {
        return direccionRepository.findDireccion(persona_id);
    }
    
    @Override
    public Direccion saveDireccion(Direccion persona) {
        return direccionRepository.save(persona);
    }
    
    @Override
    public Cat_Colonia findOneDireccion(Integer id) {
        return cat_ColoniaRepository.findById(id).get();
    }
    
    @Override
    public Direccion editarDireccionPersona(DireccionDTO direccion, Integer persona_id) {
        Direccion d = this.direccionRepository.findDireccion(persona_id);
        Direccion candidato = d;
        candidato.setCalle(direccion.getCalle());
        candidato.setColonia_id(direccion.getColonia_id());
        candidato.setNum_exterior(direccion.getNum_exterior());
        candidato.setNum_interior(direccion.getNum_interior());
        return direccionRepository.save(candidato);
    }

    // ********************************************
    // ******** Servicios Para Contacto *******
    // ********************************************
    @Override
    public Contacto saveContacto(Contacto contacto) {
        return contactoRepository.save(contacto);
    }
    
    @Override
    public Contacto findContacto(Integer persona_id) {
        return contactoRepository.findContacto(persona_id);
    }
    
    @Override
    public Contacto editarContacto(ContactoDTO contacto, Integer id_persona) {
        Contacto d = this.contactoRepository.findContacto(id_persona);
        Contacto datos = d;
        
        datos.setMail(contacto.getMail());
        datos.setNombre_emergencias(contacto.getNombre_emergencias());
        datos.setTelefono_emergencias(contacto.getTelefono_emergencias());
        datos.setTelefono_particular(contacto.getTelefono_particular());
        datos.setTelefono_celular(contacto.getTelefono_celular());
        
        return contactoRepository.save(datos);
        
    }

    // ********************************************
    // ******** Servicios Para Expediente *******
    // ********************************************
    @Override
    public Persona editarPersonaExpediente(PersonaExpedienteDTO persona, Integer id) {
        
        Persona p = this.personaRepository.findById(id).get();
        Persona personaExp = p;
        personaExp.setExpediente_si_no(persona.getExpediente_si_no());
        
        return personaRepository.save(personaExp);
    }

    // ********************************************
    // ******** Servicios Para Licencia *******
    // ********************************************
    @Override
    public Licencia findLicencia(Integer persona_id) {
        return licenciaRepository.findLicencia(persona_id);
    }
    
    @Override
    public Licencia saveLicencia(Licencia persona) {
        return licenciaRepository.save(persona);
    }
    
    @Override
    public Licencia editarLicenciaPersona(LicenciaDTO licencia, Integer persona_id) {
        Licencia d = this.licenciaRepository.findLicencia(persona_id);
        Licencia candidato = d;
        candidato.setNum_licencia(licencia.getNum_licencia());
        candidato.setTipo_licencia_id(licencia.getTipo_licencia_id());
        candidato.setInicio_vig_licencia(licencia.getInicio_vig_licencia());
        candidato.setFin_vig_licencia(licencia.getFin_vig_licencia());
        candidato.setPersona_id(licencia.getPersona_id());
        return licenciaRepository.save(candidato);
    }

    // ********************************************
    // ******** Servicios Para Medico ************
    // ********************************************
    @Override
    public Medico saveMedico(Medico medico) {
        return medicoRepository.save(medico);
    }
    
    @Override
    public Medico findMedico(Integer persona_id) {
        return medicoRepository.findMedico(persona_id);
    }
    
    @Override
    public Medico editarMedico(MedicoDTO medico, Integer persona_id) {
        Medico d = this.medicoRepository.findMedico(persona_id);
        Medico datos = d;
        datos.setAltura(medico.getAltura());
        datos.setPeso(medico.getPeso());
        datos.setTipo_sangre_id(medico.getTipo_sangre_id());
        datos.setEnfermedades(medico.getEnfermedades());
        datos.setAlergias(medico.getAlergias());
        datos.setMedico_cabecera(medico.getMedico_cabecera());
        datos.setTelefono_medico(medico.getTelefono_medico());
        //datos.setContacto_caso_accidente(medico.getContacto_caso_accidente());
        datos.setClinica(medico.getClinica());
        datos.setEnfermedades_si_no(medico.getEnfermedades_si_no());
        datos.setAlergias_si_no(medico.getAlergias_si_no());
        //datos.setTelefono_contacto_caso_accidente(medico.getTelefono_contacto_caso_accidente());
        return medicoRepository.save(datos);
    }

// ********************************************
    // ******** Servicios Para Beneficiario *******
    //*********************************************
    @Override
    public Beneficiario saveBeneficiario(Beneficiario beneficiario) {
        return beneficiarioRepository.save(beneficiario);
    }
    
    @Override
    public List<Beneficiario> findBeneficiario(Integer trabajador_id) {
        return beneficiarioRepository.findBeneficiario(trabajador_id);
    }
    
    @Override
    public Beneficiario editarBeneficiario(BeneficiarioDTO beneficiario, Integer id_beneficiario) {
        Beneficiario d = this.beneficiarioRepository.findById(id_beneficiario).get();
        Beneficiario datos = d;
        datos.setNombre(beneficiario.getNombre());
        datos.setApellido_paterno(beneficiario.getApellido_paterno());
        datos.setApellido_materno(beneficiario.getApellido_materno());
        datos.setCat_Parentesco(beneficiario.getCat_Parentesco());
        datos.setCat_tipo_beneficiario(beneficiario.getCat_tipo_beneficiario());
        datos.setPorcentaje(beneficiario.getPorcentaje());
        return beneficiarioRepository.save(datos);
    }
    
    @Override
    public Beneficiario findBeneficiarioPersona(Integer trabajador_id) {
        return beneficiarioRepository.findBeneficiarioPersona(trabajador_id);
    }
    
    @Override
    public Beneficiario findOneBeneficiario(Integer id) {
        return beneficiarioRepository.findById(id).get();
    }
    
    @Override
    public List<Beneficiario> saveAll(List<Beneficiario> productData) {
        return beneficiarioRepository.saveAll(productData);
    }
    
    @Override
    public Beneficiario eliminarBeneficiario(BeneficiarioEstatusDTO beneficiario, Integer id_beneficiario) {
        Beneficiario d = this.beneficiarioRepository.findById(id_beneficiario).get();
        Beneficiario datos = d;
        
        datos.setEstatus(beneficiario.getEstatus());
        return beneficiarioRepository.save(datos);
    }
    // ********************************************
    // ******** Servicios Para Beneficiario Carta *******
    //*********************************************

    @Override
    public Beneficiario_carta saveBeneficiarioCarta(Beneficiario_carta beneficiario_carta) {
        return Beneficiario_cartaRepository.save(beneficiario_carta);
    }
    
    @Override
    public List<Beneficiario_carta> findBeneficiarioCarta(Integer persona_id) {
        return Beneficiario_cartaRepository.findBeneficiarioCarta(persona_id);
    }
    
    @Override
    public Beneficiario_carta findBeneficiarioPersonaCarta(Integer persona_id) {
        return Beneficiario_cartaRepository.findBeneficiarioPersonaCarta(persona_id);
        
    }
    
    @Override
    public Beneficiario_carta editarBeneficiarioCarta(Beneficiario_cartaDTO beneficiario_carta, Integer id_beneficiario_carta) {
        Beneficiario_carta d = this.Beneficiario_cartaRepository.findById(id_beneficiario_carta).get();
        Beneficiario_carta datos = d;
        datos.setNombre(beneficiario_carta.getNombre());
        datos.setApellido_paterno(beneficiario_carta.getApellido_paterno());
        datos.setApellido_materno(beneficiario_carta.getApellido_materno());
        datos.setCat_Parentesco(beneficiario_carta.getCat_Parentesco());
        datos.setTipo_beneficiario_carta_id(beneficiario_carta.getTipo_beneficiario_carta_id());
        datos.setPorcentaje(beneficiario_carta.getPorcentaje());
        return Beneficiario_cartaRepository.save(datos);
    }
    
    @Override
    public Beneficiario_carta findOneBeneficiarioCarta(Integer id) {
        return Beneficiario_cartaRepository.findById(id).get();
    }
    
    @Override
    public List<Beneficiario_carta> saveAllCarta(List<Beneficiario_carta> productData) {
        return Beneficiario_cartaRepository.saveAll(productData);
    }
    
    @Override
    public Beneficiario_carta eliminarBeneficiarioCarta(Beneficiario_cartaEstatusDTO beneficiario_carta, Integer id_beneficiario_carta) {
        Beneficiario_carta d = this.Beneficiario_cartaRepository.findById(id_beneficiario_carta).get();
        Beneficiario_carta datos = d;
        
        datos.setEstatus(beneficiario_carta.getEstatus());
        return Beneficiario_cartaRepository.save(datos);
    }
    
    @Override
    public List<Beneficiario_carta> findBeneficiarioCartaSec(Integer persona_id) {
        return Beneficiario_cartaRepository.findBeneficiarioCartaSec(persona_id);
    }
    
    @Override
    public Beneficiario_carta findBeneficiarioPersonaCartaSec(Integer persona_id) {
        return Beneficiario_cartaRepository.findBeneficiarioPersonaCartaSec(persona_id);
    }
    
    @Override
    public Integer idGrado(Integer persona_id) {
        return personaRepository.idGrado(persona_id);
    }
}
