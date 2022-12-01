package city.smartb.iris.vc;

import city.smartb.iris.ldproof.LdProof;
import com.google.common.collect.ImmutableList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
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

        @Test
        void asJsonWithCutomVCRef() {
            LdProof proof = LdProof.fromMap(new LinkedHashMap<>());

            List<VCRef> objs = VCRef.list();
            VerifiableCredential build = VerifiableCredentialBuilder.create().with("vcref", objs).build(proof);

            Object json = build.asJson().get(LdProof.JSON_LD_PROOF);
            boolean isMap = json instanceof Map;
            Assertions.assertThat(isMap).isTrue();

        }
    }

    public static class VCRef {

        public static List<VCRef> list() {
            return ImmutableList.of(
                    new VCRef().setId("1").setJws("jws1"),
                    new VCRef().setId("2").setJws("jws2")
            );
        }
        private String jws;
        private String id;

        public String getJws() {
            return jws;
        }

        public VCRef setJws(String jws) {
            this.jws = jws;
            return this;
        }

        public String getId() {
            return id;
        }

        public VCRef setId(String id) {
            this.id = id;
            return this;
        }
    }
}