/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.entity.Propiedades_Nomina;
import java.util.List;

/**
 *
 * @author rroscero23
 */
public interface PropiedadesNominaService {

    public List<Propiedades_Nomina> findAllPropiedadesNomina();

    public List<Propiedades_Nomina> findPropiedadesNomina(Integer nomina_id);

    public Propiedades_Nomina updatePropiedadNomina(Propiedades_Nomina propiedadesNomina, Integer id_propiedad);

    public Propiedades_Nomina findOnePropiedad(Integer id_propiedad);

    public Double findSalMinFiniquito(Integer id_nomina);

    public Double findSalMinimo(Integer id_nomina);

}
