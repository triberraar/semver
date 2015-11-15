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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((major == null) ? 0 : major.hashCode());
		result = prime * result + ((minor == null) ? 0 : minor.hashCode());
		result = prime * result + ((patch == null) ? 0 : patch.hashCode());
		result = prime * result + ((preRelease == null) ? 0 : preRelease.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Version other = (Version) obj;
		if (major == null) {
			if (other.major != null)
				return false;
		} else if (!major.equals(other.major))
			return false;
		if (minor == null) {
			if (other.minor != null)
				return false;
		} else if (!minor.equals(other.minor))
			return false;
		if (patch == null) {
			if (other.patch != null)
				return false;
		} else if (!patch.equals(other.patch))
			return false;
		if (preRelease == null) {
			if (other.preRelease != null)
				return false;
		} else if (!preRelease.equals(other.preRelease))
			return false;
		return true;
	}

	public boolean isBefore(Version otherVersion) {
		if (this.equals(otherVersion)) {
			return false;
		}
		if (this.getMajor() < otherVersion.getMajor()) {
			return true;
		} else if (this.getMajor() > otherVersion.getMajor()) {
			return false;
		}
		if (this.getMinor() < otherVersion.getMinor()) {
			return true;
		} else if (this.getMinor() > otherVersion.getMinor()) {
			return false;
		}
		if (this.getPatch() < otherVersion.getPatch()) {
			return true;
		} else if (this.getPatch() > otherVersion.getPatch()) {
			return false;
		}
		if (this.getPreRelease().isPresent() && !otherVersion.getPreRelease().isPresent()) {
			return false;
		} else if (!this.getPreRelease().isPresent() && otherVersion.getPreRelease().isPresent()) {
			return true;
		}
		return comparePreRelease(otherVersion);
	}

	private boolean comparePreRelease(Version otherVersion) {
		String[] splittedPreRelease = this.getPreRelease().get().split("\\.");
		String[] otherSplittedPreRelease = otherVersion.getPreRelease().get().split("\\.");

		for (int i = 0; i < splittedPreRelease.length; i++) {
			if (i < otherSplittedPreRelease.length && !splittedPreRelease[i].equals(otherSplittedPreRelease[i])) {
				String preReleaseItem = splittedPreRelease[i];
				String otherPreReleaseItem = otherSplittedPreRelease[i];

				if (StringUtils.isNumeric(preReleaseItem) && !StringUtils.isNumeric(otherPreReleaseItem)) {
					return true;
				} else if (StringUtils.isNumeric(otherPreReleaseItem) && !StringUtils.isNumeric(preReleaseItem)) {
					return false;
				}
				if (StringUtils.isNumeric(preReleaseItem) && StringUtils.isNumeric(otherPreReleaseItem)) {
					return Integer.valueOf(preReleaseItem) < Integer.valueOf(otherPreReleaseItem);
				} else {
					return preReleaseItem.compareTo(otherPreReleaseItem) < 0;
				}
			}
			if (i == otherSplittedPreRelease.length) {
				return true;
			}
		}

		return false;

	}

}
