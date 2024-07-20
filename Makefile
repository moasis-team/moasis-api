postgres:
	docker run --name moasis -p 5432:5432 -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=moasis -d postgres:14-alpine
redis:
	docker run --name moasis-redis -p 6379:6379 -d redis:alpine
	docker run --name moasis -p 5433:5432 -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=moasis -d postgres:14-alpine

# 루트 프로젝트의 build.gradle 에서 아래의 버전 변경 후 실행
# allprojects {
#     version = '0.0.<VERSION>'
# }
build:
	./gradlew :api:jibDockerBuild
	./gradlew :image-processor:jibDockerBuild
	./gradlew setDockerComposeEnv
	docker-compose --env-file .env up

.PHONY: postgres build
