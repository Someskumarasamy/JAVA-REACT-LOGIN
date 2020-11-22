const axios = require('axios');
const baseUrl = 'http://localhost:8080/SimpleLogin/';
export const googleId = '879295277348-1it13b4u31puv222uq4qutu256r80v9u.apps.googleusercontent.com'
export const api = axios.create({
    baseURL: baseUrl + "user/",
    timeout: 10000000,
    headers: {
        'Access-Control-Allow-Origin': '*'
    }
});