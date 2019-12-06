import { Action } from "redux";
import { Appointment } from "./index";
import { getData } from "../Utils/NetworkUtils";

export const ADD_APT = "ADD_APT";
export const REQUEST_APTS = "REQUEST_APTS";
export const RECEIVE_APTS = "RECEIVE_APTS";

export interface ReceiveAptsAction extends Action {
  data: Appointment[];
}

export const requestApts = () => ({ type: REQUEST_APTS });
export const receiveApts = (data: Appointment[]) => ({
  type: RECEIVE_APTS,
  data: data
});

export function fetchApts() {
  return (dispatch: any) => {
    let username = localStorage.getItem("username");
    dispatch(requestApts());
    return getData(`/client/${username}/apts`, []).then(data => {
      data && dispatch(receiveApts(data as Appointment[]));
    });
  };
}


export const register = (token: string | null) => ({
  type: ADD_APT,
  data: token
});

export function requestAppointmentRegister(
  date: any,
  time: any,
  description: string,
  status: string,
  reason: string,
  pet: any,
  client: any,
  vet: any
) {
  return (dispatch: any) =>
    performAppointmentRegister(
      date,
      time,
      description,
      status,
      reason,
      pet,
      client,
      vet
    ).then(token => dispatch(register(token)));
}

async function performAppointmentRegister(
  date: any,
  time: any,
  description: string,
  status: string,
  reason: string,
  pet: any,
  client: any,
  vet: any
) {
  const myHeaders = new Headers();
  myHeaders.append("Content-Type", "application/json");

  return fetch("/appointments", {
    method: "POST",
    headers: myHeaders,
    body: JSON.stringify({
      date: date + " " + time,
      desc: description,
      status: status,
      reason: reason,
      petId: pet,
      client: client,
      vetId: vet
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
