import { Action } from "redux";

export const ADD_PET = "ADD_PET";

export const register = (token: string | null) => ({
  type: ADD_PET,
  data: token
});

export function requestAdminRegister(
  username: string,
  password: string,
  name: string,
  photo: string,
  email: string,
  phone: string,
  address: string,
  employeeID: string
) {
  return (dispatch: any) =>
    performRegister(
      username,
      password,
      name,
      photo,
      email,
      phone,
      address,
      employeeID
    ).then(token => dispatch(register(token)));
}

async function performRegister(
  username: string,
  password: string,
  name: string,
  photo: string,
  email: string,
  phone: string,
  address: string,
  employeeID: string
) {
  const myHeaders = new Headers();
  myHeaders.append("Content-Type", "application/json");

  return fetch("/admin", {
    method: "POST",
    headers: myHeaders,
    body: JSON.stringify({
      username: username,
      pass: password,
      name: name,
      photo: photo,
      email: email,
      phone: phone,
      address: address,
      employeeID: employeeID
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
