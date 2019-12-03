/**
 Copyright 2019 João Costa Seco, Eduardo Geraldo

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import {Action} from "redux";
import {
    ADD_PET,
    AddPetAction,
    RECEIVE_PETS,
    ReceiveAptAction,
    ReceivePetAction,
    REQUEST_APTS,
    REQUEST_PETS
} from './actions';
import {PetState} from "./index";

const initialState = {
    pets:[],
    isFetching:false,
    isFetchingApts:false,
    apts:[],
};
function petReducer(state:PetState = initialState, action:Action):PetState {
    switch (action.type) {
        case ADD_PET:
            return {...state, pets:[...state.pets, { id:0, name: (action as AddPetAction).name, photo: (action as AddPetAction).photo}]};
        case REQUEST_PETS:
            return {...state, isFetching:true};
        case RECEIVE_PETS:
            return {...state, isFetching:false, pets: (action as ReceivePetAction).data};
        case REQUEST_APTS:
            return {...state, isFetchingApts: true};
        case RECEIVE_PETS:
            return {...state, isFetchingApts: false, apts: (action as ReceiveAptAction).data};
        default:
            return state
    }
}

export default petReducer