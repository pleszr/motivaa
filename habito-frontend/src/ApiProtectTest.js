const express = require('express');
const expressJwt  = require('express-jwt');
const {request} = require("express");

const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));


// Public key from Keycloak, ensure it's formatted correctly
const secretKey = 'eyJhbGciOiJIUzUxMiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI2NWQ3OWZlMS1lMDBkLTQwOTEtYjUyNC01NTMzZmI2OTQ1NmEifQ.eyJleHAiOjE3MTUxMzIxMTQsImlhdCI6MTcxNTA0NTcxNCwianRpIjoiNDZkZmM2YzMtOWY3ZC00MjAyLWJiOTUtZTc3MzZjNmYzMzQ2IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9oYWJpdG8iLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvcmVhbG1zL2hhYml0byIsInR5cCI6IkluaXRpYWxBY2Nlc3NUb2tlbiJ9.FsZgr4eBHdIZHhTuDQ3c9fjejk4-x8O_2udxSLqAH1S3Q1zNGvue8dW4gveUcB44eil6GtxTjCwtG_q4_q1iXw'

// Middleware to protect routes with Keycloak JWT
const checkJwt = expressJwt.expressjwt(({
    secret: secretKey,
    audience: 'http://localhost:8080/realms/habito',
    issuer: `http://localhost:8080/realms/habito`,
    algorithms: ['HS512']
}));


app.use('/posts', checkJwt, (req, res, next) => {
    next();
    res.status(201).send({ message: 'Test post endpoint works' });

});


app.listen(3030, () => {
    console.log('Server is running on http://localhost:3030');
});






