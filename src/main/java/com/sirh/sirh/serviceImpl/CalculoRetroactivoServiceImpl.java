/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.ConceptosRetroActJubDTO;
import com.sirh.sirh.entity.Tmp_Retro_Jubilados;
import com.sirh.sirh.repository.Pagos_4Repository;
import com.sirh.sirh.repository.Tmp_MovimientosRepository;
import com.sirh.sirh.repository.Tmp_Retro_JubiladosRepository;
import com.sirh.sirh.repository.TrabajadorRepository;
import com.sirh.sirh.service.CalculoRetroactivoService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rroscero23
 */
@Service
public class CalculoRetroactivoServiceImpl implements CalculoRetroactivoService {

    @Autowired
    private Pagos_4Repository pagos_4Repository;

    @Autowired
    private Tmp_Retro_JubiladosRepository tmp_Retro_JubiladosRepository;

    @Autowired
    private Tmp_MovimientosRepository tmp_MovimientosRepository;

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Override
    public void calculaRetroactivoJubilados(List<String> expedientesDescartados, Integer periodoInicio,
            Integer periodoFin, Double porcentajeIncrPension, Double porcentajeIncrAyudaTransp,
            Double porcentajeIncrVales, Double porcentajeIncrPensAtrasada) {
        Boolean anioActualProcesado = tmp_Retro_JubiladosRepository.seProcesoAnioActual();
        if (anioActualProcesado) {
            throw new RuntimeException("Ya se procesó el año actual, existe al menos un registro con el año actual");
        }
        //Convertir los expedientes a ids de trabajadores
        List<Integer> trabajadoresDescartados = trabajadorRepository.obtenerIdTrabajadorConExpediente(expedientesDescartados);
        // Obtener los resultados de las incidencias
        List<ConceptosRetroActJubDTO> incidencias = pagos_4Repository.conceptosRetroactivoJubilados(periodoInicio, periodoFin, trabajadoresDescartados);
        // Obtener los resultados de los cálculos  
        List<ConceptosRetroActJubDTO> calculos = pagos_4Repository.calculosRetroactivoJubilados(periodoInicio, periodoFin, trabajadoresDescartados);
        // Verificar si incidencias o cálculos están vacíos
        if (incidencias.isEmpty() || calculos.isEmpty()) {
            String mensajeError = incidencias.isEmpty()
                    ? "No se encontraron incidencias para el período dado."
                    : "No se encontraron cálculos para el período dado.";
            throw new RuntimeException(mensajeError);
        }
        // Unificar incidencias y cálculos en un solo proceso
        List<ConceptosRetroActJubDTO> informacionCompleta = new ArrayList<>();
        informacionCompleta.addAll(incidencias);
        informacionCompleta.addAll(calculos);
        //Se ordenan las incidencias obtenidas por el id del trabajador
        Collections.sort(informacionCompleta, Comparator.comparing(ConceptosRetroActJubDTO::getTrabajador_id));
        //Procesa y guarda las incidencias en tmp_retro_jubilados por id_trabajador
        procesarYGuardarValores(informacionCompleta, porcentajeIncrPension, porcentajeIncrAyudaTransp, porcentajeIncrVales, porcentajeIncrPensAtrasada);
    }

    @Override
    public void insertarEnTmpMovimientos() {
        //Se verifica que ya existan cálculos procesados en tmp_retro_movimientos
        Boolean anioActualProcesado = tmp_Retro_JubiladosRepository.seProcesoAnioActual();
        //Se verifica que no existan movimientos de la nómina 4 (Jubilados) para no duplicar registros
        Boolean registroJubilados = tmp_MovimientosRepository.existe_movimiento_retro_jubilados();
        if (!anioActualProcesado) {
            throw new RuntimeException("No se ha procesado el año actual, no existen registros con el año actual, considere primero generar los cálculos");
        }
        if (registroJubilados) {
            throw new RuntimeException("Ya se generaron movimientos de retroactivo para jubilados, existe al menos un registro con el periodo 92");
        }
        int anio = LocalDate.now().getYear();
        //Se adiciona la incidencia 02 (Días laborados)
        tmp_MovimientosRepository.adicionarIncidencia2RetroactivoJubilados(anio);
        //Se adiciona la incidenncia 29(Pensión Jubilatoria)
        tmp_MovimientosRepository.adicionarIncidencia29RetroactivoJubilados(anio);
        //Se adiciona la incidenncia 30(Vales de Despensa)
        tmp_MovimientosRepository.adicionarIncidencia30RetroactivoJubilados(anio);
        //Se adiciona la incidenncia 33(Ayuda Transporte)
        tmp_MovimientosRepository.adicionarIncidencia33RetroactivoJubilados(anio);
        //Se adiciona la incidenncia 251(Pensión Jubilatoria Atrasada)
        tmp_MovimientosRepository.adicionarIncidencia251RetroactivoJubilados(anio);
    }

