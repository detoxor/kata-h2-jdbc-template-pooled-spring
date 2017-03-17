package cz.tc.learn.h2;

import cz.tc.learn.h2.dao.PersonDao;
import cz.tc.learn.h2.model.Person;
import java.util.List;
import java.util.Random;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tomas.cejka
 * 
 * @see <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/html/jdbc.html">Data access with JDBC</a>
 * @see <a href="http://www.journaldev.com/2593/spring-jdbc-example">Spring JDBC example</a>
 * @see <a href="http://www.mkyong.com/spring/spring-jdbctemplate-querying-examples/">Spring JdbcTemplate Querying examples</a>
 */
public class App 
{
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");

        PersonDao personDao = ctx.getBean("personDao", PersonDao.class);
        long id = new Random().nextLong();
        
        Person emp = new Person();
        emp.setId(id);
        emp.setFirstName("Karolina");
        emp.setLastName("Malkova");

        //Create
        personDao.insert(emp);

        //Read
        Person emp1 = personDao.find(id);
        System.out.println("Person found=" + emp1);

        //Update
        emp.setLastName("Cejkova");
        personDao.update(emp);

        //Get All
        List<Person> list = personDao.findAll();
        System.out.println(list);
    }
}
