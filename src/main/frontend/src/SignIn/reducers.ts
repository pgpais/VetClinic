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

const parseAuthorities = (authorities:Array<Authorities>) => { //TODO: what's the type?
    //TODO: get authorities from token
    let roles: String[] = [];
    authorities.forEach(e => {
        roles.push(e["authority"])
    });
    console.log(roles)
    const first = authorities[0];
    console.log(first);
    console.log(first["authority"]);
    return first["authority"].toString();
};

const initialState = {isSignedIn: checkIfTokenIsValid() };

function signInReducer(state = initialState, action:Action) {
    switch (action.type) {
        case SIGN_IN:
            let token = (action as SignInAction).data;
            if( token ) {
                let t = parseJwt(token.split(" ")[1]);
                console.log(t);
                //console.log(roles);
                localStorage.setItem("role", parseAuthorities(t["authorities"])); //TODO: save in globalState (How do you make it persist?)
                localStorage.setItem('jwt',token);
                return {...state, isSignedIn: true};
            } else {
                return state;
            }
        case SIGN_OUT:
            localStorage.removeItem('jwt');
            return {...state, isSignedIn: false};
        case REGISTER:
            let regtoken = (action as SignInAction).data;
            if( regtoken ) {
                localStorage.setItem('jwt',regtoken);
                return {...state, isSignedIn: true};
            } else {
                return state;
            }
        default:
            return state;
    }
}

export default signInReducer