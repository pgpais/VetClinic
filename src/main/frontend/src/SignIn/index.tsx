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

import React, {ChangeEvent, FormEvent, useState} from "react";
import {connect} from "react-redux";
import {GlobalState} from "../App";
import {requestSignIn, signOut} from "./actions";

export interface SignInState { isSignedIn: boolean }

const ProtoSignInForm = (
    props:{
        isSignedIn:boolean,
        performSignIn:(username:string, pass:string)=>void,
        performSignOut:()=>void
    }) => {

    const [ username, setUsername ] = useState("");
    const [ password, setPassword ] = useState("");

    let submitHandler = (e:FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        props.performSignIn(username, password);
        setUsername("");
        setPassword("")
    };

    let handlerLogout = (e:FormEvent<HTMLButtonElement>) => { props.performSignOut() };

    let usernameChangeHandler = (e:ChangeEvent<HTMLInputElement>) => { setUsername(e.target.value) };

    let passwordChangeHandler = (e:ChangeEvent<HTMLInputElement>) => { setPassword(e.target.value) };

    let signInForm =
        (<form onSubmit={submitHandler}>
            <div><label>Username: <input type="text" value={username} onChange={usernameChangeHandler}/></label></div>
            <div><label>Password: <input type="password" value={password} onChange={passwordChangeHandler}/></label></div>
            <button>Sign In</button>
         </form>);

    let signOutForm = <button onClick={handlerLogout}>Sign out</button>;

    return (<> {props.isSignedIn ? signOutForm : signInForm} </>);
    // add a message space for alerts (you were signed out, expired session)
};
const mapStateToProps = (state:GlobalState) => ({isSignedIn:state.signIn.isSignedIn});
const mapDispatchToProps =
    (dispatch:any) =>
        ({
            performSignIn: (username:string, pass:string) => { dispatch(requestSignIn(username,pass))},
            performSignOut: () => dispatch(signOut())
        });
const SignInForm = connect(mapStateToProps,mapDispatchToProps)(ProtoSignInForm);

export default SignInForm

