import {Action} from "redux";
import {Vet} from "./index";
import {getData} from "../Utils/NetworkUtils";

export const ADD_VET = 'ADD_VET';
export const REQUEST_VETS = 'REQUEST_VETS';
export const RECEIVE_VETS = 'RECEIVE_VETS';

export interface AddVetAction extends Action{ name:string }
export interface ReceiveVetAction extends Action{ data:Vet[] }

export const addVet = (vet:Vet) => ({type: ADD_VET, data:vet});
export const requestVets = () => ({type: REQUEST_VETS});
export const receiveVets = (data:Vet[]) => ({type: RECEIVE_VETS, data:data});

export function fetchVets() {
    return (dispatch:any) => {
        dispatch(requestVets());
        return getData(`/vets`, [])
            .then(data => { data && dispatch(receiveVets(data as Vet[]))})
    }
}