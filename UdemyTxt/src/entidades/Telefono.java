package entidades;

public class Telefono {

    private Integer id;
    private String numero;
    private Cliente clienteId;

    public Telefono() {
    }

    public Telefono(Integer id, String numero, Cliente clienteId) {
        this.id = id;
        this.numero = numero;
        this.clienteId = clienteId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

}
