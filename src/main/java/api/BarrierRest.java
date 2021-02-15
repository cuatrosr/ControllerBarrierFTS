package api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import json.Barrier;
import model.ConvertAndAnalyze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/barrier")
public class BarrierRest {

    private static final Logger logger = LoggerFactory.getLogger(BarrierRest.class);

    @Path("/post")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Barrier responsePostJson(Barrier obj) throws Exception {
        Barrier br = new Barrier();
        br.setId(obj.getId());
        br.setId_barrera(obj.getId_barrera());
        br.setOrden(obj.getOrden());
        br.setFecha_solicitud(obj.getFecha_solicitud());
        br.setEstatus_orden(ConvertAndAnalyze.analyzeIdBarrier(obj.getId_barrera(), obj.getOrden()));
        boolean error = ConvertAndAnalyze.setBarrierJsonMensaje(br);
        if (error) {
            logger.error("Barrier. id:{}, id_barrera:{}, orden:{}, fecha_solicitud:{}, estatus_orden:{}, mensaje:{}",
                    br.getId(), br.getId_barrera(), br.getOrden(), br.getFecha_solicitud(),
                    br.getEstatus_orden(), br.getMensaje());
        } else {
            logger.info("Barrier. id:{}, id_barrera:{}, orden:{}, fecha_solicitud:{}, estatus_orden:{}, mensaje:{}",
                    br.getId(), br.getId_barrera(), br.getOrden(), br.getFecha_solicitud(),
                    br.getEstatus_orden(), br.getMensaje());
        }
        return br;
    }
}