    @Override
    public List<ConceptosRetroActJubDTO> conceptosRetroactivoJubilados(Integer periodoInicial, Integer periodoFinal, List<Integer> trabajadoresDescartados) {
        return pagos_4Repository.conceptosRetroactivoJubilados(periodoInicial, periodoFinal, trabajadoresDescartados);
    }

    @Override
    public List<ConceptosRetroActJubDTO> calculosRetroactivoJubilados(Integer periodoInicial, Integer periodoFinal, List<Integer> trabajadoresDescartados) {
        return pagos_4Repository.calculosRetroactivoJubilados(periodoInicial, periodoFinal, trabajadoresDescartados);
    }

    private void procesarYGuardarValores(List<ConceptosRetroActJubDTO> conceptos,
            Double porcentajePension,
            Double porcentajeAyudaTrans,
            Double porcentajeVales,
            Double porcentajePensionAtrasada) {
        // Dividir los porcentajes recibidos entre 100 para obtener valores decimales
        double porcentajePensionDecimal = porcentajePension / 100.0;
        double porcentajeAyudaTransDecimal = porcentajeAyudaTrans / 100.0;
        double porcentajeValesDecimal = porcentajeVales / 100.0;
        double porcentajePensionAtrasadaDecimal = porcentajePensionAtrasada / 100.0;
        int anio = LocalDate.now().getYear();
        int periodoInicial = anio * 100 + 2;
        final double FACTOR_INICIAL = 10.0 / 15.0;
        final double FACTOR = 1.0;
        // Mapa para almacenar los valores acumulados por trabajador y periodo
        Map<String, Double[]> acumuladosPorTrabajador = new HashMap<>();
        for (ConceptosRetroActJubDTO dto : conceptos) {
            double valor = dto.getValor();
            int periodo = dto.getPeriodo_aplicacion();
            int catIncidenciaId = dto.getCat_incidencia_id();
            int trabajadorId = dto.getTrabajador_id();
            double factor = periodo == periodoInicial ? FACTOR_INICIAL : FACTOR;
            double valorAjustado = BigDecimal.valueOf(valor * factor).setScale(2, RoundingMode.HALF_UP).doubleValue();
            double valorAjustadoVales = BigDecimal.valueOf(valor / 4).setScale(2, RoundingMode.HALF_UP).doubleValue();
            // Clave para identificar el registro único de trabajador y periodo
            String clave = trabajadorId + "-" + periodo;
            // Obtener el arreglo de valores acumulados para la clave, o inicializarlo si no existe
            Double[] valoresIncidencias = acumuladosPorTrabajador.get(clave);
            if (valoresIncidencias == null) {
                //A partir de la posicion 12 (12-1) se colocarán los totales calculados del porcentaje de incremento
                valoresIncidencias = new Double[17];
                Arrays.fill(valoresIncidencias, 0.0);
            }
            switch (catIncidenciaId) {
                case 40: // Clave 2 de nómina
                    valoresIncidencias[0] += valorAjustado;
                    break;
                case 63: // Clave 29 de nómina (incidencia)
                case 245: // Clave 29 de nómina (cálculo)
                    if (periodo == periodoInicial) {
                        valoresIncidencias[5] += valorAjustado;
                        //Se multiplica por el porcentaje de incremento
                        valoresIncidencias[11] += redondearValor(valorAjustado * porcentajePensionDecimal);
                    }
                    break;
                case 64: // Clave 30 de nómina (incidencia)
                    if (periodo == periodoInicial) {
                        valoresIncidencias[1] += valorAjustadoVales;
                        //Se multiplica por el porcentaje de incremento
                        valoresIncidencias[12] += redondearValor(valorAjustadoVales * porcentajeValesDecimal);
                    } else {
                        valoresIncidencias[1] += valorAjustado;
                        //Se multiplica por el porcentaje de incremento
                        valoresIncidencias[12] += redondearValor(valorAjustado * porcentajeValesDecimal);
                    }
                    break;
                case 246: // Clave 30 de nómina (cálculo)
                    if (periodo == periodoInicial) {
                        valoresIncidencias[6] += valorAjustadoVales;
                        //Se multiplica por el porcentaje de incremento
                        valoresIncidencias[13] += redondearValor(valorAjustadoVales * porcentajeValesDecimal);
                    } else {
                        valoresIncidencias[6] += valorAjustado;
                        //Se multiplica por el porcentaje de incremento
                        valoresIncidencias[13] += redondearValor(valorAjustadoVales * porcentajeValesDecimal);
                    }
                    break;
                case 112: // Clave 251 de nómina (incidencia)
                    if (periodo != periodoInicial) {
                        valoresIncidencias[4] += valorAjustado;
                        valoresIncidencias[14] += redondearValor(valorAjustado * porcentajePensionAtrasadaDecimal);
                    }
                    break;
                case 286: // Clave 251 de nómina (cálculo)
                    if (periodo != periodoInicial) {
                        valoresIncidencias[9] += valorAjustado;
                        valoresIncidencias[15] += redondearValor(valorAjustado * porcentajePensionAtrasadaDecimal);
                    }
                    break;
                default:
                    throw new RuntimeException("Incidencia con id " + catIncidenciaId + " no es válida");
            }
            // Actualizar el mapa con los valores acumulados
            acumuladosPorTrabajador.put(clave, valoresIncidencias);
        }
        // Guardar los valores acumulados en la base de datos
        for (Map.Entry<String, Double[]> entry : acumuladosPorTrabajador.entrySet()) {
            String clave = entry.getKey();
            Integer trabajadorId = Integer.parseInt(clave.split("-")[0]);
            Integer periodo = Integer.parseInt(clave.split("-")[1]);
            Double[] valoresIncidencias = entry.getValue();
            // Obtener el sueldo y puestoId correspondientes al trabajador y periodo
            ConceptosRetroActJubDTO dto = conceptos.stream()
                    .filter(c -> c.getTrabajador_id().equals(trabajadorId) && c.getPeriodo_aplicacion().equals(periodo))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Datos no encontrados para trabajador " + trabajadorId + " y periodo " + periodo));
            double sueldo = dto.getSueldo_mensual();
            int puestoId = dto.getId_puesto();
            // Guardar el registro en la base de datos
            guardarRegistrosTmpRetroJubilados(trabajadorId, puestoId, periodo, sueldo, valoresIncidencias);
        }
        // Llamar a la función para insertar en tmp_movimientos todos los totales almacenados al terminar el ciclo for
        //insertarEnTmpMovimientos();
    }

