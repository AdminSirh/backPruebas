/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

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
import java.util.List;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;

/**
 *
 * @author jarellano22
 */
public interface PersonaService {

    public List<Persona> personasSinExpediente();

    public Persona save(PersonaDTO persona);

    public Persona existeCurp(String curp);

    public Persona findOne(Integer id);

    public Integer ultimoIdPersona();

    public Persona save(PersonaGeneralesDTO persona, Integer id);

    // ******** Servicios Para Direccion *******
    public Direccion findDireccion(Integer persona_id);

    public Direccion saveDireccion(Direccion persona);

    public Direccion editarDireccionPersona(DireccionDTO direccion, Integer id);

    public Cat_Colonia findOneDireccion(Integer id);

    // ******** Servicios Para Contacto *******
    public Contacto saveContacto(Contacto contacto);

    public Contacto findContacto(Integer persona_id);

    public Contacto editarContacto(ContactoDTO contacto, Integer id_persona);

    // ********* Editar expediente si_no de persona *****************
    public Persona editarPersonaExpediente(PersonaExpedienteDTO persona, Integer id);
    // ******** Servicios Para Licencia *******

    public Licencia findLicencia(Integer persona_id);

    public Licencia saveLicencia(Licencia persona);

    public Licencia editarLicenciaPersona(LicenciaDTO licencia, Integer id);

    // ******** Servicios Para Medico *******
    public Medico saveMedico(Medico medico);

    public Medico findMedico(Integer persona_id);

    public Medico editarMedico(MedicoDTO medico, Integer id_persona);

    // ******** Servicios Para Beneficiario *******
    public Beneficiario saveBeneficiario(Beneficiario beneficiario);

    public List<Beneficiario> findBeneficiario(Integer trabajador_id);

    public Beneficiario findBeneficiarioPersona(Integer trabajador_id);

    public Beneficiario editarBeneficiario(BeneficiarioDTO contacto, Integer id_trabajador);

    public Beneficiario findOneBeneficiario(Integer id);

    public List<Beneficiario> saveAll(List<Beneficiario> productData); //Guardar Varios Registros

    public Beneficiario eliminarBeneficiario(BeneficiarioEstatusDTO beneficiario, Integer id_beneficiario);

   // ******** Servicios Para Beneficiario Carta *******
    public Beneficiario_carta saveBeneficiarioCarta(Beneficiario_carta beneficiario_carta);
    
    public List<Beneficiario_carta> findBeneficiarioCarta(Integer persona_id);
    
    public Beneficiario_carta findBeneficiarioPersonaCarta(Integer persona_id);

    public Beneficiario_carta editarBeneficiarioCarta(Beneficiario_cartaDTO contacto, Integer persona_id);

    public Beneficiario_carta findOneBeneficiarioCarta(Integer id);

    public List<Beneficiario_carta> saveAllCarta(List<Beneficiario_carta> productData); //Guardar Varios Registros

    public Beneficiario_carta eliminarBeneficiarioCarta(Beneficiario_cartaEstatusDTO beneficiario_carta, Integer id_beneficiario_carta);
    ///////////////// Servicios para Beneficiario carta secundarios//////////
    public List<Beneficiario_carta> findBeneficiarioCartaSec(Integer persona_id);
    
    public Beneficiario_carta findBeneficiarioPersonaCartaSec(Integer persona_id);
    
    public Integer idGrado (Integer persona_id);
}
