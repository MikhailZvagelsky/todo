
### Run in Docker

```shell
docker container run --name notifier -e "PORT=8091" -p 8091:8091 mikhailzvagelsky/devopswithkubernetes_todo
```

### Run in k8s

Run k8s cluster, forwarding host's ports to ports of agent and load-balancer.

```shell
k3d cluster create --port 8082:30080@agent:0 -p 8081:80@loadbalancer --agents 2
```

```shell
kubectl apply -f manifests/deployment.yml
```

#### Option 1.
Use ports-forwarding:
```shell
kubectl port-forward todo-app-deployment-xxx 8082:8091
```
App is available `http://localhost:8082/`

#### Option 2.
Use NodePort service:
```shell
kubectl apply -f manifests/node-port-service.yml
```
App is available `http://localhost:8082/`

#### Option 4.
Use Ingress.
```shell
kubectl apply -f manifests/cluster-ip-service.yml
kubectl apply -f manifests/ingress.yml
```
App is available on port 80: `http://localhost:8081/`
