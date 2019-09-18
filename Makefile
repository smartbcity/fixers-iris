NAME   	:= smartbcity/iris-api
IMG    	:= ${NAME}:${VERSION}
LATEST  := ${NAME}:latest

package:
	@docker build --build-arg VERSION=${VERSION} -f Dockerfile -t ${IMG} .

tag-latest:
	@docker tag ${IMG} ${LATEST}

push:
	@docker push ${NAME}

