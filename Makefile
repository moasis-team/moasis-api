postgres:
	docker run --name moasis -p 5432:5432 -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=moasis -d postgres:14-alpine