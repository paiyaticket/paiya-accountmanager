
#!/bin/bash
./gradlew clean build && 
docker build -t koffiange/paiya-accountmanager:latest . &&
docker push &&
cd cicd &&
docker compose up -d --force-recreate