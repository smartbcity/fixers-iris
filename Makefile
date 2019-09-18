NAME   	:= smartbcity/iris-api
IMG    	:= ${NAME}:${VERSION}
LATEST  := ${NAME}:latest

package:
	@docker build --build-arg VERSION=${VERSION} -f Dockerfile -t ${IMG} .

push:
	@docker push ${IMG}

push-latest:
    @docker tag ${IMG} ${LATEST}
	@docker push ${LATEST}