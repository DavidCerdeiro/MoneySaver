package com.TFG.app.backend.type_periodic;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.TFG.app.backend.type_periodic.entity.Type_Periodic;
import com.TFG.app.backend.type_periodic.repository.Type_PeriodicRepository;

@DataJpaTest
@ActiveProfiles("test")
public class Type_PeriodicIntegrationTest {
    @Autowired
    private Type_PeriodicRepository typePeriodicRepository;

    @Test
    public void testCreateTypePeriodic() {
        Type_Periodic typePeriodic = new Type_Periodic();
        typePeriodic.setName("monthly");
        typePeriodicRepository.save(typePeriodic);

        assertThat(typePeriodicRepository.findByName("monthly")).isNotEmpty();
    }

}
