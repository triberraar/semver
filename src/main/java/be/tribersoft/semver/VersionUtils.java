package be.tribersoft.semver;

import java.util.ArrayList;
import java.util.List;

public class VersionUtils {

	public static List<Version> sort(List<Version> input) {
		List<Version> output = new ArrayList<>(input);
		output.sort(Version::compareTo);
		return output;
	}

}
