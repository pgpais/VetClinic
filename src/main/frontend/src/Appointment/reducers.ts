import {Action} from "redux";
import {AptState} from "./index";
import {ADD_APT, RECEIVE_APTS, ReceiveAptsAction, REQUEST_APTS} from "./actions";

const initialState = {
    apts:[],
    isFetchingApts:false
};

function aptReducer(state:AptState = initialState, action:Action):AptState {
    switch (action.type) {
        case ADD_APT:
            return {...state}; //TODO: change state to add new added Apt
        case REQUEST_APTS:
            return {...state, isFetchingApts:true};
        case RECEIVE_APTS:
            return {...state, isFetchingApts:false, apts: (action as ReceiveAptsAction).data};
        default:
            return state;
    }
}

export default aptReducer