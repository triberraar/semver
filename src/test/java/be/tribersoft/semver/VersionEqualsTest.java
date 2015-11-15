package be.tribersoft.semver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class VersionEqualsTest {

	@Test
	public void twoVersionsAreEqualWhenMajorMinorPatchAndPreReleaseAreSame() {
		Version version1 = new Version("1.2.3-SNAPSHOT.beta.1+12308987");
		Version version2 = new Version("1.2.3-SNAPSHOT.beta.1+12307");
		Version version3 = new Version("1.2.3-SNAPSHOT.beta.1");

		assertThat(version1).isEqualTo(version2);
		assertThat(version2).isEqualTo(version3);
	}

	@Test
	public void twoVersionsAreNotEqualWhenMajorDiffers() {
		Version version1 = new Version("2.2.3-SNAPSHOT.beta.1+12308987");
		Version version2 = new Version("1.2.3-SNAPSHOT.beta.1+12307");

		assertThat(version1).isNotEqualTo(version2);
	}

	@Test
	public void twoVersionsAreNotEqualWhenMinorDiffers() {
		Version version1 = new Version("1.1.3-SNAPSHOT.beta.1+12308987");
		Version version2 = new Version("1.2.3-SNAPSHOT.beta.1+12307");

		assertThat(version1).isNotEqualTo(version2);
	}

	@Test
	public void twoVersionsAreNotEqualWhenPatchDiffers() {
		Version version1 = new Version("1.2.1-SNAPSHOT.beta.1+12308987");
		Version version2 = new Version("1.2.3-SNAPSHOT.beta.1+12307");

		assertThat(version1).isNotEqualTo(version2);
	}

	@Test
	public void twoVersionsAreNotEqualWhenPreReleaseDiffers() {
		Version version1 = new Version("1.2.3-SNAPSHOT.beta+12308987");
		Version version2 = new Version("1.2.3-SNAPSHOT.beta.1+12307");

		assertThat(version1).isNotEqualTo(version2);
	}
}
