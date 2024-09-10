/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.service.FirmaService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author oguerrero23
 */
@RestController
@RequestMapping("foto")
public class FotoController {
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    FirmaService firmaService;
    
    String ruta = "src/main/resources/static/";

    File directorio = new File(ruta);
    
    public void rutas() {
        if (!(directorio.exists() && directorio.isDirectory())) {
            ruta = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        }
    }
    
    @PostMapping(value = "/guardarFoto/{trabajador_id}")
    public ResponseEntity<String> enviarFoto(@PathVariable Integer trabajador_id, @RequestParam("foto") MultipartFile file) throws IOException {
        // Cargar el documento en un objeto File
        File foto = convertirMultipartFileAFile(file);
        Integer id_trabajador = trabajador_id;
        // Crear una solicitud HTTP POST con el documento adjunto
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("foto", new FileSystemResource(foto));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Enviar la solicitud al proyecto receptor
        //ResponseEntity<String> response = restTemplate.exchange("http://localhost:8181/foto/recibirFoto/" + id_trabajador , HttpMethod.POST, requestEntity, String.class);
        ResponseEntity<String> response = restTemplate.exchange("http://10.19.1.242:8080/firmas/foto/recibirFoto/" + id_trabajador , HttpMethod.POST, requestEntity, String.class);
        
        // Eliminar el documento de la raiz del proyecto para que no se duplique
        foto.delete();
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
    @GetMapping(value = "/buscarFotoCargada/{trabajador_id}")
    public ResponseEntity<ByteArrayResource> buscarFotoCargada(@PathVariable Integer trabajador_id) {
        String url = "http://10.19.1.242:8080/firmas/foto/mostrarFotoTrabajador/" + trabajador_id; // URL del controlador en el otro proyecto
        //String url = "http://localhost:8181/foto/mostrarFotoTrabajador/" + trabajador_id; // URL del controlador en el otro proyecto
        
        ResponseEntity<ByteArrayResource> response = restTemplate.exchange(url, HttpMethod.GET, null, ByteArrayResource.class);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            // Si la respuesta original del otro controlador es NOT_FOUND, devolver una respuesta de recurso no encontrado
            return ResponseEntity.notFound().build();
        } else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            // Si la respuesta original del otro controlador es INTERNAL_SERVER_ERROR, devolver una respuesta de error del servidor interno
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } else {
            // Si la respuesta es diferente de NOT_FOUND o INTERNAL_SERVER_ERROR, devolver la misma respuesta original
            return response;
        }
    }
}
