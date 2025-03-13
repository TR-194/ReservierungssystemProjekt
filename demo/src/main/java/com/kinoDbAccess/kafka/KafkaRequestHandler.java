package com.kinoDbAccess.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinoDbAccess.DbAccess;
import com.kinoDbAccess.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class KafkaRequestHandler {

    private final DbAccess dbAccess;
    private final KafkaTemplate<String, Map<String, Object>> kafkaTemplate;
    private final KafkaResponseSender responseSender;

    @Autowired
    public KafkaRequestHandler(DbAccess dbAccess, KafkaTemplate<String, Map<String, Object>> kafkaTemplate, KafkaResponseSender responseSender) {
        this.dbAccess = dbAccess;
        this.kafkaTemplate = kafkaTemplate;
        this.responseSender = responseSender;
    }

    // 🔹 1️⃣ Alle Aufführungen abrufen
    @KafkaListener(topics = "dbAuffuehrungGetAll", groupId = "kino-group")
    public void dbHandleAuffuehrungGetAllRequest(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        List<AuffuehrungDTO> auffuehrungen = dbAccess.getAllAuffuehrungen();
        responseSender.sendResponse("kino.response", requestId, auffuehrungen);
    }

    // 🔹 2️⃣ Einzelne Aufführung abrufen
    @KafkaListener(topics = "dbAuffuehrungGetById", groupId = "kino-group")
    public void dbHandleAuffuehrungGetByIdRequest(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        Long id = ((Number) request.get("id")).longValue();
        AuffuehrungDTO auffuehrung = dbAccess.getAuffuehrungById(id);
        responseSender.sendResponse("kino.response", requestId, auffuehrung);
    }

    // 🔹 3️⃣ Neue Aufführung erstellen
    @KafkaListener(topics = "dbAuffuehrungCreate", groupId = "kino-group")
    public void dbHandleAuffuehrungCreateRequest(Map<String, Object> request) {
        AuffuehrungDTO auffuehrungDTO = (AuffuehrungDTO) request.get("data");
        dbAccess.insertAuffuehrung(auffuehrungDTO);
    }

    // 🔹 4️⃣ Aufführung löschen
    @KafkaListener(topics = "dbAuffuehrungDelete", groupId = "kino-group")
    public void dbHandleAuffuehrungDeleteRequest(Map<String, Object> request) {
        Long id = ((Number) request.get("id")).longValue();
        dbAccess.deleteAuffuehrung(id);
    }

    // 🔹 5️⃣ Alle Filme abrufen
    @KafkaListener(topics = "dbFilmGetAll", groupId = "kino-group")
    public void dbHandleFilmGetAllRequest(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        List<FilmDTO> filme = dbAccess.getAllFilme();
        responseSender.sendResponse("kino.response", requestId, filme);
    }

    // 🔹 6️⃣ Neuen Film erstellen
    @KafkaListener(topics = "dbFilmCreate", groupId = "kino-group")
    public void dbHandleFilmCreateRequest(Map<String, Object> request) {
        FilmDTO filmDTO = (FilmDTO) request.get("data");
        dbAccess.insertFilm(filmDTO);
    }

    // 🔹 7️⃣ Alle Buchungen abrufen
    @KafkaListener(topics = "buchungGetAll", groupId = "kino-group")
    public void dbHandleBuchungGetAllRequest(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        List<BuchungDTO> buchungen = dbAccess.getAllBuchungen();
        responseSender.sendResponse("kino.response", requestId, buchungen);
    }

    // 🔹 8️⃣ Neue Buchung erstellen
    @KafkaListener(topics = "dbBuchungCreate", groupId = "kino-group")
    public void dbHandleBuchungCreateRequest(Map<String, Object> request) {
        BuchungDTO buchungDTO = (BuchungDTO) request.get("data");
        dbAccess.insertBuchung(buchungDTO);
    }

    // 🔹 9️⃣ Buchung löschen
    @KafkaListener(topics = "dbBuchungDelete", groupId = "kino-group")
    public void dbHandleBuchungDeleteRequest(Map<String, Object> request) {
        Long id = ((Number) request.get("id")).longValue();
        dbAccess.deleteBuchung(id);
    }

    // 🔹 🔟 Alle Reservierungen abrufen
    @KafkaListener(topics = "dbReservierungGetAll", groupId = "kino-group")
    public void dbHandleReservierungGetAllRequest(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        List<ReservierungDTO> reservierungen = dbAccess.getAllReservierungen();
        responseSender.sendResponse("kino.response", requestId, reservierungen);
    }

    // 🔹 11️⃣ Neue Reservierung erstellen
    @KafkaListener(topics = "dbReservierungCreate", groupId = "kino-group")
    public void dbHandleReservierungCreateRequest(Map<String, Object> request) {
        ReservierungDTO reservierungDTO = (ReservierungDTO) request.get("data");
        dbAccess.insertReservierung(reservierungDTO);
    }

    // 🔹 12️⃣ Reservierung stornieren
    @KafkaListener(topics = "dbReservierungCancel", groupId = "kino-group")
    public void dbHandleReservierungCancelRequest(Map<String, Object> request) {
        Long reservierungsId = ((Number) request.get("id")).longValue();
        dbAccess.deleteReservierung(reservierungsId);
    }

    // 🔹 13️⃣ Reservierung in Buchung umwandeln
    @KafkaListener(topics = "dbReservierungConvert", groupId = "kino-group")
    public void dbHandleReservierungConvertRequest(Map<String, Object> request) {
        Long reservierungsId = ((Number) request.get("id")).longValue();
        dbAccess.convertReservierungToBuchung(reservierungsId);
    }

    // 🔹 14️⃣ Alle Sitzplätze abrufen
    @KafkaListener(topics = "dbSitzplatzGetAll", groupId = "kino-group")
    public void dbHandleSitzplatzGetAllRequest(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        List<SitzplatzDTO> sitzplaetze = dbAccess.getAllSitzplaetze();
        responseSender.sendResponse("kino.response", requestId, sitzplaetze);
    }

    // 🔹 15️⃣ Sitzplätze einer Aufführung abrufen
    @KafkaListener(topics = "dbSitzplatzGetByAuffuehrung", groupId = "kino-group")
    public void dbHandleSitzplaetzeByAuffuehrungRequest(Map<String, Object> request) {
        String requestId = (String) request.get("requestId");
        Long auffuehrungId = ((Number) request.get("id")).longValue();
        List<SitzplatzDTO> sitzplaetze = dbAccess.getSitzplaetzeByAuffuehrung(auffuehrungId);
        responseSender.sendResponse("kino.response", requestId, sitzplaetze);
    }

    @KafkaListener(topics = "dbKinosaalCreate", groupId = "kino-group")
    public void dbHandleKinosaalCreateRequest(Map<String, Object> request) {
        System.out.println("Empfangene Kafka-Nachricht für Kinosaal-Erstellung: " + request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            KinosaalDTO kinosaal = objectMapper.convertValue(request.get("data"), KinosaalDTO.class);

            if (kinosaal == null) {
                System.err.println("Fehler: kinosaal ist null! Überprüfe das empfangene JSON.");
                return;
            }

            // 1. Kinosaal in DB speichern und ID abrufen
            Long kinosaalId = dbAccess.insertKinosaal(kinosaal);

            // 2. Sitzreihen speichern
            for (SitzreiheDTO reihe : kinosaal.getSitzreihen()) {
                reihe.setKinosaalId(kinosaalId);  // Kinosaal-ID setzen
                Long sitzreiheId = dbAccess.insertSitzreihe(reihe);

                //  3. Sitzplätze speichern
                for (SitzplatzDTO platz : reihe.getSitzplaetze()) {
                    platz.setSitzreiheId(sitzreiheId);
                    dbAccess.insertSitzplatz(platz);
                }
            }

            System.out.println("Kinosaal erfolgreich in DB gespeichert: " + kinosaal.getName());

        } catch (Exception e) {
            System.err.println("Fehler bei der Speicherung des Kinosaals: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
