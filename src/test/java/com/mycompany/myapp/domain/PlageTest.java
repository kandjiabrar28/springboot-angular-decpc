package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PlageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plage.class);
        Plage plage1 = new Plage();
        plage1.setId(1L);
        Plage plage2 = new Plage();
        plage2.setId(plage1.getId());
        assertThat(plage1).isEqualTo(plage2);
        plage2.setId(2L);
        assertThat(plage1).isNotEqualTo(plage2);
        plage1.setId(null);
        assertThat(plage1).isNotEqualTo(plage2);
    }
}
