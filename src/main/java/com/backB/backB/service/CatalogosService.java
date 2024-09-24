/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.backB.backB.service;

import com.backB.backB.DTO.Cat_Si_NoDTO;
import com.backB.backB.entity.Cat_Genero;
import com.backB.backB.entity.Cat_Si_No;
import java.util.List;

/**
 *
 * @author rroscero23
 */
public interface CatalogosService {

    // ********** CATALOGO SINO ****************************************
    public List<Cat_Si_NoDTO> findAllSiNo();

    public List<Cat_Si_No> findAllDatosSiNo(); //Listar todos los datos

    public Cat_Si_No saveSiNo(Cat_Si_No cat_Si_No);

    public Cat_Si_No updateSiNo(Integer id, Cat_Si_NoDTO cat_Si_No);

    public Cat_Si_No estatusSiNo(Integer id, Integer estatus);

    public Cat_Si_No findOneSiNo(Integer id);
    
    // ********** CATALOGO GENERO   *******************
    public List<Cat_Genero> findAllDatosGenero(); //Listar todos los datos

    public Cat_Genero saveGenero(Cat_Genero cat_Genero);
    
    public Cat_Genero findOneGenero(Integer id);

    public Cat_Genero updateGenero(Cat_Genero genero, Integer id_genero);

    public Cat_Genero cambioEstatusGenero(Integer id_genero, Integer activo);

}
