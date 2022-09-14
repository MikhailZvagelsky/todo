Build image
```shell
docker image build -t todo_script .
```

Re-tag it and put to registry:
```shell
docker image tag todo_script:latest mikhailzvagelsky/todo_script:v1
```
```shell
docker image push mikhailzvagelsky/todo_script:v1
```

Run container
```shell
docker container run --name script --rm -e "BACKEND_URL=http://localhost:8091/todos" mikhailzvagelsky/todo_script:v1
```