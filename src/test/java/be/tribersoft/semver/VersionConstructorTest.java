package be.tribersoft.semver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class VersionConstructorTest {

	@Test(expected = NotASemverException.class)
	public void failsFailsForRandomString() {
		new Version("random stuff");
	}

	@Test(expected = NotASemverException.class)
	public void failsWhenOnlyMajorVersion() {
		new Version("1");
	}

	@Test(expected = NotASemverException.class)
	public void failsWithLeadingZeroInMajor() {
		new Version("01.1.1");
	}

	@Test(expected = NotASemverException.class)
	public void failsWhenOnlyMajorAndMinorVersion() {
		new Version("1.1");
	}

	@Test(expected = NotASemverException.class)
	public void failsWithLeadingZeroInMinor() {
		new Version("1.01.1");
	}

	@Test(expected = NotASemverException.class)
	public void failsWithLeadingZeroInPatch() {
		new Version("1.1.01");
	}

	@Test(expected = NotASemverException.class)
	public void failsWhenPreRelaseVersionIsProvidedButEmpty() {
		new Version("1.1.1-");
	}

	@Test(expected = NotASemverException.class)
	public void failsWhenPreRelaseVersionIsProvidedButOneIdentifierEmpty() {
		new Version("1.1.1-aplha.");
	}

	@Test(expected = NotASemverException.class)
	public void failsWhenBuildMetaDataIsProvidedAndNumericWithLeadingZero() {
		new Version("1.1.1-01");
	}

	@Test(expected = NotASemverException.class)
	public void failsWhenBuildMetaDataIsProvidedButEmpty() {
		new Version("1.1.1+");
	}

	@Test(expected = NotASemverException.class)
	public void failsWhenBuildMetaDataIsProvidedButOneIdentifierEmpty() {
		new Version("1.1.1+z.");
	}

	@Test(expected = NotASemverException.class)
	public void failsWhenBuildMetaDataIsProvidedButOneIdentifierHasAPlusSign() {
		new Version("1.1.1+z.+1");
	}

	@Test
	public void versionWithOnlyMajorMinorAndPath() {
		Version version = new Version("1.2.3");

		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version("1.0.0");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(0);
		assertThat(version.getPatch()).isEqualTo(0);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version("0.1.0");
		assertThat(version.getMajor()).isEqualTo(0);
		assertThat(version.getMinor()).isEqualTo(1);
		assertThat(version.getPatch()).isEqualTo(0);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version("0.0.1");
		assertThat(version.getMajor()).isEqualTo(0);
		assertThat(version.getMinor()).isEqualTo(0);
		assertThat(version.getPatch()).isEqualTo(1);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version("0.0.0");
		assertThat(version.getMajor()).isEqualTo(0);
		assertThat(version.getMinor()).isEqualTo(0);
		assertThat(version.getPatch()).isEqualTo(0);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isFalse();
	}

	@Test
	public void versionWithOnlyMajorMinorPatchAndPreRelease() {
		Version version = new Version("1.2.3-beta");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("beta");
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version("1.2.3-beta.2");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("beta.2");
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version("1.2.3-1.beta");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("1.beta");
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version("1.2.3-beta.alpha");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("beta.alpha");
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version("1.2.3-rc.11");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("rc.11");
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version("1.2.3-rc1.alpha");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("rc1.alpha");
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version("1.2.3-rc1.flying-unicorn");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("rc1.flying-unicorn");
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version("1.2.3-x.y.z-1.2.3");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("x.y.z-1.2.3");
		assertThat(version.getBuildMetadata().isPresent()).isFalse();
	}

	@Test
	public void versionWithOnlyMajorMinorPatchAndBuildMetadata() {
		Version version = new Version("1.2.3+beta");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("beta");

		version = new Version("1.2.3+beta.2");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("beta.2");

		version = new Version("1.2.3+01.2");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("01.2");

		version = new Version("1.2.3+1.beta");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("1.beta");

		version = new Version("1.2.3+beta.alpha");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("beta.alpha");

		version = new Version("1.2.3+rc.11");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("rc.11");

		version = new Version("1.2.3+rc1.alpha");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("rc1.alpha");

		version = new Version("1.2.3+rc1.flying-unicorn");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("rc1.flying-unicorn");

		version = new Version("1.2.3+x.y.z-1.2.3");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("x.y.z-1.2.3");
	}

	@Test
	public void versionWithMajorMinorPatchPreReleaseAndBuildMetadata() {
		Version version = new Version("1.2.3-beta+beta");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("beta");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("beta");

		version = new Version("1.2.3-beta.2+beta.2");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("beta.2");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("beta.2");

		version = new Version("1.2.3-1.2+01.2");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("1.2");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("01.2");

		version = new Version("1.2.3-1.beta+1.beta");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("1.beta");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("1.beta");

		version = new Version("1.2.3-beta.alpha+beta.alpha");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("beta.alpha");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("beta.alpha");

		version = new Version("1.2.3-rc.11+rc.11");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("rc.11");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("rc.11");

		version = new Version("1.2.3-rc1.alpha+rc1.alpha");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("rc1.alpha");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("rc1.alpha");

		version = new Version("1.2.3-rc1.flying-unicorn+rc1.flying-unicorn");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("rc1.flying-unicorn");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("rc1.flying-unicorn");

		version = new Version("1.2.3-x.y.z-1.2.3+x.y.z-1.2.3");
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("x.y.z-1.2.3");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("x.y.z-1.2.3");
	}

}
