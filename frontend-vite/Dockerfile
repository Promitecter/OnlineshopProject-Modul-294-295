# Stage 1: Build der statischen Dateien
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Webserver zum Ausliefern (Nginx)
FROM nginx:alpine
# Kopiere den "dist" Ordner (das Ergebnis von npm run build) in den Nginx Ordner
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]