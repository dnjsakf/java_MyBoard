package my.board.home;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class MysqlConnectionTest {
	@Inject
	DataSource dataSource;
	
	@Test
	public void test() throws Exception {
		Connection conn = dataSource.getConnection();
		System.out.println("[conn] " + conn);
		if( conn != null ) {
			PreparedStatement pd = conn.prepareStatement("SELECT NOW()");
			ResultSet result = pd.executeQuery();
			if( result.next() ) {
				System.out.println("[user] " + result.getString(1));
			} else {
				System.out.println("[no user]");
			}
		} else {
			System.out.println("[connection is null]");
		}
	}
	
}
