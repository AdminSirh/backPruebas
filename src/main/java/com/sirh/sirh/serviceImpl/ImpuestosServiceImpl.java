
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.entity.Cat_Impuestos_Nomina;
import com.sirh.sirh.repository.Cat_Impuestos_NominaRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sirh.sirh.service.CatImpuestosNominaService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author nreyes22
 */

@Service
@Transactional
public class ImpuestosServiceImpl implements CatImpuestosNominaService{

    @Autowired
    private Cat_Impuestos_NominaRepository cat_Impuestos_NominaRepository;
    
    //************ SERVICE PARA LISTAR IMPUESTOS ***********
    @Override
    public List<Cat_Impuestos_Nomina> listadoImpuestos() {
        return cat_Impuestos_NominaRepository.findAll();
    }

    //********************** SERVICE PARA GUARDAR UN IMPUESTO *****************
    @Override
    public Cat_Impuestos_Nomina save(Cat_Impuestos_Nomina cat_Impuestos_Nomina) {
       return cat_Impuestos_NominaRepository.save(cat_Impuestos_Nomina);
    }

    //********************************* SERVICE PARA ACTUALIZAR UN IMPUESTO ********************************************
    @Override
    public Cat_Impuestos_Nomina updateImpuestosNomina(Cat_Impuestos_Nomina cat_Impuestos_Nomina, Integer id_impuestosNomina) {
        Cat_Impuestos_Nomina cIN = this.cat_Impuestos_NominaRepository.findById(id_impuestosNomina).get();
        
        cIN.setPeriodo(cat_Impuestos_Nomina.getPeriodo());
        cIN.setLimite_inferior(cat_Impuestos_Nomina.getLimite_inferior());
        cIN.setLimite_superior(cat_Impuestos_Nomina.getLimite_superior());
        cIN.setCuota_fija_porcentaje(cat_Impuestos_Nomina.getCuota_fija_porcentaje());
        cIN.setCuota_fija_dinero(cat_Impuestos_Nomina.getCuota_fija_dinero());
        cIN.setPorcentaje_excedente_limite_inferior(cat_Impuestos_Nomina.getPorcentaje_excedente_limite_inferior());
        cIN.setArticulo(cat_Impuestos_Nomina.getArticulo());
        cIN.setAnio(cat_Impuestos_Nomina.getAnio());
        cIN.setFecha_inicio(cat_Impuestos_Nomina.getFecha_inicio());
        cIN.setFecha_fin(cat_Impuestos_Nomina.getFecha_fin());
        cIN.setOrigen(cat_Impuestos_Nomina.getOrigen());
        cIN.setVigencia(cat_Impuestos_Nomina.getVigencia());
        cIN.setNomina_id(cat_Impuestos_Nomina.getNomina_id());
        cIN.setImpuesto_concepto_id(cat_Impuestos_Nomina.getImpuesto_concepto_id());
        
        return cat_Impuestos_NominaRepository.save(cIN);
    }
 
    //************ SERVICE PARA BUSCAR UN IMPUESTO MEDIANTE EL ID ***********
    @Override
    public Cat_Impuestos_Nomina findOneImpuestoNomina(Integer id_impuestosNomina) {
        return cat_Impuestos_NominaRepository.findById(id_impuestosNomina).get();
    }

    //*************************************** SERVICE PARA IMPORTAR IMPUESTOS DEL EXCEL *************************************************
    @Override
    public void importarImpuestosExcel(MultipartFile archivoExcel,Integer anio, String periodo, Integer impuestoConcepto) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(archivoExcel.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0); //Se obtiene la hoja en la posición 0 del libro.
        int lastRow = worksheet.getPhysicalNumberOfRows(); //Se obtiene el número de la última fila (no vacía) en la hoja de cálculo.
        
