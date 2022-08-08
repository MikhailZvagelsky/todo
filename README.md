### Run in k8s

Run k8s cluster, forwarding host's ports to ports of agent and load-balancer.

```shell
k3d cluster create -p 8081:80@loadbalancer --agents 2
```

For the PersistentVolume to work we need to create the local path in the node we are binding it to.
Since our k3d cluster runs via docker let's create a directory at
`/tmp/kube`
in the `k3d-k3s-default-agent-0` container:

```shell
docker exec k3d-k3s-default-agent-0 mkdir -p /tmp/kube
````

Deploy persistent volume and persistent volume claim fro the folder
`/Users/mikhail.zvagelsky/kubernetes/devopswithkubernetes/exersize_1-11/manifests/`
And deploy the app:

```shell
kubectl apply -f manifests/
```

App is available on port 8081: `http://localhost:8081/`.
