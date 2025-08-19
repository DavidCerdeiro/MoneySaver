package com.TFG.app.backend.establishment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.TFG.app.backend.establishment.entity.Establishment;
import com.TFG.app.backend.establishment.repository.EstablishmentRepository;
import com.TFG.app.backend.region.entity.Region;
import com.TFG.app.backend.region.repository.RegionRepository;

@DataJpaTest
@ActiveProfiles("test")
public class EstablishmentIntegrationTest {

    @Autowired
    private EstablishmentRepository establishmentRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Test
    public void testCreateEstablishment() {
        Region region = new Region();
        region.setCountry("Spain");
        region.setCity("Cádiz");
        regionRepository.save(region);

        Establishment establishment = new Establishment();
        establishment.setName("Eutimio");
        establishment.setRegion(region);

        establishmentRepository.save(establishment);

        assertThat(establishmentRepository.findByNameAndRegion(establishment.getName(), region)).isNotNull();
    }
}
