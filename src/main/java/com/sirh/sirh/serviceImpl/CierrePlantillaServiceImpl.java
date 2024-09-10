package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.PlantillaMensualDTO;
import com.sirh.sirh.DTO.Registro_Plazas_ConsolidadoDTO;
import com.sirh.sirh.DTO.Tmp_ConsolidadoDTO;
import com.sirh.sirh.entity.Registro_Plantilla_Mensual;
import com.sirh.sirh.entity.Registro_Plazas_Consolidado;
import com.sirh.sirh.entity.Tmp_Consolidado;
import com.sirh.sirh.repository.Cat_PlazaRepository;
import com.sirh.sirh.repository.Registro_Plantilla_MensualRepository;
import com.sirh.sirh.repository.Registro_Plazas_ConsolidadoRepository;
import com.sirh.sirh.repository.Tmp_ConsolidadoRepository;
import com.sirh.sirh.repository.Trabajador_plazaRepository;
import com.sirh.sirh.service.CierrePlantillaService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

@Service
public class CierrePlantillaServiceImpl implements CierrePlantillaService {

    @Autowired
    private Cat_PlazaRepository cat_PlazaRepository;

    @Autowired
    private Registro_Plantilla_MensualRepository registro_Plantilla_MensualRepository;

    @Autowired
    private Registro_Plazas_ConsolidadoRepository registro_Plazas_ConsolidadoRepository;

    @Autowired
    private Trabajador_plazaRepository trabajador_plazaRepository;

    @Autowired
    private Tmp_ConsolidadoRepository tmp_ConsolidadoRepository;

    /* Se ejecuta los días 28 cada hora hasta comprobar que sea media noche, esto se realizó así porque no reconoce la hora cuando entra a cron como parametro */
    @Scheduled(cron = "0 0 * 28 * ?", zone = "America/Mexico_City")
    @Transactional
    @Override
    public void ejecutarCierreMensual() {
        LocalDateTime now = LocalDateTime.now();
        //Se verifica si la hora corresponde a las 10 de noche, solo se ejecuta los días 28 de cada mes, se coloca 23 por el horario de verano
        if (now.getHour() == 23) {
            List<PlantillaMensualDTO> registrosDTO = trabajador_plazaRepository.cierrePlantillaMensual();

            List<Registro_Plantilla_Mensual> registrosEntidad = registrosDTO.stream()
                    .map(this::convertirDTOPlantAEntidad)
                    .collect(Collectors.toList());
            registro_Plantilla_MensualRepository.saveAll(registrosEntidad);
        }
    }

    @Transactional
    @Override
    //@PostConstruct //Para generarla al ejecutar el programa, se hizo la carga inicial el dia de hoy 17/07/2024
    public void generaRegistroPlazasConsolidado() {
        List<Registro_Plazas_ConsolidadoDTO> registrosDTO = cat_PlazaRepository.generaRegistroConsolidado();

        List<Registro_Plazas_Consolidado> registrosEntidad = registrosDTO.stream()
                .map(this::convertirConsolidadoaEntidad)
                .collect(Collectors.toList());

        registro_Plazas_ConsolidadoRepository.saveAll(registrosEntidad);
    }

