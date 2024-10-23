#!/bin/bash

# 새로운 컨테이너 실행
docker run -d --name front-app -p 8080:8080 masiljangajji/back-app:latest
