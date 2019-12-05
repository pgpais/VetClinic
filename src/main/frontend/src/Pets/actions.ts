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

import { getData } from "../Utils/NetworkUtils";
import { Action } from "redux";
import { Pet } from "./index";

export const ADD_PET = "ADD_PET";
export const REQUEST_PETS = "REQUEST_PETS";
export const RECEIVE_PETS = "RECEIVE_PETS";

export interface AddPetAction extends Action {
  photo: string;
  name: string;
}
export interface ReceivePetAction extends Action {
  data: Pet[];
}

export const addPet = (pet: Pet) => ({ type: ADD_PET, data: pet });
export const requestPets = () => ({ type: REQUEST_PETS });
export const receivePets = (data: Pet[]) => ({
  type: RECEIVE_PETS,
  data: data
});

export function fetchPets(username: string | null, filter: string) {
  return (dispatch: any) => {
    username = localStorage.getItem("username"); //TODO: stop using this, eventually
    dispatch(requestPets());
    console.log("Username at fetchPets: " + username);
    return getData(
      `/client/byUsername/${username}/pets?search=${encodeURI(filter)}`,
      []
    ).then(data => {
      data && dispatch(receivePets(data as Pet[]));
    });
    // notice that there is an extra "pet" in the path above which is produced
    // in this particular implementation of the service. {pet: Pet, appointments:List<AppointmentDTO>}
  };
}
export const ADD_NEW_PET = "ADD_PET";

export const register = (token: string | null) => ({
  type: ADD_NEW_PET,
  data: token
});

export function requestPetRegister(
  name: string,
  species: string,
  photo: string,
  owner: any,
  appointments: any,
  chip: string,
  physDesc: string,
  healthDesc: string,
  removed: boolean
) {
  return (dispatch: any) =>
    performRegister(
      name,
      species,
      photo,
      owner,
      appointments,
      chip,
      physDesc,
      healthDesc,
      removed
    ).then(token => dispatch(register(token)));
}

async function performRegister(
  name: string,
  species: string,
  photo: string,
  owner: any,
  appointments: any,
  chip: string,
  physDesc: string,
  healthDesc: string,
  removed: boolean
) {
  const myHeaders = new Headers();
  myHeaders.append("Content-Type", "application/json");

  return fetch("/pets", {
    method: "POST",
    headers: myHeaders,
    body: JSON.stringify({
      name: name,
      species: species,
      photo: photo,
      owner: owner,
      appointments: appointments,
      chip: chip,
      physDesc: physDesc,
      healthDesc: healthDesc,
      removed: removed
    })
  })
    .then(response => {
      if (response.ok) return response.headers.get("Authorization");
      else {
        console.log(`Error: ${response.status}: ${response.statusText}`);
        return null;
        // and add a message to the Ui: wrong password ?? other errors?
      }
    })
    .catch(err => {
      console.log(err);
      return null;
    });
}
