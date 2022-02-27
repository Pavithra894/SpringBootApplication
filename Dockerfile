FROM mhart/alpine-node:6.9.2

WORKDIR /var/app

COPY . /var/app

RUN npm install --development

EXPOSE 3000

ENV NODE_ENV=development

CMD ["node", "bin/www”]