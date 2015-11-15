package be.tribersoft.semver;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class VersionConstructorWithPartsTest {

	@Test(expected = NotASemverException.class)
	public void failsWhenOnlyMajorVersion() {
		new Version(1, null, null);
	}

	@Test(expected = NotASemverException.class)
	public void failsWhenOnlyMajorVersionListsAsEmptyList() {
		new Version(1, null, null, new ArrayList<>(), new ArrayList<>());
	}

	@Test(expected = NotASemverException.class)
	public void failsWhenOnlyMinorVersion() {
		new Version(null, 1, null);
	}

	@Test(expected = NotASemverException.class)
	public void failsWhenOnlyPatchVersion() {
		new Version(null, null, 1);
	}

	@Test(expected = NotASemverException.class)
	public void failsWhenPreRelaseVersionIsProvidedButEmpty() {
		new Version(1, 1, 1, Arrays.asList(""), null);
	}

	@Test(expected = NotASemverException.class)
	public void failsWhenPreReleaseIsProvidedAndNumericWithLeadingZero() {
		new Version(1, 1, 1, Arrays.asList("01"), null);
	}

	@Test(expected = NotASemverException.class)
	public void failsWhenBuildMetaDataIsProvidedButEmpty() {
		new Version(1, 1, 1, null, Arrays.asList(""));
	}

	@Test(expected = NotASemverException.class)
	public void failsWhenBuildMetaDataIsProvidedButOneIdentifierHasAPlusSign() {
		new Version(1, 1, 1, null, Arrays.asList("1", "+2"));
	}

	@Test
	public void versionWithOnlyMajorMinorAndPath() {
		Version version = new Version(1, 2, 3);

		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version(1, 0, 0);
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(0);
		assertThat(version.getPatch()).isEqualTo(0);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version(0, 1, 0);
		assertThat(version.getMajor()).isEqualTo(0);
		assertThat(version.getMinor()).isEqualTo(1);
		assertThat(version.getPatch()).isEqualTo(0);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version(0, 0, 1);
		assertThat(version.getMajor()).isEqualTo(0);
		assertThat(version.getMinor()).isEqualTo(0);
		assertThat(version.getPatch()).isEqualTo(1);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version(0, 0, 0);
		assertThat(version.getMajor()).isEqualTo(0);
		assertThat(version.getMinor()).isEqualTo(0);
		assertThat(version.getPatch()).isEqualTo(0);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isFalse();
	}

	@Test
	public void versionWithOnlyMajorMinorPatchAndPreRelease() {
		Version version = new Version(1, 2, 3, Arrays.asList("beta"), null);
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("beta");
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version(1, 2, 3, Arrays.asList("beta", "2"), null);
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("beta.2");
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version(1, 2, 3, Arrays.asList("1", "beta"), null);
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("1.beta");
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version(1, 2, 3, Arrays.asList("beta", "alpha"), null);
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("beta.alpha");
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version(1, 2, 3, Arrays.asList("rc", "11"), null);
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("rc.11");
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version(1, 2, 3, Arrays.asList("rc1", "alpha"), null);
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("rc1.alpha");
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version(1, 2, 3, Arrays.asList("rc1", "flying-unicorn"), null);
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("rc1.flying-unicorn");
		assertThat(version.getBuildMetadata().isPresent()).isFalse();

		version = new Version(1, 2, 3, Arrays.asList("x", "y", "z-1.2.3"), null);
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("x.y.z-1.2.3");
		assertThat(version.getBuildMetadata().isPresent()).isFalse();
	}

	@Test
	public void versionWithOnlyMajorMinorPatchAndBuildMetadata() {
		Version version = new Version(1, 2, 3, null, Arrays.asList("beta"));
		;
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("beta");

		version = new Version(1, 2, 3, null, Arrays.asList("beta", "2"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("beta.2");

		version = new Version(1, 2, 3, null, Arrays.asList("01", "2"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("01.2");

		version = new Version(1, 2, 3, null, Arrays.asList("1", "beta"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("1.beta");

		version = new Version(1, 2, 3, null, Arrays.asList("beta", "alpha"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("beta.alpha");

		version = new Version(1, 2, 3, null, Arrays.asList("rc", "11"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("rc.11");

		version = new Version(1, 2, 3, null, Arrays.asList("rc1", "alpha"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("rc1.alpha");

		version = new Version(1, 2, 3, null, Arrays.asList("rc1", "flying-unicorn"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("rc1.flying-unicorn");

		version = new Version(1, 2, 3, null, Arrays.asList("x", "y", "z-1.2.3"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isFalse();
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("x.y.z-1.2.3");
	}

	@Test
	public void versionWithMajorMinorPatchPreReleaseAndBuildMetadata() {
		Version version = new Version(1, 2, 3, Arrays.asList("beta"), Arrays.asList("beta"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("beta");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("beta");

		version = new Version(1, 2, 3, Arrays.asList("beta", "2"), Arrays.asList("beta", "2"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("beta.2");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("beta.2");

		version = new Version(1, 2, 3, Arrays.asList("1", "2"), Arrays.asList("01", "2"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("1.2");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("01.2");

		version = new Version(1, 2, 3, Arrays.asList("1", "beta"), Arrays.asList("1", "beta"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("1.beta");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("1.beta");

		version = new Version(1, 2, 3, Arrays.asList("beta", "alpha"), Arrays.asList("beta", "alpha"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("beta.alpha");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("beta.alpha");

		version = new Version(1, 2, 3, Arrays.asList("rc", "11"), Arrays.asList("rc", "11"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("rc.11");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("rc.11");

		version = new Version(1, 2, 3, Arrays.asList("rc1", "alpha"), Arrays.asList("rc1", "alpha"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("rc1.alpha");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("rc1.alpha");

		version = new Version(1, 2, 3, Arrays.asList("rc1", "flying-unicorn"), Arrays.asList("rc1", "flying-unicorn"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("rc1.flying-unicorn");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("rc1.flying-unicorn");

		version = new Version(1, 2, 3, Arrays.asList("x", "y", "z-1.2.3"), Arrays.asList("x", "y", "z-1.2.3"));
		assertThat(version.getMajor()).isEqualTo(1);
		assertThat(version.getMinor()).isEqualTo(2);
		assertThat(version.getPatch()).isEqualTo(3);
		assertThat(version.getPreRelease().isPresent()).isTrue();
		assertThat(version.getPreRelease().get()).isEqualTo("x.y.z-1.2.3");
		assertThat(version.getBuildMetadata().isPresent()).isTrue();
		assertThat(version.getBuildMetadata().get()).isEqualTo("x.y.z-1.2.3");
	}

}
