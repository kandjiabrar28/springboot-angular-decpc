package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class SurveillantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Surveillant.class);
        Surveillant surveillant1 = new Surveillant();
        surveillant1.setId(1L);
        Surveillant surveillant2 = new Surveillant();
        surveillant2.setId(surveillant1.getId());
        assertThat(surveillant1).isEqualTo(surveillant2);
        surveillant2.setId(2L);
        assertThat(surveillant1).isNotEqualTo(surveillant2);
        surveillant1.setId(null);
        assertThat(surveillant1).isNotEqualTo(surveillant2);
    }
}
