/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.serviceImpl;

import com.backB.backB.DTO.Cat_Si_NoDTO;
import com.backB.backB.entity.Cat_Genero;
import com.backB.backB.entity.Cat_Si_No;
import com.backB.backB.repository.Cat_GeneroRepository;
import com.backB.backB.service.CatalogosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.backB.backB.repository.Cat_Si_NoRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

/**
 *
 * @author rroscero23
 */
@Service
@Transactional
public class CatalogosServiceImpl implements CatalogosService {

    @Autowired
    private Cat_Si_NoRepository cat_Si_NoRepository;
    
    @Autowired
    private Cat_GeneroRepository cat_GeneroRepository;

    @Autowired
    private ModelMapper modelMapper;

    // ******************* CATALOGO SI NO ******************
    @Override
    public List<Cat_Si_NoDTO> findAllSiNo() {
        return cat_Si_NoRepository.findAll().stream().map(cat_Si_No -> modelMapper.map(cat_Si_No, Cat_Si_NoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Si_No> findAllDatosSiNo() { //Listar todos los datos
        return cat_Si_NoRepository.findAll().stream().map(cat_Si_No -> modelMapper.map(cat_Si_No, Cat_Si_No.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Si_No saveSiNo(Cat_Si_No cat_Si_No) {
        cat_Si_No.setEstatus(1);
        return cat_Si_NoRepository.save(cat_Si_No);
    }


    @Override
    public Cat_Si_No updateSiNo(Integer id, Cat_Si_NoDTO cat_Si_No) {
        Cat_Si_No desc = this.cat_Si_NoRepository.findById(id).get();
        Cat_Si_No descripcion = desc;
        descripcion.setDescripcion(cat_Si_No.getDescripcion());
        descripcion.setEstatus(1);
        return cat_Si_NoRepository.save(descripcion);
    }

    @Override
    public Cat_Si_No findOneSiNo(Integer id) {
        return cat_Si_NoRepository.findById(id).get();
    }

    @Override
    public Cat_Si_No estatusSiNo(Integer id, Integer estatus) {
        Cat_Si_No descripcion = cat_Si_NoRepository.findById(id).get();
        descripcion.setEstatus(estatus);
        return cat_Si_NoRepository.save(descripcion);
    }
    
    // ******************* CATALOGO GENERO ******************

    @Override
    public List<Cat_Genero> findAllDatosGenero() { //Listar todos los datos
        return cat_GeneroRepository.findAll().stream().map(cat_GeneroDTO -> modelMapper.map(cat_GeneroDTO, Cat_Genero.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Genero findOneGenero(Integer id) {
        return cat_GeneroRepository.findById(id).get();
    }

    @Override
    public Cat_Genero saveGenero(Cat_Genero cat_Genero) {
        Cat_Genero g = new Cat_Genero();
        g.setDesc_genero(cat_Genero.getDesc_genero());
        g.setEstatus(1);
        return cat_GeneroRepository.save(g);
    }

    @Override
    public Cat_Genero updateGenero(Cat_Genero genero, Integer id_genero) {
        Cat_Genero g = cat_GeneroRepository.findById(id_genero).get();
        g.setDesc_genero(genero.getDesc_genero());
        g.setEstatus(1);
        return cat_GeneroRepository.save(g);
    }

    @Override
    public Cat_Genero cambioEstatusGenero(Integer id_genero, Integer activo) {
        Cat_Genero stat = cat_GeneroRepository.findById(id_genero).get();
        stat.setEstatus(activo);
        return cat_GeneroRepository.save(stat);
    }

}
