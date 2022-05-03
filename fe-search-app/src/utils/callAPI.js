import axios from 'axios';
import { API_BASE_URL } from '../constant/API';

const callAPI = (endpoint, method = "GET", data) => {
    return axios({
        method: method,
        url: `${API_BASE_URL}/${endpoint}`,
        data: data
    });
}

export default callAPI;