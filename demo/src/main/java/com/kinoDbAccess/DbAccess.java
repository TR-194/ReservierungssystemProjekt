package com.kinoDbAccess;

import com.kinoDbAccess.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DbAccess {

    public JdbcTemplate getTemplate() {
        return template;
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    private JdbcTemplate template;

    public void InsertSaal(Saal saal) {
        String sql = "INSERT INTO saele (name, freigegeben) values (?, ?);";
        template.update(sql, saal.getName(), saal.isFreigegeben());
    }

    public List<Saal> GetAllSaele() {
        String sql = "SELECT * FROM saele";
        return template.query(sql, (rs, rowNum) -> new Saal(rs.getLong("id"), rs.getBoolean("freigegeben"), rs.getString("name")));
    }

    public void InsertReihe(Reihe reihe) {
        String sql = "INSERT INTO reihen (nr, saalId) values (?, ?, ?);";
        template.update(sql, reihe.getNr(), reihe.getSaalId());
    }

    public List<Reihe> GetAllReihen() {
        String sql = "SELECT * FROM reihen";
        return template.query(sql, (rs, rowNum) -> new Reihe(rs.getLong("id"), rs.getInt("nr"), rs.getLong("saalId"), Kategorie.valueOf(rs.getString("kategorie"))));
    }

    public void InsertPlatz(Platz platz) {
        String sql = "INSERT INTO plaetze (nr, reiheId) values (?, ?);";
        template.update(sql, platz.getNr(), platz.getReiheId());
    }

    public List<Platz> GetAllPlaetze() {
        String sql = "SELECT * FROM reihen";
        return template.query(sql, (rs, rowNum) -> new Platz(rs.getLong("id"), rs.getInt("nr"), (long)rs.getLong("reiheId")));
    }

    public void InsertFilm(Film film) {
        String sql = "INSERT INTO filme (name, altersbeschraenkung, dauer) values (?, ?, ?);";
        template.update(sql, film.getName(), film.getAlterbeschraenkung(), film.getDauer());
    }

    public List<Film> GetAllFilme() {
        String sql = "SELECT * FROM filme";
        return template.query(sql, (rs, rowNum) -> new Film(rs.getLong("id"), rs.getString("name"), rs.getString("altersbeschraenkung"), rs.getInt("dauer")));
    }

    public long InsertPreismodellAndGetId(Preismodell preismodell) throws SQLException {
        String sql = "INSERT INTO preismodelle (parkettPreis, logePreis, logeMitServicePreis) values (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(createPreparedStatementCreator(sql, preismodell), keyHolder);
        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            return generatedId.longValue();
        } else {
            throw new SQLException("Keine generierte ID gefunden.");
        }
    }

    public List<Preismodell> GetAllPreismodelle() {
        String sql = "SELECT * FROM preismodelle";
        return template.query(sql, (rs, rowNum) -> new Preismodell(rs.getInt("id"), rs.getDouble("parkettPreis"), rs.getDouble("logePreis"), rs.getDouble("logeMitServicePreis")));
    }

    public void InsertAuffuehrung(Auffuehrung auffuehrung) {
        String sql = "INSERT INTO auffuehrungen (filmId, datum, uhrzeit, saalId, preismodellId) values (?, ?, ?, ?, ?);";
        template.update(sql, auffuehrung.getFilmId(), auffuehrung.getDatum(), auffuehrung.getUhrzeit(), auffuehrung.getSaalId(), auffuehrung.getPreismodellId());
    }

    public List<Auffuehrung> GetAllAuffuehrungen() {
        String sql = "SELECT * FROM auffuehrungen";
        return template.query(sql, (rs, rowNum) -> new Auffuehrung(rs.getInt("id"), rs.getObject("datum", LocalDate.class), rs.getObject("uhrzeit", LocalTime.class), rs.getInt("saalId"), rs.getInt("preismodellId")));
    }

    public boolean InsertReservierung(Reservierung reservierung) {
        String sql = "INSERT INTO platzzugehoerigkeiten (auffuehrungsId, platzId, email) values ";
        ArrayList<Object> argsList = new ArrayList<Object>();
        for (long platzId : reservierung.getPlatzIds()) {
            sql = sql + "(?, ?, ?), ";
            argsList.add(reservierung.getAuffuehrungId());
            argsList.add(platzId);
            argsList.add(reservierung.getEmail());
        }
        sql = sql.substring(0, sql.length() - 2) + ";";
        Object[] args = argsList.toArray();
        try {
            template.update(sql, args);
            String sql2 = "INSERT INTO reservierungen (auffuehrungId, status, email) values (?, ?, ?);";
            template.update(sql2, reservierung.getAuffuehrungId(), reservierung.getStatus(), reservierung.getEmail());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Reservierung> GetAllReservierungen() {
        String sql = "SELECT * FROM reservierungen";
        List<Reservierung> reservierungen = template.query(sql, (rs, rowNum) -> new Reservierung(rs.getInt("id"), rs.getInt("auffuehrungId"), Status.valueOf(rs.getString("status")), rs.getString("email")));
        for (Reservierung reservierung : reservierungen) {
            String sql2 = "SELECT * FROM platzzugehoerigkeit WHERE auffuehrungId = ? AND email = ?;";
            List<Long> result = template.query(sql2, (rs, rowNum) -> rs.getLong("platzId"), reservierung.getAuffuehrungId(), reservierung.getEmail());
            reservierung.setPlatzIds(result);
        }
        return reservierungen;
    }

    public void UpdateReservierungStatus(long reservierungId, Status status) {
        String sql = "UPDATE reservierungen SET status = ? WHERE id = ?;";
        template.update(sql, status, reservierungId);
    }

    public void DeleteReservierung(long reservierungId) {
        String sql = "DELETE FROM reservierungen WHERE id = ? AND status = 'RESERVIERT';";
        template.update(sql, reservierungId);
    }

    public List<Reservierung> GetReservierungenByEmail(String email) {
        String sql = "SELECT * FROM reservierungen WHERE email = ?";
        List<Reservierung> reservierungen = template.query(sql, (rs, rowNum) -> new Reservierung(rs.getLong("id"), rs.getLong("auffuehrungId"), Status.valueOf(rs.getString("status")), rs.getString("email")), email);
        for (Reservierung reservierung : reservierungen) {
            String sql2 = "SELECT * FROM platzzugehoerigkeit WHERE auffuehrungId = ? AND email = ?;";
            List<Long> result = template.query(sql2, (rs, rowNum) -> rs.getLong("platzId"), reservierung.getAuffuehrungId(), reservierung.getEmail());
            reservierung.setPlatzIds(result);
        }
        return reservierungen;
    }

    private PreparedStatementCreator createPreparedStatementCreator(String sql, Preismodell preismodell) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                ps.setDouble(1, preismodell.getParkettPreis());
                ps.setDouble(2, preismodell.getLogePreis());
                ps.setDouble(3, preismodell.getLogeMitServicePreis());
                return ps;
            }
        };
    }

    private PreparedStatementCreator createSaalPreparedStatementCreator(String sql, Saal saal) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, saal.getName());
                return ps;
            }
        };
    }

    public long InsertSaalAndGetId(Saal saal) throws SQLException {
        String sql = "INSERT INTO saele (name) values (?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(createSaalPreparedStatementCreator(sql, saal), keyHolder);
        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            return generatedId.longValue();
        } else {
            throw new SQLException("Keine generierte ID gefunden.");
        }
    }

    private PreparedStatementCreator createReihePreparedStatementCreator(String sql, Reihe reihe) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                ps.setInt(1, reihe.getNr());
                ps.setLong(2, reihe.getSaalId());
                return ps;
            }
        };
    }

    public long InsertReiheAndGetId(Reihe reihe) throws SQLException {
        String sql = "INSERT INTO reihen (nr, saalId) values (?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(createReihePreparedStatementCreator(sql, reihe), keyHolder);
        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            return generatedId.longValue();
        } else {
            throw new SQLException("Keine generierte ID gefunden.");
        }
    }
}