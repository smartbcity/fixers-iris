
libs: package-java

package-java:
	@gradle clean build publish -P version=${VERSION}
