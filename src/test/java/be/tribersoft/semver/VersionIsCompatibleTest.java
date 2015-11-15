package be.tribersoft.semver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class VersionIsCompatibleTest {

	@Test
	public void TwoVersionsAreCompatibleIfSameMajorVersion() {
		Version version1 = new Version("1.2.3-1+6");
		Version version2 = new Version("1.12.90-7+beta");

		assertThat(version1.isCompatbile(version2)).isTrue();
		assertThat(version2.isCompatbile(version1)).isTrue();
	}

	@Test
	public void TwoVersionsAreNotCompatibleIfMajorVersionsAreDifferent() {
		Version version1 = new Version("1.2.3-1+2");
		Version version2 = new Version("2.2.3-1+2");

		assertThat(version1.isCompatbile(version2)).isFalse();
		assertThat(version2.isCompatbile(version1)).isFalse();
	}
}
