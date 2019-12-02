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

import {getData} from "../Utils/NetworkUtils";
import {Action} from "redux";
import {Pet} from "./index";

export const ADD_PET = 'ADD_PET';
export const REQUEST_PETS = 'REQUEST_PETS';
export const RECEIVE_PETS = 'RECEIVE_PETS';

export interface AddPetAction extends Action { name:string }
export interface ReceivePetAction extends Action { data:Pet[] }

export const addPet = (pet:Pet) => ({type:ADD_PET, data:pet});
export const requestPets = () => ({type: REQUEST_PETS});
export const receivePets = (data:Pet[]) => ({type: RECEIVE_PETS, data:data});

export function fetchPets(username:string, filter:string) {
    return (dispatch:any) => {
        dispatch(requestPets());
        console.log(username);
        return getData(`/client/byUsername/${username}/pets?search=${encodeURI(filter)}`, [])
            .then(data => { data && dispatch(receivePets(data as Pet[])) })
        // notice that there is an extra "pet" in the path above which is produced
        // in this particular implementation of the service. {pet: Pet, appointments:List<AppointmentDTO>}
    }
}

export function fetchClientPets() {
    return (dispatch:any) => {
        dispatch()
    }
}
