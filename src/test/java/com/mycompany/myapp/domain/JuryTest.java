package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class JuryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jury.class);
        Jury jury1 = new Jury();
        jury1.setId(1L);
        Jury jury2 = new Jury();
        jury2.setId(jury1.getId());
        assertThat(jury1).isEqualTo(jury2);
        jury2.setId(2L);
        assertThat(jury1).isNotEqualTo(jury2);
        jury1.setId(null);
        assertThat(jury1).isNotEqualTo(jury2);
    }
}
