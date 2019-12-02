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
import {REGISTER, SIGN_IN, SIGN_OUT, SignInAction} from "./actions";

interface Authorities {authority:String}

function checkIfTokenIsValid() {
    return localStorage.getItem('jwt') != null;
}

const parseJwt = (token:string) => {
    try {
        return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
        return null;
    }
};

const parseAuthorities = (authorities:Array<Authorities>) => {
    let roles = "";
    authorities.forEach(e => {
        roles += " " + e["authority"]
    }); //TODO: how to store several authorities?
    const first = authorities[0];
    return first["authority"].toString();
};

const initialState = {isSignedIn: checkIfTokenIsValid() };

function signInReducer(state = initialState, action:Action) {
    switch (action.type) {
        case SIGN_IN:
            let token = (action as SignInAction).data;
            if( token ) {
                let t = parseJwt(token.split(" ")[1]);
                //console.log(roles);
                let username = t["username"];
                console.log(username);
                let roles = parseAuthorities(t["authorities"]);
                localStorage.setItem("role", roles); //TODO: save in globalState (How do you make it persist?)
                localStorage.setItem('jwt',token);
                return {...state, isSignedIn: true, roles:roles, username:username};
            } else {
                return state;
            }
        case SIGN_OUT:
            localStorage.removeItem('jwt');
            return {...state, isSignedIn: false, roles:null, username:""};
        case REGISTER:
            let regtoken = (action as SignInAction).data;
            if( regtoken ) {
                let t = parseJwt(regtoken.split(" ")[1]);
                let username = t["username"];
                let roles = parseAuthorities(t["authorities"]);
                localStorage.setItem('jwt',regtoken);
                return {...state, isSignedIn: true, roles:roles, username:username};
            } else {
                return state;
            }
        default:
            return state;
    }
}

export default signInReducer