# Venue and Menu Service
This repository contains the source code for the Venue and Menu Service of the Spring Divas organization.

## About us
This project is for the Restaurant Interactive Ordering System, a platform that lets restaurant owners manage venues, orders, and lets customers order food directly from their table via QR code.

## Run locally
### Instruction:
1. Clone the project
    ```shell
    git clone https://github.com/spring-divas/venue-and-menu-service.git
    cd venue-and-menu-service
    ```
2. Build the service image:
    ```shell
    docker build -t venue-service .
    ```
3. If you want to run the project with `docker-compose`, you first need to configure the `.env` (See `.env.example`). After this, run the docker-compose command:
    ```shell
    docker compose up -d
    ```
4. If you want to run the project with `k8s`, you first need to configure `venue-secret.yaml` (See `k8s/venue-secret.yaml.example`). After this, start the `minikube`:
    ```shell
    minikube start
    ```
    Load the build image to the `minikube`:
    ```shell
    minikube image load venue-service:latest
    ```
    Do the `dry-run`:
    ```shell
    kubectl apply -f k8s/. --dry-run=client
    kubectl apply -f k8s/. --dry-run=server
    ```
    Apply all of the `k8s` manifests:
    ```shell
    kubectl apply -f k8s/.
    ```
    Check the pods:
    ```shell
    kubectl get pods
    ```
    Port-forward the port of the service:
    ```shell
    kubectl port-forward svc/venue-service 8081:8080
    ```