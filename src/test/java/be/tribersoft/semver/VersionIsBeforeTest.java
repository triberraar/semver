package be.tribersoft.semver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class VersionIsBeforeTest {

	@Test
	public void returnsTrueWhenMajorIsLower() {
		Version version1 = new Version("1.2.3");
		Version version2 = new Version("2.2.3");

		assertThat(version1.isBefore(version2)).isTrue();
	}

	@Test
	public void returnsFalseWhenMajorIsHigher() {
		Version version1 = new Version("2.2.3");
		Version version2 = new Version("1.2.3");

		assertThat(version1.isBefore(version2)).isFalse();
	}

	@Test
	public void returnsTrueWhenMajorIsSameAndMinorIsLower() {
		Version version1 = new Version("1.1.3");
		Version version2 = new Version("1.2.3");

		assertThat(version1.isBefore(version2)).isTrue();
	}

	@Test
	public void returnsFalseWhenMajorIsSameAndMinorIsHigher() {
		Version version1 = new Version("1.2.3");
		Version version2 = new Version("1.1.3");

		assertThat(version1.isBefore(version2)).isFalse();
	}

	@Test
	public void returnsTrueWhenMajorAndMinorsAreSameAndPatchIsLower() {
		Version version1 = new Version("1.2.2");
		Version version2 = new Version("1.2.3");

		assertThat(version1.isBefore(version2)).isTrue();
	}

	@Test
	public void returnsFalseWhenMajorAndMinorAreSameAndPatchIsHigher() {
		Version version1 = new Version("1.2.3");
		Version version2 = new Version("1.2.2");

		assertThat(version1.isBefore(version2)).isFalse();
	}

	@Test
	public void returnsFalseWhenMajorMinorAndPatchAreSameButThisHasNoPreReleaseAndOtherHasPreRelease() {
		Version version1 = new Version("1.2.3");
		Version version2 = new Version("1.2.3-SNAPSHOT");

		assertThat(version1.isBefore(version2)).isFalse();
	}

	@Test
	public void returnsTrueWhenMajorMinorAndPatchAreSameButThisHasPreReleaseAndOtherHasNoPreRelease() {
		Version version1 = new Version("1.2.3-SNAPSHOT");
		Version version2 = new Version("1.2.3");

		assertThat(version1.isBefore(version2)).isTrue();
	}

	@Test
	public void returnsTrueWhenMajorMinorAndPatchAreSameAndThisHasPreReleaseNumericAndOtherHasPreReleaseNonNumeric() {
		Version version1 = new Version("1.2.3-1");
		Version version2 = new Version("1.2.3-SNAPSHOT");

		assertThat(version1.isBefore(version2)).isTrue();
	}

	@Test
	public void returnsFalseWhenMajorMinorAndPatchAreSameAndThisHasPreReleaseNonNumericAndOtherHasPreReleaseNumeric() {
		Version version1 = new Version("1.2.3-SNAPSHOT");
		Version version2 = new Version("1.2.3-1");

		assertThat(version1.isBefore(version2)).isFalse();
	}

	@Test
	public void returnsTrueWhenMajorMinorAndPatchAreSameAndBothHavePreReleaseNumericAndThisIsPreReleaseIsLower() {
		Version version1 = new Version("1.2.3-1");
		Version version2 = new Version("1.2.3-2");

		assertThat(version1.isBefore(version2)).isTrue();
	}

	@Test
	public void returnsFalseWhenMajorMinorAndPatchAreSameAndBothHavePreReleaseNumericAndThisIsPreReleaseIsHigher() {
		Version version1 = new Version("1.2.3-12");
		Version version2 = new Version("1.2.3-2");

		assertThat(version1.isBefore(version2)).isFalse();
	}

	@Test
	public void returnsTrueWhenMajorMinorAndPatchAreSameAndBothHavePreReleaseNonNumericAndThisIsPreReleaseIsBefore() {
		Version version1 = new Version("1.2.3-a");
		Version version2 = new Version("1.2.3-b");

		assertThat(version1.isBefore(version2)).isTrue();
	}

	@Test
	public void returnsFalseWhenMajorMinorAndPatchAreSameAndBothHavePreReleaseNonNumericAndThisIsPreReleaseIsAfter() {
		Version version1 = new Version("1.2.3-b");
		Version version2 = new Version("1.2.3-a");

		assertThat(version1.isBefore(version2)).isFalse();
	}

	@Test
	public void returnsTruextWhenMajorMinorAndPatchAreSameAndThisHasSmallerPreReleaseSet() {
		Version version1 = new Version("1.2.3-alpha");
		Version version2 = new Version("1.2.3-alpha.1");

		assertThat(version1.isBefore(version2)).isTrue();
	}

	@Test
	public void traversesPreReleaseVersionsWhenMajorMinorAndPatchAreSame() {
		Version version1 = new Version("1.2.3-1.2.3.a");
		Version version2 = new Version("1.2.3-1.2.3.b");

		assertThat(version1.isBefore(version2)).isTrue();
	}

	@Test
	public void returnsFalseWhenVersionsAreEqual() {
		Version version1 = new Version("1.2.3-a");
		Version version2 = new Version("1.2.3-a");

		assertThat(version1.isBefore(version2)).isFalse();
	}
}
