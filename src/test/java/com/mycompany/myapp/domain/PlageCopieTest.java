package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PlageCopieTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlageCopie.class);
        PlageCopie plageCopie1 = new PlageCopie();
        plageCopie1.setId(1L);
        PlageCopie plageCopie2 = new PlageCopie();
        plageCopie2.setId(plageCopie1.getId());
        assertThat(plageCopie1).isEqualTo(plageCopie2);
        plageCopie2.setId(2L);
        assertThat(plageCopie1).isNotEqualTo(plageCopie2);
        plageCopie1.setId(null);
        assertThat(plageCopie1).isNotEqualTo(plageCopie2);
    }
}
