import { documentConstant } from "../constant/documentConstant";

const initialState=[]

const documentReducer=(state=initialState, action)=>{
    switch (action.type) {
        case documentConstant.GET_DOCUMENT:
            return action.data;
        case documentConstant.GET_ALL_OF_INDEX:
            const data=action.data.object;
            const result=data.map((item, index)=>{
                return item.sourceAsMap;
            })
            return result;
        default:
            return state;
    }
}

export default documentReducer;