import axios from 'axios';
import { API_BASE_URL } from '../constant/API';

const callAPI = (headers,endpoint, method = "GET", data) => {
    return axios({
        headers:headers,
        method: method,
        url: `${API_BASE_URL}/${endpoint}`,
        data: data
    });
}

export default callAPI;