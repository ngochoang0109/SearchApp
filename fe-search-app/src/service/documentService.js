import callAPI from "../utils/callAPI"

const addDocument=(data)=>{
    return callAPI({
        'Content-Type': 'application/json',
    },'elastic/add-document','POST',data);
}

const getAll=(data)=>{
    return callAPI({
        'Content-Type': 'application/json',
    },'elastic/search','POST',data);
}

const getAllOfIndex=(index)=>{
    return callAPI({
        'Content-Type': 'application/json',
    },'elastic/search','POST',index);
}

export const documentService={
    addDocument,
    getAll,
    getAllOfIndex
}