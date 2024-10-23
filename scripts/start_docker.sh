#!/bin/bash

# 새로운 컨테이너 실행
docker run -d --name front-app -p 80:3000 masiljangajji/front-app:latest
