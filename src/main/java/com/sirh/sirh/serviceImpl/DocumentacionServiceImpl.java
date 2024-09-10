/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.DocumentacionDTO;
import com.sirh.sirh.entity.Documentacion;
import com.sirh.sirh.repository.Cat_Tipo_DocumentoRepository;
import com.sirh.sirh.repository.DocumentacionRepository;
import com.sirh.sirh.service.DocumentacionService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.core.io.ByteArrayResource;

/**
 *
 * @author jarellano22
 */
@Service
@Transactional
public class DocumentacionServiceImpl implements DocumentacionService {

    @Autowired
    private DocumentacionRepository documentacionRepository;

    @Autowired
    private Cat_Tipo_DocumentoRepository cat_tipo_documentacionRepository;
    //private final Path rootFolder = Paths.get("documentos/personas/");
    //private static final String DIRECTORY = "documentos/personas";

    //@Autowired
    //ModelMapper mapper;
    @Override
    public Documentacion save(DocumentacionDTO documentacion, MultipartFile file) {
        //Documentacion d = mapper.map(documentacionDTO, Documentacion.class);

        String carga = "documentos/personas/";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String NombreFormateado = Normalizer.normalize(file.getOriginalFilename(), Normalizer.Form.NFD);
                NombreFormateado = NombreFormateado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

                Path path = Paths.get(carga + documentacion.getPersona_id() + "_" + NombreFormateado);
                Files.write(path, bytes);

                Documentacion d = new Documentacion();
                d.setNombre(documentacion.getPersona_id() + "_" + NombreFormateado);//Nombre  
                d.setRuta(carga);//ruta destino
                d.setPersona_id(documentacion.getPersona_id());//tamaño
                d.setCat_tipo_documento(documentacion.getCat_tipo_documento());
                d.setExtencion(FilenameUtils.getExtension(file.getOriginalFilename()));//extension
                d.setTamano(file.getSize());//tamaño
                d.setNombre_real(file.getOriginalFilename());//Nombre real 
                LocalDate datetime = LocalDate.now();
                d.setFecha_captura(datetime);
                d.setFecha_modificacion(datetime);
                return documentacionRepository.save(d);

            } catch (IOException e) {
            }
        }
        return null;
    }

    @Override
    public List findAll() {
        return cat_tipo_documentacionRepository.findAll();
    }

    @Override
    public List<Documentacion> documentosCargados(Integer persona_id) {
        return documentacionRepository.findAllDocumentos(persona_id);
    }

    @Override
    public Optional<Documentacion> verDocumento(Integer persona_id) {
        return documentacionRepository.findById(persona_id);
    }

    @Override
    public ByteArrayResource verDocumentosCargados(String name) {
        //Path rootFolder = Paths.get("documentos/personas/");
        // Path file = rootFolder.resolve(name);
        return null;
    }

    @Override
    public void deleteDocumento(Integer id_documentacion) {
        documentacionRepository.deleteDocumento(id_documentacion);
    }
}
