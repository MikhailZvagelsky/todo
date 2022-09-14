[![CI/CD GKE](https://github.com/MikhailZvagelsky/todo/actions/workflows/google-cloud-pipeline.yml/badge.svg)](https://github.com/MikhailZvagelsky/todo/actions/workflows/google-cloud-pipeline.yml)

### 1. Run in development (local) mode

#### Postgres settings

The application uses Postgres, and reads postgres url, username and password
from files with same names in folder specified in 
the `POSTGRES_CREDENTIALS_FOLDER` environment variable. 

For local development the `POSTGRES_CREDENTIALS_FOLDER` variable
is defined in the [local.env](local/local.env) 
file alongside with the corresponding [folder](local/secrets).

1. Run a Postgres server on URL specified in the [url](local/secrets/url) file.
   Run following commands in a psql console.

2. Create database specified in the url, used to store application data. 
```shell
create database "todo";
```

3. Create user account as specified in the [username](local/secrets/username),
and the [password](local/secrets/password) files.
```shell
create user "localtodouser" with encrypted password 'localTodoPassword';
```

4. Grant privileges for the user.
```shell
grant all privileges on database "todo" to "localtodouser";
```

#### Run backend

Application expects that environment variable `POSTGRES_CREDENTIALS_FOLDER`
points to a folder with correct postgres credentials.

Run main application class [TodoApplication](src/main/java/com/example/todo/TodoApplication.java).

App is listening on port 8091: http://localhost:8091/todos, http://localhost:8091/daily_image

#### Run frontend

Check that the [.env](src/main/frontend/.env) file contains the correct backend url
(http://localhost:8091) it will be used by React app to send browser requests to the backend.

```shell
cd frontend
npm start
```

Web page http://localhost:3000/

#### Run script to append link to a Wiki page to todo list.

Go to the [cronJobs](src/main/cronJobs) folder.
Set url for fetching toto list:
```shell
export BACKEND_URL=http://localhost:8091/todos
```
and run the [script](src/main/cronJobs/dailyTodo/createTodo.sh)
```shell
 ./createTodo.sh
```

### Kubernetes Secret manifest management

Do not commit actual secret.yml to VCS.

Encrypt secret with the SOPS tool, and the 'age' encryptor.
Generate private/public key pair at first, and store it in a file:
```shell
age-keygen -o key.txt
```
Encrypt secret.yml, only username and password are actually encrypted,
use public key for that:
:
```shell
sops --encrypt --age age1ssqylrszr8sj6ys8glqhrr7w0zkdve80kyndcsuhu3qsx3zywaxsxph2z0 --encrypted-regex '^(username|password)$' secret.yml > secret.enc.yml
```

To decrypt, story a private key as an environment variable:
```shell
export SOPS_AGE_KEY=AGE-SECRET-KEY-...
```
and decrypt:
```shell
sops --decrypt secret.enc.yml > secret.decrypted.yml
```

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

Create secret as described in the [secret management](#kubernetes-secret-manifest-management) section:
```shell
export SOPS_AGE_KEY=AGE-SECRET-KEY-...
```
```shell
sops --decrypt secret.enc.yaml | kubectl apply -f -
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

Deploy [manifests](manifests/GKE), do not forget to decrypt the secret.yml.