package com.TFG.app.backend.region;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.TFG.app.backend.region.entity.Region;
import com.TFG.app.backend.region.repository.RegionRepository;

@DataJpaTest
@ActiveProfiles("test")
public class RegionIntegrationTest {
    @Autowired
    private RegionRepository regionRepository;

    @Test
    public void testSaveRegion() {
        Region region = new Region();
        region.setCountry("Spain");
        region.setCity("Cádiz");
        regionRepository.save(region);

        assertThat(regionRepository.existsByCountryAndCity("Spain", "Cádiz")).isTrue();
    }

    @Test
    public void testSaveDuplicateRegionThrowsException() {
        Region region1 = new Region();
        region1.setCountry("Spain");
        region1.setCity("Cádiz");
        regionRepository.save(region1);

        Region region2 = new Region();
        region2.setCountry("Spain");
        region2.setCity("Cádiz");

        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> { regionRepository.saveAndFlush(region2); });
    }
}
