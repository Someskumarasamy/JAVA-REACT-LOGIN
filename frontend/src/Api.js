const axios = require('axios');
const baseUrl = 'http://localhost:8080/SimpleLogin/';
export const api = axios.create({
    baseURL: baseUrl + "user/",
    timeout: 10000000,
    headers: {
        'Access-Control-Allow-Origin': '*'
    }
});