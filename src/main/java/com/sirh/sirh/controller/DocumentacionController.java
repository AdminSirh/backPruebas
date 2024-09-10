/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.entity.Cat_Tipo_Documento;
import com.sirh.sirh.entity.Documentacion;
import com.sirh.sirh.service.CatalogosService;
import com.sirh.sirh.service.DocumentacionService;
import com.sirh.sirh.service.PersonaService;
import com.sirh.sirh.util.Response;
import com.sirh.sirh.util.MediaTypeUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import sirh.sirh.exception.OutputEntity;

/**
 *
 * @author jarellano22
 */
@RestController
@RequestMapping("documentacion")
public class DocumentacionController {

    @Autowired
    DocumentacionService documentacionService;
    @Autowired
    PersonaService personaService;
    @Autowired
    CatalogosService catalogosService;
    private static final String DIRECTORY = "documentos/personas";
    private static final String AYUDA = "documentos/ayuda";
    @Autowired
    private ServletContext servletContext;
    
    @Autowired
    private RestTemplate restTemplate;
    
    String ruta = "src/main/resources/static/";

    File directorio = new File(ruta);
    
    public void rutas() {
        if (!(directorio.exists() && directorio.isDirectory())) {
            ruta = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        }
    }
    
    @PostMapping(value = "/guardarDocumentacion/{tipo_documento_id}/{persona_id}")
    public ResponseEntity<String> enviarDocumento(@PathVariable Integer tipo_documento_id,@PathVariable Integer persona_id,@RequestParam("file") MultipartFile file) throws IOException {
        
        // Cargar el documento en un objeto File
        File documento = convertirMultipartFileAFile(file);
        Integer id_persona = persona_id;
        Integer id_tipo_documento = tipo_documento_id;
        // Crear una solicitud HTTP POST con el documento adjunto
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("documento", new FileSystemResource(documento));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Enviar la solicitud al proyecto receptor
        ResponseEntity<String> response = restTemplate.exchange("http://10.19.1.242:8080/documentos/documentacion/recibirDocumento/" + id_persona + "/" + id_tipo_documento, HttpMethod.POST, requestEntity, String.class);
        // Eliminar el documento de la raiz del proyecto para que no se duplique
        documento.delete();
        // Devolver la respuesta del proyecto receptor
        return response;
    }

    private File convertirMultipartFileAFile(MultipartFile multipartFile) throws IOException {
        rutas();
        File file = new File(ruta + multipartFile.getOriginalFilename()); // Crea un archivo temporal
        
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
            
        } catch (IOException e) {
            // Manejar la excepción apropiadamente
            // Eliminar el archivo temporal si ocurre un error
            file.delete();
            throw e;
        }
        return file;
    }
     //Enviar RUTA del Documento
    @GetMapping(value = "/buscarDocumentoCargado/{id_documentacion}")
    public ResponseEntity<ByteArrayResource> buscarDocumentoCargado(@PathVariable Integer id_documentacion) {
        String url = "http://10.19.1.242:8080/documentos/documentacion/mostrarDocumento/" + id_documentacion; // URL del controlador en el otro proyecto

        ResponseEntity<ByteArrayResource> response = restTemplate.exchange(url, HttpMethod.GET, null, ByteArrayResource.class);
        return response;
    }
    // Guarda datos basicos para crear registro de candidato nuevo validado por curp 
