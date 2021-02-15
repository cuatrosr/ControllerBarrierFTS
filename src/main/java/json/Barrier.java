package json;

//El formato en el cual se recibe y devuelve el Json
public class Barrier {

    private String id;
    private String id_barrera;
    private String orden;
    private String fecha_solicitud;
    private String estatus_orden;
    private String mensaje;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_barrera() {
        return id_barrera;
    }

    public void setId_barrera(String id_barrera) {
        this.id_barrera = id_barrera;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getFecha_solicitud() {
        return fecha_solicitud;
    }

    public void setFecha_solicitud(String fecha_solicitud) {
        this.fecha_solicitud = fecha_solicitud;
    }
    
    public String getEstatus_orden() {
        return estatus_orden;
    }
    
    public void setEstatus_orden(String estatus_orden) {
        this.estatus_orden = estatus_orden;
    }

    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
