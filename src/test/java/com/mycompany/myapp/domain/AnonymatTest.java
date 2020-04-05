package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AnonymatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anonymat.class);
        Anonymat anonymat1 = new Anonymat();
        anonymat1.setId(1L);
        Anonymat anonymat2 = new Anonymat();
        anonymat2.setId(anonymat1.getId());
        assertThat(anonymat1).isEqualTo(anonymat2);
        anonymat2.setId(2L);
        assertThat(anonymat1).isNotEqualTo(anonymat2);
        anonymat1.setId(null);
        assertThat(anonymat1).isNotEqualTo(anonymat2);
    }
}
