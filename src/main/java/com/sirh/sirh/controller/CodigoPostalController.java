
package com.sirh.sirh.controller;

import com.sirh.sirh.entity.Cat_Colonia;
import com.sirh.sirh.service.CatalogosService;
import com.sirh.sirh.util.Response;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sirh.sirh.exception.OutputEntity;

/**
 *
 * @author nreyes22
 */

@RestController
@RequestMapping("codigoPostal")
public class CodigoPostalController {
    
    
    @Autowired
    CatalogosService catalogosService;
    
    // *********** BUSCAR POR CÓDIGO POSTAL **********************
    @GetMapping("/buscarCP/{cod_postal}")
    public ResponseEntity<OutputEntity<List<Cat_Colonia>>> buscarDatosCP(@PathVariable String cod_postal) {
         OutputEntity<List<Cat_Colonia>> out = new OutputEntity<>();
        try {
            List<Cat_Colonia> result = catalogosService.findByCod_Postal(cod_postal);
            
             out.success(Response.OK.getCode(), Response.OK.getKey(),result);
             //System.out.println("result " + result.get(0).getCat_municipio().getDesc_municipio());
            return new ResponseEntity<>(out, out.getCode());
                    } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
