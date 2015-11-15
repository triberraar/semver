package be.tribersoft.semver;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Version {
	private static final String REGEX = "(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(?:-((?:(?:\\d*[A-Za-z-][0-9A-Za-z-]*|(?:0|[1-9]\\d*))\\.)*(?:\\d*[A-Za-z-][0-9A-Za-z-]*|(?:0|[1-9]\\d*))))?(?:\\+((?:(?:[0-9A-Za-z-]+)\\.)*[0-9A-Za-z-]+))?";
	private static final Pattern SEMVER = Pattern.compile(REGEX);

	private Integer major;
	private Integer minor;
	private Integer patch;
	private Optional<String> preRelease;
	private Optional<String> buildMetadata;

	public Version(String version) {
		validateAndSet(version);
	}

	public Version(Integer major, Integer minor, Integer patch) {
		this(major, minor, patch, null, null);
	}

	public Version(Integer major, Integer minor, Integer patch, List<String> preRelease, List<String> buildMetadata) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.valueOf(major));
		stringBuilder.append(".");
		stringBuilder.append(String.valueOf(minor));
		stringBuilder.append(".");
		stringBuilder.append(String.valueOf(patch));
		if (preRelease != null && !preRelease.isEmpty()) {
			stringBuilder.append("-");
			stringBuilder.append(StringUtils.join(preRelease, '.'));
		}
		if (buildMetadata != null && !buildMetadata.isEmpty()) {
			stringBuilder.append("+");
			stringBuilder.append(StringUtils.join(buildMetadata, '.'));
		}
		validateAndSet(stringBuilder.toString());
	}

	private void validateAndSet(String version) {
		Matcher semverMatcher = SEMVER.matcher(version);
		if (!semverMatcher.matches()) {
			throw new NotASemverException(version);
		}
		if (semverMatcher.matches()) {
			major = Integer.valueOf(semverMatcher.group(1));
			minor = Integer.valueOf(semverMatcher.group(2));
			patch = Integer.valueOf(semverMatcher.group(3));
			preRelease = Optional.ofNullable(semverMatcher.group(4));
			buildMetadata = Optional.ofNullable(semverMatcher.group(5));
		}
	}

	public Integer getMajor() {
		return major;
	}

	public Integer getMinor() {
		return minor;
	}

	public Integer getPatch() {
		return patch;
	}

	public Optional<String> getPreRelease() {
		return preRelease;
	}

	public Optional<String> getBuildMetadata() {
		return buildMetadata;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.valueOf(major));
		stringBuilder.append(".");
		stringBuilder.append(String.valueOf(minor));
		stringBuilder.append(".");
		stringBuilder.append(String.valueOf(patch));
		if (getPreRelease().isPresent()) {
			stringBuilder.append("-");
			stringBuilder.append(getPreRelease().get());
		}
		if (getBuildMetadata().isPresent()) {
			stringBuilder.append("+");
			stringBuilder.append(getBuildMetadata().get());
		}
		return stringBuilder.toString();
	}

	public boolean isCompatbile(Version otherVersion) {
		return otherVersion.getMajor().equals(this.getMajor());
	}

}
