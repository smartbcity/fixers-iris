package city.smartb.iris.vc;

import city.smartb.iris.ldproof.LdProof;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

class VerifiableCredentialBuilderTest {

    @Nested
    class LdProofTest {
        @Test
        void asJson() {
            LdProof proof = LdProof.fromMap(new LinkedHashMap<>());

            VerifiableCredential build = VerifiableCredentialBuilder.create().build(proof);

            Object json = build.asJson().get(LdProof.JSON_LD_PROOF);
            boolean isMap = json instanceof Map;
            Assertions.assertThat(isMap).isTrue();

        }
    }
}