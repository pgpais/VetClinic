import {Action} from "redux";
import {Appointment} from "./index";
import {getData} from "../Utils/NetworkUtils";

export const REQUEST_APTS = 'REQUEST_APTS';
export const RECEIVE_APTS = 'RECEIVE_APTS';

export interface ReceiveAptsAction extends Action{data:Appointment[]}

export const requestApts = () => ({type:REQUEST_APTS});
export const receiveApts = (data:Appointment[]) => ({type:RECEIVE_APTS, data:data});

export function fetchApts() {
    return (dispatch:any) => {
        let username = localStorage.getItem("username");
        dispatch(requestApts());
        return getData(`/client/${username}/apts`, [])
            .then(data => { data && dispatch(receiveApts(data as Appointment[]))})
    }
}