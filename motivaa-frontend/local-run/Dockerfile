FROM node:lts-alpine

WORKDIR /app
## Copying package.json
COPY ../package.json ./

RUN npm install


COPY . .

EXPOSE 8081

CMD ["npm", "start"]
