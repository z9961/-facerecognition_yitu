import static org.junit.Assert.*;

import org.junit.Test;

import dao.IUserDao;
import dao.UserDaoImp;
import entity.User;

public class userTest {

	@Test
	public void test() {
		IUserDao userdao = new UserDaoImp();

		User user = new User();

		user.setPhone("12345678912");
		user.setPassword("222");
		user.setUsername("test");
		user.setState(0);

		Boolean isok = userdao.registe(user);
		System.out.println(isok);

		User user2 = userdao.login("12345678912", "222");
		System.out.println(user2.toString());

		User user3 = userdao.login("12345678912");
		System.out.println(user3.toString());

	}

}
