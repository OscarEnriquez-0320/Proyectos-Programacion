package Modelo;

public class Persona {
    private String nombre;
    private String ocupacion;
    private int categoriaEdad;
    private String categoriaEmpleo;
    private String taxId;
    private boolean usCitizen;
    private String genero;

    public Persona(String nombre, String ocupacion, int categoriaEdad, 
                   String categoriaEmpleo, String taxId, boolean usCitizen, String genero) {
        this.nombre = nombre;
        this.ocupacion = ocupacion;
        this.categoriaEdad = categoriaEdad;
        this.categoriaEmpleo = categoriaEmpleo;
        this.taxId = taxId;
        this.usCitizen = usCitizen;
        this.genero = genero;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getOcupacion() { return ocupacion; }
    public int getCategoriaEdad() { return categoriaEdad; }
    public String getCategoriaEmpleo() { return categoriaEmpleo; }
    public String getTaxId() { return taxId; }
    public boolean isUsCitizen() { return usCitizen; }
    public String getGenero() { return genero; }
}