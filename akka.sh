#!/bin/bash

cat > abc.yml << EOF
apiVersion: apps/v1
kind: Deployment
metadata:
   name: akka-http-app-deployment
spec:
   replicas: 1
   template:
         metadata:
            name: akka-http-pod
            labels:
               app: akka-http-app
         spec:
             containers:
              - image: sagar2405/akka-http-app:${GO_PIPELINE_LABEL}
                name: akka-http-app
   selector:
         matchLabels:
              app: akka-http-app
EOF