package com.sirh.sirh.service;

import com.sirh.sirh.DTO.Periodos_PagoDTO;
import com.sirh.sirh.entity.Ciclos_Conceptos;
import com.sirh.sirh.entity.Periodos_Pago;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PeriodosPagoService {

    // ********* PERIODOS DE PAGO ********************* 
    public List<Periodos_Pago> saveAllPeriodos(List<Periodos_PagoDTO> data);

    // ********* Semanas de corte de Tiempo extra confianza ********************* 
    public void saveSemanasCorteFromExcel(MultipartFile file) throws IOException;

    public List<String> findNominasCargadas();

    public List<Periodos_Pago> listAllPeriodos(Integer nomina_id);

    // ********* CICLOS CONCEPTOS ********************
    public Ciclos_Conceptos saveCiclos(Ciclos_Conceptos ciclo);

    public List<Ciclos_Conceptos> findAllCiclos();

    public Ciclos_Conceptos updateCiclos(Ciclos_Conceptos ciclo, Integer id_ciclo);

    public Ciclos_Conceptos findOneCiclo(Integer id_ciclo);

    void importarCiclosExcel(MultipartFile archivoExcel) throws IOException, ParseException;

    public void deleteCiclos();

    //**************PERIODOS DE AGUINALDO **********************
    public List<Periodos_Pago> findPeriodosAguinaldo(Integer id_nomina);

    public List<Object[]> findPeriodosProximosSinPagar();
}
