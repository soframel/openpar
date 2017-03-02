package org.soframel.opendata.openpar.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.soframel.opendata.openpar.OpenparApp;
import org.soframel.opendata.openpar.repository.ScrutinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenparApp.class)
public class ScrutinsServiceTest {

	@Autowired
	private ScrutinRepository scrutinRepository;

	@Autowired
	private ScrutinsService scrutinsService;

	@Test
	public void downloadScrutins() throws ClientProtocolException, IOException {
		scrutinsService.downloadAndUpdateScrutins();

		assertThat(scrutinRepository.count() > 0);
	}

}
