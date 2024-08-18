FROM node:20-slim

WORKDIR /work

COPY e2e /work

RUN npm install

CMD ["npm", "run", "test"]