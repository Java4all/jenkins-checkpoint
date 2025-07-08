docker-build:
	docker build -t jenkins-checkpoint-builder .

docker-run:
	docker run --rm -v $(PWD):/app -v $(PWD)/.m2:/root/.m2 jenkins-checkpoint-builder
