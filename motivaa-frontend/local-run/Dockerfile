
FROM node:lts-alpine


WORKDIR /app

COPY ../package.json ../package-lock.json ./


RUN npm install


COPY ../.eslintrc.cjs ./
COPY ../.eslintrc.json ./
COPY ../next.config.mjs ./
COPY ../postcss.config.js ./
COPY ../postcss.config.mjs ./
COPY ../tailwind.config.js ./
COPY ../mocker.mjs ./
COPY ../index.html ./
COPY ../.env ./
COPY ../jsconfig.json ./
COPY . .


EXPOSE 8081
EXPOSE 3000
EXPOSE 3001
EXPOSE 8093


CMD ["npm", "run", "dev"]

