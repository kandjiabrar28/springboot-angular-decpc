package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PVSurveillanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PVSurveillance.class);
        PVSurveillance pVSurveillance1 = new PVSurveillance();
        pVSurveillance1.setId(1L);
        PVSurveillance pVSurveillance2 = new PVSurveillance();
        pVSurveillance2.setId(pVSurveillance1.getId());
        assertThat(pVSurveillance1).isEqualTo(pVSurveillance2);
        pVSurveillance2.setId(2L);
        assertThat(pVSurveillance1).isNotEqualTo(pVSurveillance2);
        pVSurveillance1.setId(null);
        assertThat(pVSurveillance1).isNotEqualTo(pVSurveillance2);
    }
}
