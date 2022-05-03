import callAPI from "../utils/callAPI"

const addDocument=(data)=>{
    return callAPI('','POST',data);
}

export const documentService={
    addDocument
}