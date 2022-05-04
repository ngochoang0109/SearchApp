import { documentConstant } from "../constant/documentConstant";
import { documentService } from "../service/documentService";

const addDocument = (data) => {
    return (dispatch) => {
        return documentService.addDocument(data)
            .then((response) => {
                return (dispatch({
                    type: documentConstant.ADD_DOCUMENT,
                    data: response.data
                }))
            })
    }
}

const getAllOfIndex = (data) => {
    console.log(data)
    return (dispatch) => {
        return documentService.getAllOfIndex({
            "index": data,
            "pageSize": 10,
            "keyword": "",
            "pageIndex": 1
        })
            .then((response) => {
                console.log(response.data);
                return (dispatch({
                    type: documentConstant.GET_ALL_OF_INDEX,
                    data: response.data
                }))
            })
    }
}

export const documentAction = {
    addDocument,
    getAllOfIndex
}