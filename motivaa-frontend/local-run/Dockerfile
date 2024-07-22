# Use the official Node.js runtime as a parent image
FROM node:lts-alpine

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json first to leverage Docker's caching mechanism
COPY ../package.json ../package-lock.json ./

# Install dependencies. This should be done before copying the rest of the files
RUN npm install

# Copy the rest of the application code
COPY ../vite.config.js ./
COPY ../.eslintrc.cjs ./
COPY ../index.html ./
COPY . .

# Expose the necessary ports
EXPOSE 8081
EXPOSE 3000
EXPOSE 3001
EXPOSE 8093

# Command to run the application
CMD ["npm", "run", "dev"]

