STORYBOOK_DOCKERFILE	:= infra/docker/storybook/Dockerfile
STORYBOOK_NAME	   	 	:= smartbcity/iris-storybook
STORYBOOK_IMG	    	:= ${STORYBOOK_NAME}:${VERSION}

libs: package-java

package-java:
	@gradle clean build publish -P version=${VERSION}

package-storybook:
	@docker build -f ${STORYBOOK_DOCKERFILE} -t ${STORYBOOK_IMG} .
