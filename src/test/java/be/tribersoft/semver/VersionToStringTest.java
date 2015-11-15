package be.tribersoft.semver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class VersionToStringTest {

	private static final String FULL = "1.2.3-rc1.1-x+rc1.01-o78";
	private static final String ONLY_MAJOR_MINOR_PATCH_BUILD_METADATA = "1.2.3+rc1.01.o-78";
	private static final String ONLY_MAJOR_MINOR_PATCH_PRE_RELEASE = "1.2.3-rc.1-x";
	private static final String OONLY_MAJOR_MINOR_PATCH = "1.2.3";

	@Test
	public void createsAValidSemverStringWhenOnlyMajorMinorAndPatch() {
		Version version = new Version(OONLY_MAJOR_MINOR_PATCH);

		assertThat(version.toString()).isEqualTo(OONLY_MAJOR_MINOR_PATCH);
	}

	@Test
	public void createsAValidSemverStringWhenOnlyMajorMinorPatchAndPreRelease() {
		Version version = new Version(ONLY_MAJOR_MINOR_PATCH_PRE_RELEASE);

		assertThat(version.toString()).isEqualTo(ONLY_MAJOR_MINOR_PATCH_PRE_RELEASE);
	}

	@Test
	public void createsAValidSemverStringWhenOnlyMajorMinorPatchAndBuildMetadata() {
		Version version = new Version(ONLY_MAJOR_MINOR_PATCH_BUILD_METADATA);

		assertThat(version.toString()).isEqualTo(ONLY_MAJOR_MINOR_PATCH_BUILD_METADATA);
	}

	@Test
	public void createsAValidSemverStringWhenFull() {
		Version version = new Version(FULL);

		assertThat(version.toString()).isEqualTo(FULL);
	}

}
