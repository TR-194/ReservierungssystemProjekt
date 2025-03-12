package com.kinoDbAccess.kafka;

import com.kinoDbAccess.DbAccess;
import com.kinoDbAccess.dto.*;
import com.kinoDbAccess.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class KafkaRequestHandler {

    DbAccess dbAccess = new DbAccess();

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, Map<String, Object>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    KafkaResponseSender responseSender = new KafkaResponseSender(kafkaTemplate);

    public KafkaRequestHandler(KafkaTemplate<String, Map<String, Object>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    @KafkaListener(topics = "kino.auffuehrung.getAll", groupId = "kino-group")
    public void handleAuffuehrungGetAllRequest(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        List<Auffuehrung> auffuehrungen = dbAccess.GetAllAuffuehrungen();
        List<Preismodell> preismodelle = dbAccess.GetAllPreismodelle();
        List<AuffuehrungDTO> dtos = new ArrayList<AuffuehrungDTO>();
        for(Auffuehrung auffuehrung : auffuehrungen){
            AuffuehrungDTO dto = new AuffuehrungDTO(auffuehrung.getId(), auffuehrung.getDatum(), auffuehrung.getUhrzeit(), auffuehrung.getFilmId(),
                    auffuehrung.getSaalId(), preismodelle.stream().filter(preismodell -> preismodell.getId() == auffuehrung.getPreismodellId()).findFirst().orElse(null));
            dtos.add(dto);
        }
        responseSender.sendResponse("kino.response", requestId, dtos);
    }

    @KafkaListener(topics = "kino.auffuehrung.create", groupId = "kino-group")
    public void handleAuffuehrungCreateRequest(Map<String, Object> request) {
        AuffuehrungDTO auffuehrungDTO = (AuffuehrungDTO) request.get("auffuehrungDTO");
        Preismodell preismodell = (Preismodell) auffuehrungDTO.preismodell;
        long id = 0;
        try{
            id = dbAccess.InsertPreismodellAndGetId(preismodell);
        } catch (Exception ex){
            System.out.println(ex.getStackTrace());
        }
        Auffuehrung auffuehrung = new Auffuehrung(auffuehrungDTO.filmId, auffuehrungDTO.datum, auffuehrungDTO.uhrzeit, auffuehrungDTO.kinosaalId, id);
        dbAccess.InsertAuffuehrung(auffuehrung);
    }

    @KafkaListener(topics = "kino.buchung.create", groupId = "kino-group")
    public void handleBuchungCreateRequest(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        BuchungDTO buchungDTO = ((BuchungDTO) request.get("buchungDTO"));
        Reservierung reservierung = new Reservierung(buchungDTO.getAuffuehrungId(), buchungDTO.getSitzplaetze(), Status.GEBUCHT, buchungDTO.getEmail());
        boolean success = dbAccess.InsertReservierung(reservierung);
        responseSender.sendResponse("kino.response", requestId, success);
    }

    @KafkaListener(topics = "kino.film.getAll", groupId = "kino-group")
    public  void handleFilmGetAllRequest(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        List<Film> filme = dbAccess.GetAllFilme();
        List<FilmDTO> dtos = new ArrayList<FilmDTO>();
        for (Film film : filme) {
            FilmDTO filmDTO = new FilmDTO(film.getId(), film.getName(), film.getAlterbeschraenkung(), film.getDauer());
            dtos.add(filmDTO);
        }
        responseSender.sendResponse("kino.response", requestId, dtos);
    }

    @KafkaListener(topics = "kino.film.create", groupId = "kino-group")
    public void handleFilmCreateRequest(Map<String, Object> request) {
        Film film = ((Film) request.get("filmDTO"));
        dbAccess.InsertFilm(film);
    }

    @KafkaListener(topics = "kinosaal.create", groupId = "kino-group")
    public void handleSaalCreateRequest(Map<String, Object> request) {
        KinosaalDTO kinosaal = ((KinosaalDTO) request.get("kinosaalDTO"));
        Saal saal = new Saal(kinosaal.isFreigegeben(), kinosaal.getName());
        try{
            long saalId = dbAccess.InsertSaalAndGetId(saal);
            for(SitzreiheDTO sitzreiheDTO : kinosaal.getSitzreihen()){
                Reihe reihe = new Reihe(sitzreiheDTO.getReihenNummer(), saalId, sitzreiheDTO.getKategorie());
                long reiheId = dbAccess.InsertReiheAndGetId(reihe);
                for (SitzplatzDTO sitzplatzDTO : sitzreiheDTO.getSitzplaetze()){
                    Platz platz = new Platz(sitzplatzDTO.getNummer(), reiheId);
                    dbAccess.InsertPlatz(platz);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
        }
    }

    @KafkaListener(topics = "reservierung.create", groupId = "kino-group")
    public void handleReservierungCreateRequest(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        ReservierungDTO reservierungDTO = ((ReservierungDTO) request.get("reservierungDTO"));
        Reservierung reservierung = new Reservierung(reservierungDTO.getAuffuehrungId(), reservierungDTO.getSitzplatzIds(), Status.RESERVIERT, reservierungDTO.getEmail());
        boolean success = dbAccess.InsertReservierung(reservierung);
        responseSender.sendResponse("kino.response", requestId, success);
    }

    @KafkaListener(topics = "reservierung.cancel", groupId = "kino-group")
        public void handleReservierungDeleteRequest(Map<String, Object> request) {
        dbAccess.DeleteReservierung((long) request.get("reservierungsId"));
    }

    @KafkaListener(topics = "reservierung.convert", groupId = "kino-group")
    public void handleReservierungConvertRequest(Map<String, Object> request) {
        dbAccess.UpdateReservierungStatus((long) request.get("reservierungsId"), Status.GEBUCHT);
    }

    @KafkaListener(topics = "sitzplatz.getAll", groupId = "kino-group")
        public void handleSitzplatzGetAllRequest(Map<String, Object> request){
        String requestId = (String) request.get("requestId");
        List<Platz> plaetze = dbAccess.GetAllPlaetze();
        List<SitzplatzDTO> dtos = new ArrayList<SitzplatzDTO>();
        for (Platz platz : plaetze) {
            SitzplatzDTO dto = new SitzplatzDTO(platz.getId(), platz.getNr(), platz.getReiheId());
            dtos.add(dto);
        }
        responseSender.sendResponse("kino.response", requestId, dtos);
    }
}
