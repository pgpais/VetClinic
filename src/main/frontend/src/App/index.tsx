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

import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import SignInForm, { SignInState } from "../SignIn";
import { FilteredPetList, PetState } from "../Pets";
import reducer from "./reducers";
import { applyMiddleware, createStore } from "redux";
import thunk from "redux-thunk";
import { connect, Provider } from "react-redux";
import { createLogger } from "redux-logger";
import { Home, VetState } from "../Home";
import {Appointment, AptState} from "../Appointment";

export interface GlobalState {
  apts: AptState;
  username: string;
  pets: PetState;
  signIn: SignInState;
  vets: VetState;
}

const ProtoPage = (props: { isSignedIn: boolean }) => {
  //TODO: move this to home?
  //TODO: show different stuff on Home based on role
  return (
    <>
      <>
        <SignInForm />
      </>
      <Home /> //TODO: show different stuff based on role
    </>
  );
};
const mapStateToProps = (state: GlobalState) => ({
  isSignedIn: state.signIn.isSignedIn,
  username: state.username
});
const Page = connect(mapStateToProps)(ProtoPage);

const logger = createLogger(); // see the console for the effect of this middleware
let store = createStore(reducer, applyMiddleware(thunk, logger));

const App = () => {
  return (
    <Provider store={store}>
      <Page />
    </Provider>
  );
};

export default App;
