package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CorrecteurTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Correcteur.class);
        Correcteur correcteur1 = new Correcteur();
        correcteur1.setId(1L);
        Correcteur correcteur2 = new Correcteur();
        correcteur2.setId(correcteur1.getId());
        assertThat(correcteur1).isEqualTo(correcteur2);
        correcteur2.setId(2L);
        assertThat(correcteur1).isNotEqualTo(correcteur2);
        correcteur1.setId(null);
        assertThat(correcteur1).isNotEqualTo(correcteur2);
    }
}
