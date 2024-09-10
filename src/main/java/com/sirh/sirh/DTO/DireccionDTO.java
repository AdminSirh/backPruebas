
package com.sirh.sirh.DTO;

/**
 *
 * @author nreyes22
 */
public class DireccionDTO {
    
    private String calle;
    private String num_exterior;
    private String num_interior;
    private Integer colonia_id;
    private Integer persona_id;

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNum_exterior() {
        return num_exterior;
    }

    public void setNum_exterior(String num_exterior) {
        this.num_exterior = num_exterior;
    }

    public String getNum_interior() {
        return num_interior;
    }

    public void setNum_interior(String num_interior) {
        this.num_interior = num_interior;
    }

    public Integer getColonia_id() {
        return colonia_id;
    }

    public void setColonia_id(Integer colonia_id) {
        this.colonia_id = colonia_id;
    }

    public Integer getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(Integer persona_id) {
        this.persona_id = persona_id;
    }

    public DireccionDTO(String calle, String num_exterior, String num_interior, Integer colonia_id, Integer persona_id) {
        this.calle = calle;
        this.num_exterior = num_exterior;
        this.num_interior = num_interior;
        this.colonia_id = colonia_id;
        this.persona_id = persona_id;
    }

    public DireccionDTO() {
    }
    
    
    
}
