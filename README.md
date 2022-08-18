### Run in k8s

Run k8s cluster, forwarding host's ports 8081 (frontend endpoint), and 8091 (backend endpoint) to ports load-balancer.

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

Deploy persistent volume and persistent volume claim:

```shell
kubectl apply -f manifests/volume/
```

And deploy the app:

```shell
kubectl apply -f manifests/
```

App is available on port 8081: http://localhost:8081/
You also can send requests to the backend directly http://localhost:8081/todos , http://localhost:8081/daily_image 
