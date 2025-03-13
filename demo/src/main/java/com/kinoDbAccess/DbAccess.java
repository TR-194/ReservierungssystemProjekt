package com.kinoDbAccess;

import com.kinoDbAccess.dto.*;
import com.kinoDbAccess.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DbAccess {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public DbAccess(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
    }

    // Alle Aufführungen abrufen
    public List<AuffuehrungDTO> getAllAuffuehrungen() {
        String sql = "SELECT * FROM Auffuehrung";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new AuffuehrungDTO(
                rs.getLong("id"),
                rs.getDate("datum").toLocalDate(),
                rs.getTime("uhrzeit").toLocalTime(),
                rs.getLong("film_id"),
                rs.getLong("kinosaal_id"),
                getPreismodellById(rs.getLong("preismodell_id"))
        ));
    }

    //⃣Eine Aufführung anhand der ID abrufen
    public AuffuehrungDTO getAuffuehrungById(Long id) {
        String sql = "SELECT * FROM Auffuehrung WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new AuffuehrungDTO(
                rs.getLong("id"),
                rs.getDate("datum").toLocalDate(),
                rs.getTime("uhrzeit").toLocalTime(),
                rs.getLong("film_id"),
                rs.getLong("kinosaal_id"),
                getPreismodellById(rs.getLong("preismodell_id"))
        ), id);
    }

    //  Neue Aufführung erstellen
    public void insertAuffuehrung(AuffuehrungDTO auffuehrung) {
        String sql = "INSERT INTO Auffuehrung (datum, uhrzeit, film_id, kinosaal_id, preismodell_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, auffuehrung.getDatum(), auffuehrung.getUhrzeit(), auffuehrung.getFilmId(), auffuehrung.getKinosaalId(), auffuehrung.getPreismodell().getId());
    }

    // Aufführung löschen
    public void deleteAuffuehrung(Long id) {
        String sql = "DELETE FROM Auffuehrung WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Alle Buchungen abrufen
    public List<BuchungDTO> getAllBuchungen() {
        String sql = "SELECT * FROM Buchung";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new BuchungDTO(
                rs.getLong("id"),
                rs.getLong("auffuehrung_id"),
                getSitzplaetzeForBuchung(rs.getLong("id")),  // Holt vollständige Sitzplätze
                getZahlungByBuchungId(rs.getLong("id")),
                rs.getString("email")
        ));
    }

    private List<SitzplatzDTO> getSitzplaetzeForBuchung(Long buchungId) {
        String sql = "SELECT s.id, s.nummer, s.status, s.sitzreihe_id " +
                "FROM Sitzplatz s " +
                "JOIN Buchung_Sitzplatz bs ON s.id = bs.sitzplatz_id " +
                "WHERE bs.buchung_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new SitzplatzDTO(
                rs.getLong("id"),
                rs.getInt("nummer"),
                rs.getString("status"),
                rs.getLong("sitzreihe_id")
        ), buchungId);
    }



    public void insertBuchung(BuchungDTO buchung) {
        transactionTemplate.execute(status -> {
            try {
                for (SitzplatzDTO sitzplatz : buchung.getSitzplaetze()) {
                    Long sitzplatzId = sitzplatz.getId();

                    // 1️⃣ Sitzplatz sperren (FOR UPDATE)
                    String lockQuery = "SELECT status FROM Sitzplatz WHERE id = ? FOR UPDATE";
                    String statusSitzplatz = jdbcTemplate.queryForObject(lockQuery, String.class, sitzplatzId);

                    if (!"frei".equals(statusSitzplatz)) {
                        throw new IllegalStateException("Sitzplatz " + sitzplatzId + " ist nicht mehr verfügbar!");
                    }

                    // 2️⃣ Buchung in die DB einfügen
                    String insertQuery = "INSERT INTO Buchung (auffuehrung_id, email, zahlung_id) VALUES (?, ?, ?)";
                    jdbcTemplate.update(insertQuery, buchung.getAuffuehrungId(), buchung.getEmail(), buchung.getZahlung().getId());

                    // 3️⃣ Sitzplatz als "gebucht" markieren
                    String updateQuery = "UPDATE Sitzplatz SET status = 'gebucht' WHERE id = ?";
                    jdbcTemplate.update(updateQuery, sitzplatzId);
                }

            } catch (Exception e) {
                status.setRollbackOnly();  // Transaktion rückgängig machen
                throw e;
            }

            return null;
        });
    }

    //  Buchung löschen
    public void deleteBuchung(Long id) {
        String sql = "DELETE FROM Buchung WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Alle Filme abrufen
    public List<FilmDTO> getAllFilme() {
        String sql = "SELECT * FROM Film";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new FilmDTO(
                rs.getLong("id"),
                rs.getString("titel"),
                rs.getString("altersbeschraenkung"),
                rs.getInt("dauer")
        ));
    }

    //  Neuen Film hinzufügen
    public void insertFilm(FilmDTO film) {
        String sql = "INSERT INTO Film (titel, altersbeschraenkung, dauer) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, film.getTitel(), film.getAlterbeschraenkung(), film.getDauer());
    }

    public Long insertKinosaal(KinosaalDTO kinosaal) {
        String sql = "INSERT INTO Kinosaal (name, freigegeben) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, kinosaal.getName());
            ps.setBoolean(2, kinosaal.isFreigegeben());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    // Reservierung erstellen
    public void insertReservierung(ReservierungDTO reservierung) {
        transactionTemplate.execute(status -> {
            try {
                // Reservierung in die DB einfügen und ID abrufen
                String insertReservierungQuery = "INSERT INTO Reservierung (auffuehrung_id, email, name, preis, status) VALUES (?, ?, ?, ?, ?)";
                KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(insertReservierungQuery, Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, reservierung.getAuffuehrungId());
                    ps.setString(2, reservierung.getEmail());
                    ps.setString(3, reservierung.getName());
                    ps.setDouble(4, reservierung.getPreis());
                    ps.setString(5, reservierung.getStatus());
                    return ps;
                }, keyHolder);

                Long reservierungId = keyHolder.getKey().longValue();

                //  Alle Sitzplätze sichern
                for (SitzplatzDTO sitzplatz : reservierung.getSitzplaetze()) {
                    Long sitzplatzId = sitzplatz.getId();

                    //Sitzplatz sperren (FOR UPDATE)
                    String lockQuery = "SELECT status FROM Sitzplatz WHERE id = ? FOR UPDATE";
                    String statusSitzplatz = jdbcTemplate.queryForObject(lockQuery, String.class, sitzplatzId);

                    if (!"frei".equals(statusSitzplatz)) {
                        throw new IllegalStateException("Sitzplatz " + sitzplatzId + " ist nicht mehr verfügbar!");
                    }

                    // Sitzplatz mit der Reservierung verknüpfen
                    String insertSitzplatzReservierungQuery = "INSERT INTO Reservierung_Sitzplatz (reservierung_id, sitzplatz_id) VALUES (?, ?)";
                    jdbcTemplate.update(insertSitzplatzReservierungQuery, reservierungId, sitzplatzId);

                    // Sitzplatz als "reserviert" markieren
                    String updateQuery = "UPDATE Sitzplatz SET status = 'reserviert' WHERE id = ?";
                    jdbcTemplate.update(updateQuery, sitzplatzId);
                }

            } catch (Exception e) {
                status.setRollbackOnly();  // Transaktion rückgängig machen
                throw e;
            }

            return null;
        });
    }

    // Reservierung stornieren
    public void deleteReservierung(Long reservierungId) {
        String sql = "DELETE FROM Reservierung WHERE id = ?";
        jdbcTemplate.update(sql, reservierungId);
    }

    // Sitzplatz einer Aufführung abrufen
    public List<SitzplatzDTO> getSitzplaetzeByAuffuehrung(Long auffuehrungId) {
        String sql = "SELECT * FROM Sitzplatz WHERE id IN (SELECT sitzplatz_id FROM Reservierung_Sitzplatz WHERE reservierung_id IN (SELECT id FROM Reservierung WHERE auffuehrung_id = ?))";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new SitzplatzDTO(
                rs.getLong("id"),
                rs.getInt("nummer"),
                rs.getString("status"),
                rs.getLong("sitzreihe_id")
        ), auffuehrungId);
    }

    // Zahlung anhand der Buchungs-ID abrufen
    private ZahlungDTO getZahlungByBuchungId(Long buchungId) {
        String sql = "SELECT * FROM Zahlung WHERE buchung_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new ZahlungDTO(
                rs.getLong("id"),
                rs.getDouble("betrag"),
                rs.getString("filmtitel"),
                rs.getLong("auffuehrung_id"),
                rs.getDate("zahlungsdatum").toLocalDate(),
                rs.getLong("buchung_id")
        ), buchungId);
    }

    // Sitzplatz-IDs einer Buchung abrufen
    private List<Long> getSitzplatzIdsForBuchung(Long buchungId) {
        String sql = "SELECT sitzplatz_id FROM Buchung_Sitzplatz WHERE buchung_id = ?";
        return jdbcTemplate.queryForList(sql, Long.class, buchungId);
    }

    // Preismodell anhand der ID abrufen
    private PreismodellDTO getPreismodellById(Long id) {
        String sql = "SELECT * FROM Preismodell WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new PreismodellDTO(
                rs.getLong("id"),
                rs.getDouble("loge_mit_service_preis"),
                rs.getDouble("loge_preis"),
                rs.getDouble("parkett_preis")
        ), id);
    }

    public List<SitzplatzDTO> getAllSitzplaetze() {
        String sql = "SELECT * FROM Sitzplatz";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new SitzplatzDTO(
                rs.getLong("id"),
                rs.getInt("nummer"),
                rs.getString("status"),
                rs.getLong("sitzreihe_id")
        ));
    }

    public void convertReservierungToBuchung(Long reservierungsId) {
        String sql = "UPDATE Reservierung SET status = 'GEBUCHT' WHERE id = ?";
        jdbcTemplate.update(sql, reservierungsId);
    }

    public List<ReservierungDTO> getAllReservierungen() {
        String sql = "SELECT * FROM Reservierung";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ReservierungDTO(
                rs.getLong("id"),
                rs.getLong("auffuehrung_id"),
                getSitzplaetzeForReservierung(rs.getLong("id")),  // Jetzt vollständige Sitzplatz-Objekte abrufen
                rs.getString("status"),
                rs.getDouble("preis"),
                rs.getString("email"),
                rs.getString("name")
        ));
    }

    /**
     * Holt alle Sitzplätze einer Reservierung als vollständige `SitzplatzDTO`-Objekte.
     */
    private List<SitzplatzDTO> getSitzplaetzeForReservierung(Long reservierungId) {
        String sql = "SELECT s.id, s.nummer, s.status, s.sitzreihe_id " +
                "FROM Sitzplatz s " +
                "JOIN Reservierung_Sitzplatz rs ON s.id = rs.sitzplatz_id " +
                "WHERE rs.reservierung_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new SitzplatzDTO(
                rs.getLong("id"),
                rs.getInt("nummer"),
                rs.getString("status"),
                rs.getLong("sitzreihe_id")
        ), reservierungId);
    }

    // Hilfsmethode: Holt die Sitzplätze einer Reservierung
    private List<Long> getSitzplatzIdsForReservierung(Long reservierungId) {
        String sql = "SELECT sitzplatz_id FROM Reservierung_Sitzplatz WHERE reservierung_id = ?";
        return jdbcTemplate.queryForList(sql, Long.class, reservierungId);
    }

    public Long insertSitzreihe(SitzreiheDTO reihe) {
        String sql = "INSERT INTO Sitzreihe (reihen_nummer, kategorie_id, kinosaal_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, reihe.getReihenNummer());
            ps.setLong(2, reihe.getKategorieId());
            ps.setLong(3, reihe.getKinosaalId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void insertSitzplatz(SitzplatzDTO platz) {
        String sql = "INSERT INTO Sitzplatz (nummer, status, sitzreihe_id) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, platz.getNummer(), platz.getStatus(), platz.getSitzreiheId());
    }



}
