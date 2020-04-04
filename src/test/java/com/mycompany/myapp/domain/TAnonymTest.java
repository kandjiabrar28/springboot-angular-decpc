package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TAnonymTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TAnonym.class);
        TAnonym tAnonym1 = new TAnonym();
        tAnonym1.setId(1L);
        TAnonym tAnonym2 = new TAnonym();
        tAnonym2.setId(tAnonym1.getId());
        assertThat(tAnonym1).isEqualTo(tAnonym2);
        tAnonym2.setId(2L);
        assertThat(tAnonym1).isNotEqualTo(tAnonym2);
        tAnonym1.setId(null);
        assertThat(tAnonym1).isNotEqualTo(tAnonym2);
    }
}
