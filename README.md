#Semver

A java library to parse semver notations. It checks validity based on the [offical spec version 2.0.0](http://semver.org/) using the provided [regex](https://github.com/mojombo/semver/issues/266#issuecomment-135765386). 
It also provides functionality as checking compatability and order between different semver strings. It can also order a list of semver strings.

It is written in java 8.

## Stati
### Development
![Travis status](https://img.shields.io/travis/triberraar/semver/develop.svg)
![Coveralls status](https://img.shields.io/coveralls/triberraar/semver/develop.svg)

### Master
![Travis status](https://img.shields.io/travis/triberraar/semver/master.svg)
![Coveralls status](https://img.shields.io/coveralls/triberraar/semver/master.svg)

## Maven
It is available as a maven dependency from bintray by using the following repository:

```xml
<repository>
	<id>bintray-triberraar-maven</id>
	<name>bintray</name>
	<url>http://dl.bintray.com/triberraar/maven</url>
</repository>
```

Add it as a dependency:

```xml
<dependency>
	<groupId>be.tribersoft</groupId>
	<artifactId>semver</artifactId>
	<version>${semver.version}</version>
</dependency>
```

## API
This library is build around Version it provides following constructors:
* with a String: parses the String to a semver.
* with the different components of semver.
If the construction fails the NotASemverException is thrown

The class provides the following functionality:
* equals: Checks if two versions are equal as defined in the spec.
* isCompatible: Checks if two versions are compatible as defined in the spec.
* isBefore: Checks if the version is before another version as defined in the spec.

A utility class, VersionUtils, is provided as well with the following functionality:
* sort: Sorts a lists of versions as defined in the spec.