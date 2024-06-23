FROM node:lts-alpine

WORKDIR /app
## Copying package.json
COPY ../package.json ./
## Copying vite.config.json
COPY ../vite.config.js ./
## Copying .eslintrc.cjs
COPY ../.eslintrc.cjs ./
## Copying index.html for Vite
COPY ../index.html ./

RUN npm install


COPY . .

EXPOSE 8081
EXPOSE 3000

CMD ["npm", "run", "dev"]
