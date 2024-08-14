# Order Management

[[_TOC_]]

---

:scroll: **START**

## Introduction

This is a simple demo of Order Management System that simulates creating orders in asynchronous manner

---

## Building the project

Please note that I am using OpenApi to generate the application layer classes (APIs, DTOs).
So in order to have the project without errors please

There's a Dockerfile and docker-compose.yaml file in the project that you can use to build the project without any changes in your environment </br>
To do so you will only need:
- docker to be installed on your machine
- run the following command: docker compose up

## How to test

I updated the [order-management.yml](order-management.yml) file so, to test the solution you will need to use the updated swagger file<br>
Also one of the changes I made is that all the endpoints are now authorized except '/auth' endpoint so in order to useany of them<br>
you will need to authenticate first:

You can use these credentials to get an access token:

Email: admin@test.com
Password: admin123456

Response example:

{
"message": "Authenticated",
"accessToken": "eyJhbGciOiJIUzI1NiJ9.xxxxxxxxxxxxxxxxxxxxxx"
}

## Used Technologies
The technologies used in this project are quite simple:
- spring boot for developing the application
- H2 db for persisting the orders data

:scroll: **END**
