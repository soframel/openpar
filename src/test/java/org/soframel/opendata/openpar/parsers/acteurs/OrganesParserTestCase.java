package org.soframel.opendata.openpar.parsers.acteurs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.soframel.opendata.openpar.parsers.acteurs.Organe.TypeOrgane;
import org.soframel.opendata.openpar.repository.OrganeRepository;

public class OrganesParserTestCase {

	private OrganesParser parser;
	private OrganeRepository repo;
	private List<Organe> savedOrganes;

	@Before
	public void init() {
		savedOrganes = new ArrayList<Organe>();

		repo = Mockito.mock(OrganeRepository.class);
		when(repo.insert(Mockito.any(Organe.class))).thenAnswer(
				new Answer<Organe>() {
					@Override
					public Organe answer(InvocationOnMock invocation)
							throws Throwable {
						Organe Organe = invocation.getArgumentAt(0,
								Organe.class);
						savedOrganes.add(Organe);
						return Organe;
					}
				});

		parser = new OrganesParser();
		parser.setOrganeRepository(repo);
	}

	@Test
	public void testParseSmallOrganesFile() throws IOException {

		try (InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("organes-small.xml");) {
			assertNotNull("in is null", in);
			parser.parseAndInsert(in);
		}

		assertEquals(2, savedOrganes.size());

		for (Organe organe : savedOrganes) {
			if (organe.getType() == TypeOrgane.OrganeParlementaire_Type) {
				assertEquals("PO711218", organe.getUid());
				assertEquals("Formation des enseignants", organe.getLibelle());
				assertEquals(
						"de la mission d'information sur la formation des enseignants",
						organe.getLibelleEdition());
				assertEquals("Formation des enseignants",
						organe.getLibelleAbrege());
				assertEquals("FORMENS", organe.getLibelleAbrev());
				assertEquals(LocalDate.of(2015, 12, 16), organe.getDateDebut());
				assertNull(organe.getDateAgrement());
				assertEquals(LocalDate.of(2016, 10, 5), organe.getDateFin());
				assertEquals("5ème République", organe.getRegime());
				assertEquals(14, organe.getLegislature());

			} else if (organe.getType() == TypeOrgane.OrganeExterne_Type) {
				assertEquals("PO76034", organe.getUid());
				assertEquals("Conseil constitutionnel", organe.getLibelle());
				assertEquals("du Conseil constitutionnel",
						organe.getLibelleEdition());
				assertEquals("Conseil constitutionnel",
						organe.getLibelleAbrege());
				assertEquals("ASSEX", organe.getLibelleAbrev());
				assertEquals(LocalDate.of(1959, 2, 1), organe.getDateDebut());
				assertNull(organe.getDateAgrement());
				assertNull(organe.getDateFin());
				assertNull(organe.getRegime());
				assertEquals(0, organe.getLegislature());
			} else {
				fail("unknown type: " + organe.getType());
			}
		}
	}
}
