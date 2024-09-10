/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.DTO.DocumentacionDTO;
import com.sirh.sirh.entity.Documentacion;
import java.util.List;
import java.util.Optional;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author jarellano22
 */
public interface DocumentacionService {

    public Documentacion save(DocumentacionDTO documentacion, MultipartFile file);

    public List findAll();

    public List<Documentacion> documentosCargados(Integer persona_id);

    public Optional<Documentacion> verDocumento(Integer id_documentacion);

    public ByteArrayResource verDocumentosCargados(String name);

    public void deleteDocumento(Integer id_documentacion);

}