    private double redondearValor(double valor) {
        return BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    // Guarda la información de las incidencias periodo a periodo por trabajador en la tabla tmp_retro_jubilados
    private void guardarRegistrosTmpRetroJubilados(Integer trabajadorId,
            Integer puestoId,
            Integer periodo,
            Double sueldo,
            Double[] valoresIncidencias) {
        Tmp_Retro_Jubilados tmpRetroJubilados = new Tmp_Retro_Jubilados();
        tmpRetroJubilados.setTrabajador_id(trabajadorId);
        tmpRetroJubilados.setPuesto_id(puestoId);
        tmpRetroJubilados.setPeriodo_id(periodo);
        tmpRetroJubilados.setSueldo(sueldo);
        // Asignar valores de incidencias
        tmpRetroJubilados.setInci_02(valoresIncidencias[0]);
        tmpRetroJubilados.setInci_30(valoresIncidencias[1]);
        tmpRetroJubilados.setInci_33(valoresIncidencias[2]);
        tmpRetroJubilados.setInci_44(valoresIncidencias[3]);
        tmpRetroJubilados.setInci_251(valoresIncidencias[4]);
        tmpRetroJubilados.setP_29(valoresIncidencias[5]);
        tmpRetroJubilados.setP_30(valoresIncidencias[6]);
        tmpRetroJubilados.setP_33(valoresIncidencias[7]);
        tmpRetroJubilados.setP_44(valoresIncidencias[8]);
        tmpRetroJubilados.setP_251(valoresIncidencias[9]);
        tmpRetroJubilados.setP_76(valoresIncidencias[10]);
        // Asignar el año actual
        tmpRetroJubilados.setAnio(LocalDate.now().getYear());
        //Se guardan los porcentajes de incremento por clave 
        tmpRetroJubilados.setRetro_cve_29(valoresIncidencias[11]);
        tmpRetroJubilados.setRetro_cve_30(valoresIncidencias[12]);
        //No aplica actualmente
        tmpRetroJubilados.setRetro_cve_33(0.0);
        tmpRetroJubilados.setRetro_cve_44(0.0);
        tmpRetroJubilados.setRetro_cve_76(0.0);
        tmpRetroJubilados.setRetro_cve_251(valoresIncidencias[14]);
        // Guardar en la base de datos
        tmp_Retro_JubiladosRepository.save(tmpRetroJubilados);
    }

}
