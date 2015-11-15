package be.tribersoft.semver;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class VersionUtilsSortTest {

	@Test
	public void sorts() {
		Version version1 = new Version("1.2.3");
		Version version2 = new Version("0.0.0");
		Version version3 = new Version("10.0.0");
		Version version4 = new Version("0.0.0");
		List<Version> input = Arrays.asList(version1, version2, version3, version4);

		List<Version> sorted = VersionUtils.sort(input);

		assertThat(sorted.size()).isEqualTo(4);
		assertThat(sorted.get(0)).isEqualTo(version2);
		assertThat(sorted.get(1)).isEqualTo(version2);
		assertThat(sorted.get(2)).isEqualTo(version1);
		assertThat(sorted.get(3)).isEqualTo(version3);
	}

	@Test
	public void sortSpec() {
		Version version1 = new Version("1.0.0");
		Version version2 = new Version("1.0.0-rc.1");
		Version version3 = new Version("1.0.0-beta.11");
		Version version4 = new Version("1.0.0-beta.2");
		Version version5 = new Version("1.0.0-beta");
		Version version6 = new Version("1.0.0-alpha.beta");
		Version version7 = new Version("1.0.0-alpha.1");
		Version version8 = new Version("1.0.0-alpha");

		List<Version> input = Arrays.asList(version1, version2, version3, version4, version5, version6, version7, version8);

		List<Version> sorted = VersionUtils.sort(input);

		assertThat(sorted.size()).isEqualTo(8);
		assertThat(sorted.get(0)).isEqualTo(version8);
		assertThat(sorted.get(1)).isEqualTo(version7);
		assertThat(sorted.get(2)).isEqualTo(version6);
		assertThat(sorted.get(3)).isEqualTo(version5);
		assertThat(sorted.get(4)).isEqualTo(version4);
		assertThat(sorted.get(5)).isEqualTo(version3);
		assertThat(sorted.get(6)).isEqualTo(version2);
		assertThat(sorted.get(7)).isEqualTo(version1);
	}

}
