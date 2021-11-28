package com.quick.demo.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.transform.Source;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUserRelationRepositoryTest {

	@Autowired
	private RedisUserRelationRepository relationRepository;

	@Test
	public void friends() {
		relationRepository.friends("1638013041150965", List.of("1638013051146945", "1638013058940191"));
	}
}