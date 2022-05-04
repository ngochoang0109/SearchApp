import { documentConstant } from "../constant/documentConstant";

const initialState=[]

const documentReducer=(state=initialState, action)=>{
    switch (action.type) {
        case documentConstant.GET_DOCUMENT:
            return action.data;
        case documentConstant.GET_ALL_OF_INDEX:
            return action.data;
        default:
            return state;
    }
}

export default documentReducer;