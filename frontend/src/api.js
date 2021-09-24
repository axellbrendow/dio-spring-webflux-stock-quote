import axios from "axios";

const api = axios.create({
    baseURL: 'http://localhost:8080'
});

export const getLastQuote = () => api.get('/quotes/last')
