package city.smartb.iris.vc;

import city.smartb.iris.ldproof.LdProof;
import com.google.common.collect.ImmutableList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

class VerifiableCredentialTest {

    @Nested
    class CredentialSubject {

        @Test
        void shouldRetrunString() {
            String expected = "CredentialSubject as string";

            VerifiableCredential credential = createCredentialSubject(expected);
            String subject = credential.getCredentialSubject(String.class);

            Assertions.assertThat(subject).isEqualTo("CredentialSubject as string");

        }

        @Test
        void shouldReturnStringList() {
            List<String> expected = ImmutableList.of("1", "2");

            VerifiableCredential credential = createCredentialSubject(expected);
            List<String> subject = credential.getCredentialSubject().asListObjects(String.class);

            Assertions.assertThat(subject).containsOnly("1", "2");

        }

        @Test
        void shouldReturnPojo() {
            Pojo expected = new Pojo("1");

            VerifiableCredential credential = createCredentialSubject(expected);
            Pojo subject = credential.getCredentialSubject(Pojo.class);

            Assertions.assertThat(subject.getAttr()).isEqualTo("1");

        }

        @Test
        void shouldReturnPojoList() {
            List<Pojo> expected = ImmutableList.of(new Pojo("1"), new Pojo("2"));

            VerifiableCredential credential = createCredentialSubject(expected);
            List<Pojo> subject = credential.getCredentialSubject().asListObjects(Pojo.class);

            Assertions.assertThat(subject).containsOnly(new Pojo("1"), new Pojo("2"));

        }
    }

    public <T> VerifiableCredential createCredentialSubject(T subject) {
        return VerifiableCredentialBuilder.<T>create()
                .withContextDefault()
                .withIssuanceDateNow()
                .withId("http://smartb.city/credentials/1872")
                .withIssuer("Unit Test")
                .withCredentialSubject(subject)
                .build(LdProof.fromMap(new LinkedHashMap<>()));
    }

    public <T> VerifiableCredential createCredentialSubject(List<T> subject) {
        return VerifiableCredentialBuilder.<T>create()
                .withContextDefault()
                .withIssuanceDateNow()
                .withId("http://smartb.city/credentials/1872")
                .withIssuer("Unit Test")
                .withCredentialSubject(subject)
                .build(LdProof.fromMap(new LinkedHashMap<>()));
    }

    public static class Pojo {
        private String attr;


        public Pojo() {
        }

        public Pojo(String attr) {
            this.attr = attr;
        }

        public String getAttr() {
            return attr;
        }

        public void setAttr(String attr) {
            this.attr = attr;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pojo pojo = (Pojo) o;
            return Objects.equals(attr, pojo.attr);
        }

        @Override
        public int hashCode() {
            return Objects.hash(attr);
        }

        @Override
        public String toString() {
            return "Pojo{" +
                    "attr='" + attr + '\'' +
                    '}';
        }
    }
}