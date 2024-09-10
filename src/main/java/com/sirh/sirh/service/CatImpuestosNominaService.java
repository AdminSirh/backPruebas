
package com.sirh.sirh.service;

import com.sirh.sirh.entity.Cat_Impuestos_Nomina;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author nreyes22
 */
public interface CatImpuestosNominaService {
    
    public List<Cat_Impuestos_Nomina> listadoImpuestos();
    
    public Cat_Impuestos_Nomina save(Cat_Impuestos_Nomina cat_Impuestos_Nomina);
    
    public Cat_Impuestos_Nomina updateImpuestosNomina(Cat_Impuestos_Nomina Cat_Impuestos_Nomina, Integer id_impuestosNomina);
    
    public Cat_Impuestos_Nomina findOneImpuestoNomina(Integer id_impuestosNomina);
    
    public void importarImpuestosExcel(MultipartFile archivoExcel,Integer anio, String periodo, Integer impuestoConcepto) throws IOException;
    
    public Integer findImpuestosStatus(Integer anio);
    
    public List<Cat_Impuestos_Nomina> listadoImpuestosAnuales(Integer anio); 
    
    public Cat_Impuestos_Nomina updateStatusImpuestosAnuales(Integer anio);
    
    public Cat_Impuestos_Nomina estatusImpuesto(Integer id_impuestosNomina, Integer activo);
}
