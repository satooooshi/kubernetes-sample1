developed in minikube

kubectl delete -k overlays/develop/
kubectl apply -k overlays/develop/

./aa.sh
minikube delete
minikube start --image-mirror-country cn \
    --iso-url=https://kubernetes.oss-cn-hangzhou.aliyuncs.com/minikube/iso/minikube-v1.5.0.iso \
    --registry-mirror=https://v00rv7a3.mirror.aliyuncs.com \
    --driver="none"

minikube addons enable ingress

https://gitlab.com/creationline/thinkit-kubernetes-sample1 のkubernetes manifest管理用のレポジトリです。
