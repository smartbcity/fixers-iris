REST_JAVA_NAME	    := smartbcity/iris-api
REST_JAVA_IMG	    := ${REST_JAVA_NAME}:${VERSION}
REST_JAVA_LATEST	:= ${REST_JAVA_NAME}:latest

test:
	@gradle -p test -i

package:
	@docker build -f Dockerfile -t ${REST_JAVA_IMG} .

push:
	@docker push ${REST_JAVA_IMG}

push-latest:
	@docker tag ${REST_JAVA_IMG} ${REST_JAVA_LATEST}
	@docker push ${REST_JAVA_LATEST}