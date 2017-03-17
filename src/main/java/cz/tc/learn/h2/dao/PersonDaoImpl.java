package cz.tc.learn.h2.dao;

import cz.tc.learn.h2.model.Person;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * Data access object using spring's JDBC template API
 *
 * @author tomas.cejka
 */
public class PersonDaoImpl implements PersonDao {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insert(Person person) {
        String query = "insert into person (id, first_name, last_name) values (?,?,?)";
        Object[] args = new Object[]{person.getId(), person.getFirstName(), person.getLastName()};
        int result = jdbcTemplate.update(query, args);
        if (result != 0) {
            System.out.println("Person insert with id=" + person.getId());
        } else {
            System.out.println("Person insert failed with id=" + person.getId());
        }
    }

    @Override
    public void update(Person person) {
        String query = "update person set first_name=?, last_name=? where id=?";
        Object[] args = new Object[]{person.getFirstName(), person.getLastName(), person.getId()};
        int result = jdbcTemplate.update(query, args);
        if (result != 0) {
            System.out.println("Person insert with id=" + person.getId());
        } else {
            System.out.println("Person insert failed with id=" + person.getId());
        }
    }

    @Override
    public Person find(Long idPerson) {
        Person person = this.jdbcTemplate.queryForObject("select id, first_name, last_name from person where id = ?", 
                new Object[]{idPerson}, new PersonMapper());
        return person;
    }

    @Override
    public List<Person> findAll() {
        List<Person> person = this.jdbcTemplate.query("select id, first_name, last_name from person", 
                new PersonMapper());
        return person;
    }

    private static final class PersonMapper implements RowMapper<Person> {

        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person person = new Person();
            person.setId(rs.getLong("id"));
            person.setFirstName(rs.getString("first_name"));
            person.setLastName(rs.getString("last_name"));
            return person;
        }
    }
}