        procesarDatosExcel(lastRow,worksheet,anio, periodo, impuestoConcepto);
    }
    
    //********************************************* PROCESAR DATOS DEL EXCEL *************************************************
    private void procesarDatosExcel(Integer lastRow, XSSFSheet worksheet,Integer anio, String periodo, Integer impuesto_concepto) {
        // Se inicia un bucle for que recorre todas las filas en la hoja.
        for (int i = 1; i < lastRow; i++) { 
            XSSFRow row = worksheet.getRow(i);  //Se obtiene la fila actual de la hoja.
            if (row != null) {//Verificar si la fila no es nula
                Cat_Impuestos_Nomina cat_Impuestos_Nomina = new Cat_Impuestos_Nomina();
                
                Integer nomina= 0;
                //Se obtiene el valor de la columna y se asigna a la variable nomina dependiendo de su valor ('V', 'T', 'C', 'J', 'E').
                if (row.getCell(10).getStringCellValue().toUpperCase().equals("V")) {
                    nomina  = 1; // VARIOS
                }else if (row.getCell(10).getStringCellValue().toUpperCase().equals("T")) {
                    nomina  = 2; // TRANSPORTACIÓN
                }else if (row.getCell(10).getStringCellValue().toUpperCase().equals("C")) {
                    nomina  = 3; // CONFIANZA
                }else if (row.getCell(10).getStringCellValue().toUpperCase().equals("J")) {
                    nomina  = 4; // JUBILADOS
                }else if (row.getCell(10).getStringCellValue().toUpperCase().equals("E")) {
                    nomina  = 5; // ESTRUCTURA
                }
                
                // Se convierten las fechas del Excel a un formato de fecha LocalDateTime
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String formattedDate = sdf.format(row.getCell(6).getDateCellValue());
                String formattedDateFin = sdf.format(row.getCell(7).getDateCellValue());

                LocalDate fechaInicio = LocalDate.parse(formattedDate, DateTimeFormatter.ofPattern("yyyy/MM/dd "));
                LocalDate fechaFin = LocalDate.parse(formattedDateFin, DateTimeFormatter.ofPattern("yyyy/MM/dd "));

                //Se establecen los atributos de cat_Impuestos_Nomina con los valores de las celdas correspondientes de la fila actual.
                cat_Impuestos_Nomina.setPeriodo(periodo);
                cat_Impuestos_Nomina.setLimite_inferior(row.getCell(0).getNumericCellValue());
                cat_Impuestos_Nomina.setLimite_superior(row.getCell(1).getNumericCellValue());
                cat_Impuestos_Nomina.setCuota_fija_porcentaje(row.getCell(2).getNumericCellValue());
                cat_Impuestos_Nomina.setCuota_fija_dinero(row.getCell(3).getNumericCellValue());
                cat_Impuestos_Nomina.setPorcentaje_excedente_limite_inferior(row.getCell(4).getNumericCellValue());
                cat_Impuestos_Nomina.setArticulo(String.valueOf(row.getCell(5).getNumericCellValue()));
                cat_Impuestos_Nomina.setAnio(anio);
                cat_Impuestos_Nomina.setFecha_inicio(fechaInicio);
                cat_Impuestos_Nomina.setFecha_fin(fechaFin);
                cat_Impuestos_Nomina.setOrigen(String.valueOf(row.getCell(8).getStringCellValue()));
                cat_Impuestos_Nomina.setVigencia(row.getCell(9).getStringCellValue());
                cat_Impuestos_Nomina.setNomina_id(nomina);
                cat_Impuestos_Nomina.setEstatus(1);
                cat_Impuestos_Nomina.setImpuesto_concepto_id(impuesto_concepto);

                cat_Impuestos_NominaRepository.save(cat_Impuestos_Nomina);
            }
        }
    }
    
    //************ SERVICE PARA CONTAR LOS IMPUESTOS MEDIANTE EL ESTATUS Y AÑO ***********
    @Override
    public Integer findImpuestosStatus(Integer anio) {
        return cat_Impuestos_NominaRepository.findImpuestosStatus(anio);
    }

    //************ SERVICE PARA LISTAR LOS REGISTROS ANUALES CON ESTATUS 1 Y CONCEPTO IGUAL A IMPUESTO ***********
    @Override
    public List<Cat_Impuestos_Nomina> listadoImpuestosAnuales(Integer anio) {
        return cat_Impuestos_NominaRepository.listadoImpuestosAnuales(anio);
    }
    
    //************ SERVICE PARA ACTUALIZAR DE FORMA MASIVA EL ESTATUS DE LOS REGISTROS ANUALES CON ESTATUS 1 ***********
    @Override
    public Cat_Impuestos_Nomina updateStatusImpuestosAnuales(Integer anio) {
        List<Cat_Impuestos_Nomina> registros = cat_Impuestos_NominaRepository.listadoImpuestosAnuales(anio);

        if (!registros.isEmpty()) {
            for (int i = 0; i < registros.size(); i++) { //Itera sobre cada registro de impuesto anual en la lista.
                //Obtiene el registro de impuesto correspondiente a partir de su ID 
                Cat_Impuestos_Nomina cIN = this.cat_Impuestos_NominaRepository.findById(registros.get(i).getId_impuesto()).get();
                cIN.setEstatus(0); // Actualiza el estado del registro de impuesto a "0".
                cat_Impuestos_NominaRepository.save(cIN); // Actualizar el registro
            }
            // Luego de actualizar todos los registros, devolver un nuevo objeto Cat_Impuestos_Nomina.
            return new Cat_Impuestos_Nomina();
        }
        // Si no hay registros para actualizar, devolver un nuevo objeto Cat_Impuestos_Nomina.
        return new Cat_Impuestos_Nomina();
    }

    //********************** SERVICE PARA MODIFICAR EL ESTATUS DE LOS REGISTROS *************************
    @Override
    public Cat_Impuestos_Nomina estatusImpuesto(Integer id_impuestosNomina, Integer activo) {
        Cat_Impuestos_Nomina estatus = cat_Impuestos_NominaRepository.findById(id_impuestosNomina).get();
        estatus.setEstatus(activo);
        return cat_Impuestos_NominaRepository.save(estatus);
    }





  

  
    
}
