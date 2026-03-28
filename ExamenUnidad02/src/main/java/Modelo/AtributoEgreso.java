package Modelo;

public class AtributoEgreso {
    private String atributo;
    private String criterio;
    private String indicador;
    private int calificacion;
    private String observaciones;
    private String instrumento;  
    
    public AtributoEgreso() {}
    
    public AtributoEgreso(String atributo, String criterio, String indicador) {
        this.atributo = atributo;
        this.criterio = criterio;
        this.indicador = indicador;
        this.calificacion = 0;
    }
    
    // Getters y Setters
    public String getAtributo() { return atributo; }
    public void setAtributo(String atributo) { this.atributo = atributo; }
    
    public String getCriterio() { return criterio; }
    public void setCriterio(String criterio) { this.criterio = criterio; }
    
    public String getIndicador() { return indicador; }
    public void setIndicador(String indicador) { this.indicador = indicador; }
    
    public int getCalificacion() { return calificacion; }
    public void setCalificacion(int calificacion) { this.calificacion = calificacion; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    public String getInstrumento() { return instrumento; }
    public void setInstrumento(String instrumento) { this.instrumento = instrumento; }
}