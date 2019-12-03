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

export const SIGN_IN = 'SIGN_IN';
export const SIGN_OUT = 'SIGN_OUT';
export const REGISTER = 'REGISTER';

export interface SignInAction extends Action { data:string | null }

export const signIn = (token:string|null) => ({type:SIGN_IN, data:token});
export const signOut = () => ({type:SIGN_OUT});
export const register = (token:string|null) => ({type:REGISTER, data:token});

export function requestSignIn(username:string, password:string)  {
    return (dispatch:any) =>
        performLogin(username,password)
            .then(token => dispatch(signIn(token)))
}

async function performLogin(username:string, password:string) {
    const myHeaders = new Headers();
    myHeaders.append('Content-Type', 'application/json');

    return fetch("/login",
        {method:'POST',
            headers: myHeaders,
            body: JSON.stringify({username:username, pass:password})})
        .then( response => {
            if( response.ok ) {
                localStorage.setItem("username", username);
                return response.headers.get('Authorization');
            }
            else {
                console.log(`Error: ${response.status}: ${response.statusText}`);
                return null;
                // and add a message to the Ui: wrong password ?? other errors?
            }
        })
        .catch( err => { console.log(err); return null })
}

export function requestRegister(username: string, password: string, name: string, photo: string, email: string, phone: string, address: string){
    return (dispatch:any) =>
        performRegister(username, password, name, photo, email, phone, address)
            .then(token => dispatch(register(token)))
}

async function performRegister(username:string, password:string, name:string, photo:string, email:string, phone:string, address:string) {
    const myHeaders = new Headers();
    myHeaders.append('Content-Type', 'application/json');

    return fetch("/register",
        {method:'POST',
            headers: myHeaders,
            body: JSON.stringify({username:username, pass:password, name:name, photo:photo, email:email, phone:phone, address:address})})
        .then( response => {
            if( response.ok )
                return response.headers.get('Authorization');
            else {
                console.log(`Error: ${response.status}: ${response.statusText}`);
                return null;
                // and add a message to the Ui: wrong password ?? other errors?
            }
        })
        .catch( err => { console.log(err); return null })
}
