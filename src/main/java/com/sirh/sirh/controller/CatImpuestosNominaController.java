
package com.sirh.sirh.controller;

import com.sirh.sirh.entity.Cat_Colonia;
import com.sirh.sirh.entity.Cat_Impuestos_Nomina;
import com.sirh.sirh.service.CatImpuestosNominaService;
import com.sirh.sirh.util.Response;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sirh.sirh.exception.OutputEntity;

/**
 *
 * @author nreyes22
 */

@RestController
@RequestMapping("/impuestosNomina")
public class CatImpuestosNominaController {
    @Autowired
    CatImpuestosNominaService catImpuestosNominaService;
    
    
    // **************** LISTAR REGISTROS DE TABLA IMPUESTO NOMINA *****************************
    @GetMapping(value = "/listarImpuestos")
    public ResponseEntity<Cat_Impuestos_Nomina> listarImpuestos() {
        try {
            List<Cat_Impuestos_Nomina> result = catImpuestosNominaService.listadoImpuestos();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    // **************** GUARDAR REGISTRO DE IMPUESTO NOMINA *****************************
     @PostMapping(value = "/guardarCatImpuestosNomina")
    public ResponseEntity<OutputEntity<Cat_Impuestos_Nomina>> saveColonia(@RequestBody Cat_Impuestos_Nomina catImpuestosNomina) {
        OutputEntity<Cat_Impuestos_Nomina> out = new OutputEntity<>();
        try {
            catImpuestosNominaService.save(catImpuestosNomina);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), null);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    
    
     // **************** EDITAR CAMPOS DE IMPUESTO NOMINA *****************************
    @PostMapping(value = "/editarCatImpuesto/{id_impuesto}")
    public ResponseEntity<OutputEntity<String>> editarCatImpuesto(@RequestBody Cat_Impuestos_Nomina cat_Impuestos_Nomina, @PathVariable("id_impuesto") Integer id_impuesto) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catImpuestosNominaService.updateImpuestosNomina(cat_Impuestos_Nomina, id_impuesto);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de estructura modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    

//     **************** BUSCAR IMPUESTO NOMINA *****************************
    @GetMapping(value = "/buscarImpuestoNomina/{id_impuesto}")
    public ResponseEntity<OutputEntity<Cat_Impuestos_Nomina>> buscarImpuestoNomina(@PathVariable Integer id_impuesto) {
        OutputEntity<Cat_Impuestos_Nomina> out = new OutputEntity<>();
        try {
            Cat_Impuestos_Nomina result = catImpuestosNominaService.findOneImpuestoNomina(id_impuesto);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // **************** IMPORTAR LOS REGISTROS DEL ARCHIVO DE EXCEL *****************************
    @PostMapping("/importarImpuestosExcel")
    public ResponseEntity<Void> importarImpuestosExcel(@RequestParam("archivo") MultipartFile archivoExcel,@RequestParam("anio") Integer anio,
            @RequestParam("periodo") String periodo,@RequestParam("impuestoConcepto") Integer impuestoConcepto) {
        try {
            catImpuestosNominaService.importarImpuestosExcel(archivoExcel,anio, periodo,impuestoConcepto);
            
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // **************** LISTAR REGISTROS DE TABLA IMPUESTO NOMINA *****************************
    @GetMapping(value = "/buscarImpuestosStatus/{anio}")
    public ResponseEntity<Integer> buscarImpuestosStatus(@PathVariable("anio") Integer anio) {
        try {
            Integer result = catImpuestosNominaService.findImpuestosStatus(anio);
            if (result==null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    //Actualizar datos de Catatalogo Inhabilitado
    @PostMapping("/actualizarStatusImpuestosAnuales/{anio}")
    public ResponseEntity<OutputEntity<String>> updateStatusImpuestosAnuales(@PathVariable Integer anio) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catImpuestosNominaService.updateStatusImpuestosAnuales(anio);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Estatus Actualizado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    
    
    // **************** LISTAR REGISTROS DE TABLA IMPUESTO NOMINA ANUALES *****************************
    @GetMapping(value = "/listarImpuestosAnuales/{anio}")
    public ResponseEntity<Cat_Impuestos_Nomina> listarImpuestosAnuales(@PathVariable Integer anio) {
        try {
            List<Cat_Impuestos_Nomina> result = catImpuestosNominaService.listadoImpuestosAnuales(anio);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }  
    
    // **************** CAMBIAR EL ESTATUS DE LOS IMPUESTOS *****************************
    @GetMapping(value = "/estatusImpuesto/{id_impuesto}/{activo}")
     public ResponseEntity<Cat_Impuestos_Nomina> estatusImpuesto (@RequestBody @PathVariable Integer id_impuesto, @PathVariable Integer activo) {
        try {
            return new ResponseEntity<>(
                    catImpuestosNominaService.estatusImpuesto(id_impuesto, activo), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     
}
