package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.Registro_Plazas_ConsolidadoDTO;
import com.sirh.sirh.DTO.Tmp_ConsolidadoDTO;
import com.sirh.sirh.entity.Cat_Plaza;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author nreyes22
 */
public interface Cat_PlazaRepository extends JpaRepository<Cat_Plaza, Integer> {

    //LISTAR LAS PLAZAS DE ACUERDO AL ID_PUESTO
    @Query(value = "SELECT * FROM catalogo_plaza sm WHERE sm.puesto_id = :puesto_id", nativeQuery = true)
    List<Cat_Plaza> findAllPlaza(@Param("puesto_id") Integer puesto_id);

    //CONTAR LAS PLAZAS DEPENDIENDO DEL NÚMERO DE PUESTO 
    @Query(value = "SELECT COUNT(*) FROM catalogo_plaza sm WHERE sm.puesto_id = :puesto_id and estatus_plaza_id = 3 or sm.puesto_id = :puesto_id and estatus_plaza_id = 1", nativeQuery = true)
    Integer countPlazas(@Param("puesto_id") Integer puesto_id);

    //OBTENER ULTIMO ID DE LA TABLA 
    @Query(value = "SELECT MAX(id_plaza) FROM catalogo_plaza", nativeQuery = true)
    Integer ultimoId();

    //BUSCAR LAS PLAZAS DISPONIBLES SEGUN EL ID DEL PUESTO
    @Query(value = "SELECT * FROM catalogo_plaza sm WHERE sm.puesto_id = :puesto_id and estatus_plaza_id = 3", nativeQuery = true)
    List<Cat_Plaza> findPlazasDisponibles(@Param("puesto_id") Integer puesto_id);

    //CONTAR LAS PLAZAS DISPONIBLES DEPENDIENDO DEL NÚMERO DE PUESTO 
    @Query(value = "SELECT COUNT(*) FROM catalogo_plaza sm WHERE sm.puesto_id = :puesto_id and estatus_plaza_id =3", nativeQuery = true)
    Integer countPlazasDisponibles(@Param("puesto_id") Integer puesto_id);

    //CONTAR LAS PLAZAS ASIGNADAS DEPENDIENDO DEL NÚMERO DE PUESTO 
    @Query(value = "SELECT COUNT(*) FROM catalogo_plaza sm WHERE sm.puesto_id = :puesto_id and estatus_plaza_id =1", nativeQuery = true)
    Integer countPlazasAsignadas(@Param("puesto_id") Integer puesto_id);

    //BUSCAR LAS PLAZAS ASIGNADAS SEGÚN EL ID DEL PUESTO
    @Query(value = "SELECT * FROM catalogo_plaza sm WHERE sm.puesto_id = :puesto_id and estatus_plaza_id = 1", nativeQuery = true)
    List<Cat_Plaza> findPlazasAsignadas(@Param("puesto_id") Integer puesto_id);

    @Query(value = "SELECT new com.sirh.sirh.DTO.Tmp_ConsolidadoDTO(\n"
            + "YEAR(current_date()) AS anio, \n"
            + "MONTH(current_date()) AS mes, \n"
            + "Puesto.base_confianza AS tipo,\n"
            + "Puesto.id_puesto AS puesto_id,\n"
            + "Puesto.nivel,\n"
            + "COUNT(*) AS numero_plazas_autorizadas,\n"
            + "CURRENT_DATE AS fecha_movimiento,\n"
            + "1 AS estatus)\n"
            + "FROM \n"
            + "Cat_Plaza AS InfoPlazas\n"
            + "JOIN Cat_Puesto AS Puesto ON InfoPlazas.cat_puesto.id_puesto = Puesto.id_puesto\n"
            + "WHERE estatus_plaza_id IN (1, 3)\n"
            + "GROUP BY Puesto.id_puesto, Puesto.nivel, Puesto.base_confianza\n"
            + "ORDER BY Puesto.base_confianza, Puesto.nivel, Puesto.id_puesto")
    List<Tmp_ConsolidadoDTO> generaRegistroTemporalConsolidado();

    @Query(value = "SELECT new com.sirh.sirh.DTO.Registro_Plazas_ConsolidadoDTO(\n"
            + "YEAR(current_date()), \n"
            + "MONTH(current_date()), \n"
            + "Puesto.base_confianza AS tipo,\n"
            + "Puesto.id_puesto,\n"
            + "Puesto.nivel,\n"
            + "COUNT(*) AS numero_plazas_autorizadas,\n"
            + "CURRENT_DATE AS fecha_movimiento,\n"
            + "1 AS estatus)\n"
            + "FROM \n"
            + "Cat_Plaza AS InfoPlazas\n"
            + "JOIN Cat_Puesto AS Puesto ON InfoPlazas.cat_puesto.id_puesto = Puesto.id_puesto\n"
            + "WHERE estatus_plaza_id IN (1, 3)\n"
            + "GROUP BY Puesto.id_puesto, Puesto.nivel, Puesto.base_confianza\n"
            + "ORDER BY Puesto.base_confianza, Puesto.nivel, Puesto.id_puesto")
    List<Registro_Plazas_ConsolidadoDTO> generaRegistroConsolidado();

}
