[![CI/CD GKE](https://github.com/MikhailZvagelsky/todo/actions/workflows/google-cloud-pipeline.yml/badge.svg)](https://github.com/MikhailZvagelsky/todo/actions/workflows/google-cloud-pipeline.yml)


### Run in Idea.

#### Postgres settings

The application stores data in Postgres.
An invironment variable specified in the `local.env` file in the root folder,
points to a folder where postgres credentials are stored.

Run a Postgres server on URL specified in the configuration, and create
required database, a user account, and privileges for the user.

```shell
create database ${POSTGRES_DB};
create user ${POSTGRES_USERNAME} with encrypted password '${POSTGRES_PASSWORD}';
grant all privileges on database ${POSTGRES_DB} to ${POSTGRES_USERNAME};
```

#### Run backend

Run main application class `TodoApplication`.
Application expects environment variable as specified in the `local.env` file in the root folder.

App is listening on port 8091: http://localhost:8091/todos, http://localhost:8091/daily_image

#### Run frontend

```shell
cd frontend
npm start
```

Web page http://localhost:3000/

#### Run script to append link to a Wiki page to todo list.

Run the shell [script](src/main/cronJobs/dailyTodo/createTodo.sh) 
as described in the [README](src/main/cronJobs/dailyTodo/README.md).


### Run in k3s Kubernetes cluster

Run k8s cluster, forwarding host's ports 8081 (frontend endpoint), and 8091 (backend endpoint) to load-balancer port 80.

```shell
k3d cluster create -p 8081:80@loadbalancer -p 8091:80@loadbalancer --agents 2
```

For the PersistentVolume to work we need to create the local path in the node we are binding it to.
Since our k3d cluster runs via docker let's create a directory at
`/tmp/kube`
in the `k3d-k3s-default-agent-0` container:

```shell
docker exec k3d-k3s-default-agent-0 mkdir -p /tmp/kube
````

Create namespace:
```shell
kubectl apply -f manifests/k3s/namespace.yml
```

Deploy persistent volume and persistent volume claim:

```shell
kubectl apply -f manifests/k3s/volume/
```

Create secret:
```shell
kubectl apply -f manifests/k3s/secret.yml
```

Deploy postgres:
```shell
kubectl apply -f manifests/k3s/postgres.yml

```

Deploy backend:

```shell
kubectl apply -f manifests/k3s/java-backend.yml
```

Deploy frontend:
```shell
kubectl apply -f manifests/k3s/react-frontend.yml
```

Deploy ingress:
```shell
kubectl apply -f manifests/k3s/ingress.yml
```

Run containerised shell script which adds link to a Wiki page to todo list:
```shell
kubectl apply -f manifests/k3s/todo-job.yml
```

Deploy a cron job that adds link to a Wiki page to todo list every 5 minutes:
```shell
kubectl apply -f manifests/k3s/todo-cronjob.yml
```

App is available on port 8081: http://localhost:8081/
You also can send requests to the backend directly http://localhost:8081/todos , http://localhost:8081/daily_image 

### Deploy in Google Kubernetes Engine

Deploy [manifests](manifests/GKE).