//    @PostMapping(value = "/guardarDocumentacion")
//    public ResponseEntity<OutputEntity<Documentacion>> guardarDocumentacion(@ModelAttribute DocumentacionDTO documentacion, @RequestPart(value = "file", required = true) MultipartFile file) {
//        OutputEntity<Documentacion> out = new OutputEntity<>();
//        try {
//            documentacionService.save(documentacion, file);
//
//            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), null);
//            return new ResponseEntity<>(out, out.getCode());
//        } catch (Exception e) {
//            out.error();
//            System.out.println(e);
//            return new ResponseEntity<>(out, out.getCode());
//        }
//    }

    //Lista de los tipos de documentos
    @GetMapping(value = "/listarTiposDocumento")
    public ResponseEntity<OutputEntity<List<Cat_Tipo_Documento>>> listarTiposDocumento() {
        OutputEntity<List<Cat_Tipo_Documento>> out = new OutputEntity<>();
        try {
            List<Cat_Tipo_Documento> result = documentacionService.findAll();
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Lista de los documentos cargados
    @GetMapping(value = "/listarDocumentoCargados/{persona_id}")
    public ResponseEntity<OutputEntity<List<Documentacion>>> listarDocumentoCargados(@RequestBody @PathVariable Integer persona_id) {
        OutputEntity<List<Documentacion>> out = new OutputEntity<>();
        try {
            List<Documentacion> result = documentacionService.documentosCargados(persona_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    //Buscar RUTA del Documento
//    @GetMapping(value = "/buscarDocumentoCargado/{id_documentacion}")
//    public ResponseEntity<ByteArrayResource> buscarDocumentoCargado(@RequestBody @PathVariable Integer id_documentacion, HttpServletResponse response) {
//        
//        try {
//            Optional<Documentacion> documento = documentacionService.verDocumento(id_documentacion);
//            //ByteArrayResource resource = documentacionService.verDocumentosCargados(documento.get().getNombre());
//            MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, documento.get().getNombre());
//            Path path = Paths.get(DIRECTORY + "/" + documento.get().getNombre());
//            //System.out.println(DIRECTORY);
//            byte[] data = Files.readAllBytes(path);
//            ByteArrayResource resource = new ByteArrayResource(data);
//            return ResponseEntity.ok()
//                    // Content-Disposition
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
//                    // Content-Type
//                    .contentType(mediaType) //
//                    // Content-Lengh
//                    .contentLength(data.length) //
//                    .body(resource);
//            ///System.out.println(resource); 
//
//        } catch (IOException e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    //************************ ELIMINAR DOCUMENTO**************************************
    @PostMapping(value = "/eliminarDocumento/{id_documentacion}")
    public ResponseEntity<OutputEntity<String>> eliminarDocumento(@RequestBody @PathVariable("id_documentacion") Integer id_documentacion) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            documentacionService.deleteDocumento(id_documentacion);
            out.success(Response.DELETED.getCode(), Response.DELETED.getKey(), "Documento eliminado correctamente");
            return ResponseEntity.ok(out);
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Buscar RUTA del Documento ayuda
    @GetMapping(value = "/buscarDocumentoAyuda/{nombre}")
    public ResponseEntity<ByteArrayResource> buscarDocumentoAyuda(@RequestBody @PathVariable String nombre, HttpServletResponse response) {
        // String EXTERNAL_FILE_PATH = "documentos/personas/";
        try {
            //Optional<Documentacion> documento = documentacionService.verDocumento(id_documentacion);
            //ByteArrayResource resource = documentacionService.verDocumentosCargados(documento.get().getNombre());
            MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, nombre);
            Path path = Paths.get(AYUDA + "/" + nombre);
            byte[] data = Files.readAllBytes(path);
            ByteArrayResource resource = new ByteArrayResource(data);
            return ResponseEntity.ok()
                    // Content-Disposition
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                    // Content-Type
                    .contentType(mediaType) //
                    // Content-Lengh
                    .contentLength(data.length) //
                    .body(resource);
            ///System.out.println(resource); 

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //---------------------------------------------------------------------->>>>
    @GetMapping(value = "/conteoDocumentos/{persona_id}")
    public ResponseEntity<OutputEntity<Integer>> conteoDocumentosFaltantes(@RequestBody @PathVariable Integer persona_id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            List<Documentacion> result = documentacionService.documentosCargados(persona_id);
            List<Cat_Tipo_Documento> documentos = catalogosService.findDocumentos();
            int contador = 0;
            List<Integer> documentos_Persona = new ArrayList<>();
            Integer idGrado = personaService.idGrado(persona_id);
            if (idGrado != null) {
                
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getCat_tipo_documento().getId_tipo_documento() == 1 ||  result.get(i).getCat_tipo_documento().getId_tipo_documento() == 2 || result.get(i).getCat_tipo_documento().getId_tipo_documento() == 3 || result.get(i).getCat_tipo_documento().getId_tipo_documento() == 4 ||  result.get(i).getCat_tipo_documento().getId_tipo_documento() == 5) {
                        documentos_Persona.add(result.get(i).getCat_tipo_documento().getId_tipo_documento());
                    }
                }

                if (idGrado != 7 && idGrado != 9 && idGrado != 10 ) {
                    // Si el idGrado no es 7, 9 ni 10, entonces verificamos si falta solo el documento con ID 1 al 4
                    if (!documentos_Persona.contains(1) || !documentos_Persona.contains(2) ||!documentos_Persona.contains(3) || !documentos_Persona.contains(4)) {
                        contador = 0;
                    }
                } else {
                    // Si el idGrado es 7, 9 o 10, entonces comparamos el número de documentos faltantes
                    if (documentos.size() != documentos_Persona.size()) {
                        contador = documentos.size() - documentos_Persona.size();
                    }
                }
            }
            out.success(Response.OK.getCode(), Response.OK.getKey(), contador);
            return new ResponseEntity<>(out, out.getCode()); 
            
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   
    @GetMapping(value = "/ObtenGrado/{persona_id}")
    public ResponseEntity<OutputEntity<Integer>> ObtenGrado(@RequestBody @PathVariable Integer persona_id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Integer idGrado = personaService.idGrado(persona_id);
          
            out.success(Response.OK.getCode(), Response.OK.getKey(), idGrado);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