    /* Se ejecuta los días 28 cada hora hasta comprobar que sea media noche, esto se realizó así porque no reconoce la hora cuando entra a cron como parametro
    ,hace una comparación con la tabla registro_plazas_consolidado */
    @Scheduled(cron = "0 0 * 28 * ?", zone = "America/Mexico_City")
    @Transactional
    @Override
    public void ejecutarTmpRegistroConsolidado() {
        LocalDateTime now = LocalDateTime.now();
        //Se verifica si la hora corresponde a las 10 de la noche, solo se ejecuta los días 28 de cada mes, se coloca 23 por el horario de verano
        if (now.getHour() == 23) {
            List<Tmp_ConsolidadoDTO> registrosDTO = cat_PlazaRepository.generaRegistroTemporalConsolidado();
            //Se convierte el DTO devuelto en la consulta a una entidad de TMP_Consolidado
            List<Tmp_Consolidado> registrosEntidad = registrosDTO.stream()
                    .map(this::convertirDtoTmpConsolidadoaEntidad)
                    .collect(Collectors.toList());
            //Se guarda la consulta obtenida en la tabla tmp_consolidado
            tmp_ConsolidadoRepository.saveAll(registrosEntidad);
            //Se obtiene el número de modificaciones hechas en la entidad principal registro_plazas_consolidado
            Long numeroCambios = registro_Plazas_ConsolidadoRepository.obtenerNumeroCambiosConsolidado();
            //Si el número de cambios es mayor a uno entonces se actualiza el estatus en la tabla registro_plazas_consolidado 
            if (numeroCambios > 0) {
                registro_Plazas_ConsolidadoRepository.actualizarEstatusRegistroConsolidado();
            }
            //Se obtiene el número de puestos no consolidados o con modificaciones
            Long numeroAltas = tmp_ConsolidadoRepository.contarPuestosNoConsolidados();
            if (numeroAltas > 0) {
                //Se colocan las nuevas categorías modificadas o adicionadas a la tabla principal registro_plantilla_mensual
                tmp_ConsolidadoRepository.adicionaCategoriasNuevaCreacionoModificadas();
                //Se eliminan los registros de la tabla temporal
                tmp_ConsolidadoRepository.eliminaRegistrosTemporales();
            }
        }
    }

    @Override
    public Boolean boolEjecutoCierreMensual(Integer anio, Integer mes) {
        return registro_Plantilla_MensualRepository.boolEjecutoCierreMensual(anio, mes);
    }

    private Registro_Plantilla_Mensual convertirDTOPlantAEntidad(PlantillaMensualDTO dto) {
        Registro_Plantilla_Mensual entidad = new Registro_Plantilla_Mensual();

        entidad.setAnio(dto.getAnio());
        entidad.setMes(dto.getMes());
        entidad.setTrabajador_id(dto.getTrabajador_id());
        entidad.setNumero_plaza(dto.getNumero_plaza());
        entidad.setTipo(dto.getTipo());
        entidad.setNivel(dto.getNivel());
        entidad.setCodigo_puesto(dto.getCodigo_puesto());
        entidad.setPuesto(dto.getPuesto());
        entidad.setSueldo_mensual(dto.getSueldo_mensual());
        entidad.setCodigo_area(dto.getCodigo_area());
        entidad.setPuesto_id(dto.getPuesto_id());
        entidad.setArea_id(dto.getIdArea());
        entidad.setTipo_contrato_id(dto.getTipo_contrato_id());
        entidad.setTipo_nomina_id(dto.getTipo_nomina_id());
        entidad.setPrestaciones(dto.getPrestaciones());
        entidad.setSueldo_mensual_bruto(dto.getSueldo_mensual_bruto());

        return entidad;
    }

    private Tmp_Consolidado convertirDtoTmpConsolidadoaEntidad(Tmp_ConsolidadoDTO dto) {
        Tmp_Consolidado entidad = new Tmp_Consolidado();

        entidad.setAnio(dto.getAnio());
        entidad.setMes(dto.getMes());
        entidad.setTipo(dto.getTipo());
        entidad.setPuesto_id(dto.getPuesto_id());
        entidad.setNivel(dto.getNivel());
        entidad.setNumero_plazas_autorizadas(dto.getNumero_plazas_autorizadas());
        entidad.setFecha_movimiento(dto.getFecha_movimiento());
        entidad.setEstatus(dto.getEstatus());

        return entidad;
    }

    private Registro_Plazas_Consolidado convertirConsolidadoaEntidad(Registro_Plazas_ConsolidadoDTO dto) {
        Registro_Plazas_Consolidado entidad = new Registro_Plazas_Consolidado();

        entidad.setAnio(dto.getAnio());
        entidad.setMes(dto.getMes());
        entidad.setTipo(dto.getTipo());
        entidad.setPuesto_id(dto.getPuesto_id());
        entidad.setNivel(dto.getNivel());
        entidad.setPlazas_autorizadas(dto.getNumero_plazas_autorizadas());
        entidad.setFecha_movimiento(dto.getFecha_movimiento());
        entidad.setEstatus(dto.getEstatus());

        return entidad;
    }
}
