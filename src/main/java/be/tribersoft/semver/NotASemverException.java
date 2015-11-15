package be.tribersoft.semver;

public class NotASemverException extends RuntimeException {

	public NotASemverException(String version) {
		super("Version string: '" + version + "' is not a valid semver");
	}

}
