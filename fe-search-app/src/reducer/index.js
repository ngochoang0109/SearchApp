import { combineReducers } from 'redux';
import documentReducer from './documentReducer';
const appReducer = combineReducers({
    documentReducer
})

const rootReducer = (state, action) => {
    return appReducer(state, action)
}

export default rootReducer;