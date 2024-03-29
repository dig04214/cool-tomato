# 백엔드

## 기술 스택

- Spring boot 3.2.1
https://start.spring.io/
- java 17
https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
- mysql 8.1.0
https://downloads.mysql.com/archives/community/
- redis 7.2.3
- Kafka 3.6.0
- Elastic Search 7.17.18
- Logstash 7.10.2
- Kibana 7.10.2
- Spring cloud gateway 3.2.2
https://start.spring.io/
    
- Srping cloud Neflix - Eureka 3.2.2
https://start.spring.io/
    
- Srping cloud config 3.2.2
https://start.spring.io/
    
- IntelliJ IDEA 2023.3.2
https://www.jetbrains.com/ko-kr/idea/download/?section=windows
- DBeaver 23.3.1
https://dbeaver.io/download/

## 빌드 및 실행 매뉴얼

1. ec2 ssh 접속

```jsx
ssh -i I10A501T.pem ubuntu@i10a501.p.ssafy.io
```

2. Zookeeper, Kafka, Apache Storm, Elasticsearch, Logstash, Kibana, Redis, Openvidu 설치 및 실행
3. Gitlab에서 프로젝트 clone

```bash
git clone https://lab.ssafy.com/s10-webmobile1-sub2/S10P12A501
```

4. Spring cloud config server application.yml 수정

```bash
cd ~/S10P12A501/backend/backend-config
vi src/main/resources/application.yml

server:
  port: 8888
spring:
  application:
    name: config-server
  cloud:
    config:
      server:
        git:
          default-label: main
          uri: https://lab.ssafy.com/s10-webmobile1-sub2/S10P12A501
          search-paths: backend/config/**
          username: ${GIT_ID}
          password: ${GIT_PASSWORD}
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    shutdown:
      enabled: true
```

5. Spring cloud server[config, eureka, gateway] 순으로 빌드 및 시작

```bash
cd ~/S10P12A501/backend/backend-${SERVER_NAME}/
chmod 755 gradlw
./gradlw clean
./gradlw build
```

6. Spring boot server[auth, user, product, broadcast, chat, chatbot] 빌드 및 시작

```bash
cd ~/S10P12A501/backend/backend-${SERVER_NAME}/
chmod 755 gradlw
./gradlw clean
./gradlw build
```

# 프론트엔드

- Node.js (up to 20.10.0)
[Node.js — Download (nodejs.org)](https://nodejs.org/en/download/)
- VisualStudio Code (up to 1.86.1)
https://code.visualstudio.com/Download

```
//package.json
"@chakra-ui/icons": "^2.1.1",
"@chakra-ui/react": "^2.8.2",
"@emotion/react": "^11.11.3",
"@emotion/styled": "^11.11.0",
"@reduxjs/toolkit": "^2.0.1",
"@stomp/stompjs": "^7.0.0",
"@types/react-router-dom": "^5.3.3",
"@types/sockjs-client": "^1.5.4",
"axios": "^1.6.5",
"chart.js": "^4.4.1",
"dayjs": "^1.11.10",
"font-awesome": "^4.7.0",
"framer-motion": "^10.18.0",
"froala-editor": "^4.1.4",
"openvidu-browser": "^2.29.1",
"react": "^18.2.0",
"react-chartjs-2": "^5.2.0",
"react-dom": "^18.2.0",
"react-froala-wysiwyg": "^4.1.4",
"react-icons": "^5.0.1",
"react-redux": "^9.1.0",
"react-router-dom": "^6.21.2",
"redux-persist": "^6.0.0",
"sockjs-client": "^1.6.1",
"stompjs": "^2.3.3"
```

## 빌드 및 실행 매뉴얼

1. ec2 ssh 접속
    
    ```bash
    ssh -i I10A501T.pem ubuntu@i10a501.p.ssafy.io
    ```
    
2. node.js 설치( 20버전 이상 LTS )
    
    ```bash
    # Remove the old PPA if it exists
    sudo add-apt-repository -y -r ppa:chris-lea/node.js &&\
    sudo rm -f /etc/apt/sources.list.d/chris-lea-node_js-*.list &&\
    sudo rm -f /etc/apt/sources.list.d/chris-lea-node_js-*.list.save
    
    # Add the NodeSource package signing key
    KEYRING=/usr/share/keyrings/nodesource.gpg
    curl -fsSL https://deb.nodesource.com/gpgkey/nodesource.gpg.key | gpg --dearmor | sudo tee "$KEYRING" >/dev/null
    gpg --no-default-keyring --keyring "$KEYRING" --list-keys
    chmod a+r /usr/share/keyrings/nodesource.gpg
    # The key ID is 9FD3B784BC1C6FC31A8A0A1C1655A0AB68576280
    
    # Add the desired NodeSource repository
    
    VERSION=node_20.x
    KEYRING=/usr/share/keyrings/nodesource.gpg
    DISTRO="$(lsb_release -s -c)"
    echo "deb [signed-by=$KEYRING] https://deb.nodesource.com/$VERSION $DISTRO main" | sudo tee /etc/apt/sources.list.d/nodesource.list
    echo "deb-src [signed-by=$KEYRING] https://deb.nodesource.com/$VERSION $DISTRO main" | sudo tee -a /etc/apt/sources.list.d/nodesource.list
    
    # Update package lists and install Node.js
    sudo apt-get update
    sudo apt-get install nodejs
    ```
    
3. pm2 설치
    
    ```bash
    npm install pm2 -g
    ```
    
4. certbot을 이용해 인증서 발급(fullchain.pem, privkey.pem)
5. Gitlab에서 프로젝트 clone
    
    ```bash
    git clone https://lab.ssafy.com/s10-webmobile1-sub2/S10P12A501
    ```
    
6. frontend → vite-project 폴더로 이동
    
    ```bash
    cd ./S10P12A501/frontend/vite-project
    ```
    
7. 노드 모듈 설치
    
    ```bash
    npm i
    ```
    
8. vite 빌드
    
    ```bash
    npm run build
    ```
    
9. dist 폴더 생성 확인
10. frontend-static 폴더 생성 및 이동
    
    ```bash
    cd /home/ubuntu
    mkdir frontend-static
    cd ./frontend-static
    ```
    
11. express 설치
    
    ```bash
    npm install express
    ```
    
12. 발급한 인증서를 frontend-static → cert 폴더로 이동 또는 심볼릭 링크 생성
    
    ```bash
    mkdir ./cert
    sudo cp /etc/letsencrypt/live/i10a501.p.ssafy.io/fullchain.pem ./cert
    sudo cp /etc/letsencrypt/live/i10a501.p.ssafy.io/privkey.pem ./cert
    ```
    
13. privkey.pem 권한 변경
    
    ```bash
    sudo chmod 644 ./cert/privkey.pem
    ```
    
14. 빌드한 dist 폴더를 frontend-static 폴더로 복사 또는 심볼릭 링크 생성
    
    ```bash
    ln -s /home/ubuntu/S10P12A501/frontend/vite-project/dist dist
    ```
    
15. 프로젝트 폴더의 exec에서 static.js를 frontend-static 폴더로 이동
    
    ```bash
    sudo mv /home/ubuntu/S10P12A501/exec/static.js ./static.js
    ```
    
16. pm2로 static.js 실행
    
    ```bash
    sudo pm2 ./static.js --name frontend
    ```
    
17. 실행 중인 노드 프로세스 확인
    
    ```bash
    sudo pm2 ps
    ```
