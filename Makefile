STORYBOOK_DOCKERFILE	:= infra/docker/storybook/Dockerfile
STORYBOOK_NAME	   	 	:= smartbcity/iris-storybook
STORYBOOK_IMG	    	:= ${STORYBOOK_NAME}:${VERSION}

IRIS_VAULT_NAME		:= smartbcity/iris-vault-api-gateway
IRIS_VAULT_IMG		:= ${IRIS_VAULT_NAME}:${VERSION}
IRIS_VAULT_PACKAGE 	:= iris-vault:iris-vault-api-gateway

libs: package-java

package-java:
	@gradle clean build publish -P version=${VERSION}

package-storybook:
	@docker build -f ${STORYBOOK_DOCKERFILE} -t ${STORYBOOK_IMG} .

package-iris-vault:
	VERSION=${VERSION} IMAGE_NAME=${IRIS_VAULT_NAME} ./gradlew build ${IRIS_VAULT_PACKAGE}:bootBuildImage -x test
	@docker push ${IRIS_VAULT_IMG}
