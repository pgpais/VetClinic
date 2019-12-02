import {VetState} from "./index";
import {Action} from "redux";
import {ADD_VET, AddVetAction, RECEIVE_VETS, ReceiveVetAction, REQUEST_VETS} from "./actions";

const initialState = {
    vets:[],
    isFetching:false,
};

function vetReducer(state:VetState = initialState, action:Action):VetState{
    switch(action.type){
        case ADD_VET:
            return {...state, vets:[...state.vets, { vetId:0, name: (action as AddVetAction).name}]};
        case REQUEST_VETS:
            return {...state, isFetching:true};
        case RECEIVE_VETS:
            return {...state, isFetching:false, vets: (action as ReceiveVetAction).data};
        default:
            return state;
    }
}

export default vetReducer